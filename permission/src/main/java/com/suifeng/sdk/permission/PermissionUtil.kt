package com.suifeng.sdk.permission

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.provider.Settings
import androidx.annotation.NonNull
import androidx.annotation.Size
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.suifeng.sdk.permission.PermissionConstant.PERMISSION_GPS_OPEN
import com.suifeng.sdk.permission.PermissionConstant.PERMISSION_SYSTEM_NOTIFICATION
import com.suifeng.sdk.permission.PermissionConstant.PERMISSION_TAG
import com.suifeng.sdk.permission.widget.PermissionActivity
import com.suifeng.sdk.permission.widget.PermissionFragment
import com.suifeng.sdk.utils.ext.otherwise
import com.suifeng.sdk.utils.ext.yes

class PermissionUtil {
    companion object {
        internal lateinit var application: Application
        fun init(app: Application) {
            application = app
        }
    }
}

fun hasPermission(@Size(min = 1) vararg permissions: String): Boolean {
    permissions.forEach {
        // WRITE_SETTING 处理
        if (it == android.Manifest.permission.WRITE_SETTINGS &&
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
        ) {
            if (!Settings.System.canWrite(PermissionUtil.application)) {
                return false
            } else {
                return@forEach
            }
        }
        // SYSTEM_ALERT_WINDOW 处理
        if (it == android.Manifest.permission.SYSTEM_ALERT_WINDOW &&
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
        ) {
            if (!Settings.canDrawOverlays(PermissionUtil.application)) {
                return false
            } else {
                return@forEach
            }
        }
        // 通知
        if (it == PERMISSION_SYSTEM_NOTIFICATION) {
            if (!NotificationManagerCompat.from(PermissionUtil.application)
                            .areNotificationsEnabled()
            ) {
                return false
            } else {
                return@forEach
            }
        }
        // 定位服务
        if (it == PERMISSION_GPS_OPEN) {
            val locationManager =
                PermissionUtil.application.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val gpsOpen = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            if (gpsOpen) {
                return@forEach
            } else {
                return false
            }
        }

        if (ContextCompat.checkSelfPermission(
                    PermissionUtil.application,
                    it
                ) == PackageManager.PERMISSION_DENIED
        ) {
            return false
        }
    }
    return true
}

typealias PermissionCallback = (granted: List<PermissionData>, denied: List<PermissionData>) -> Unit

/**
 * 处理申请权限
 */
fun handleRequestPermission(
    context: Context,
    permissions: List<PermissionData>,
    callback: PermissionCallback
) {
    permissions.all {
        hasPermission(it.permission)
    }.yes {
        callback.invoke(permissions, emptyList())
    }.otherwise {
        if (context is FragmentActivity) {
            val permissionFragment = PermissionFragment().apply {
                this.callback = callback
                this.permissions = permissions
            }
            context.supportFragmentManager.apply {
                val transaction = beginTransaction()
                val alreadyFragment = findFragmentByTag(PERMISSION_TAG)
                if (alreadyFragment != null) {
                    transaction.remove(alreadyFragment)
                }
                transaction.add(permissionFragment, PERMISSION_TAG)
                transaction.commitNowAllowingStateLoss()
            }
        } else {
            PermissionActivity.startIntent(context, permissions, callback)
        }
    }
}

fun handleRequestPermission(
    manager: FragmentManager,
    permissions: List<PermissionData>,
    callback: PermissionCallback
) {
    val permissionFragment = PermissionFragment().apply {
        this.callback = callback
        this.permissions = permissions
    }
    manager.beginTransaction().apply {
        val alreadyFragment = manager.findFragmentByTag(PERMISSION_TAG)
        if (alreadyFragment != null) {
            remove(alreadyFragment)
        }
        add(permissionFragment, PERMISSION_TAG)
    }.commitNowAllowingStateLoss()
}