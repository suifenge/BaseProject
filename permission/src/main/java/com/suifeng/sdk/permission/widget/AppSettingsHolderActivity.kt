package com.suifeng.sdk.permission.widget

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.suifeng.sdk.permission.PermissionConstant.APP_SETTING_RC
import com.suifeng.sdk.permission.PermissionConstant.PERMISSION_GPS_OPEN
import com.suifeng.sdk.permission.PermissionConstant.PERMISSION_SYSTEM_NOTIFICATION

class AppSettingsHolderActivity : AppCompatActivity() {

    private var dialogFragment: DialogFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val permission = intent.getStringExtra(PERMISSION)
        val reason = intent.getStringExtra(REASON)
        dialogFragment = AppSettingsDialog(
            message = reason ?: "",
            positive = {
                try {
                    jumpToSetting(permission ?: "")
                } catch (e: Exception) {
                    finish()
                }
            },
            negative = {
                setResult(Activity.RESULT_CANCELED)
                finish()
            }
        )
        dialogFragment?.isCancelable = false
        dialogFragment?.show(supportFragmentManager, "")
    }

    private fun jumpToSetting(permission: String) {
        val intent =
            if (permission == android.Manifest.permission.WRITE_SETTINGS
                    && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
            ) {
                Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS).apply {
                    data = Uri.fromParts("package", packageName, null)
                }
            } else if (permission == android.Manifest.permission.SYSTEM_ALERT_WINDOW
                    && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
            ) {
                Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION).apply {
                    data = Uri.fromParts("package", packageName, null)
                }
            } else if (permission == PERMISSION_SYSTEM_NOTIFICATION
                    && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
            ) {
                Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                    putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                }
            } else if (permission == PERMISSION_GPS_OPEN
                    && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
            ) {
                Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).apply {
                    putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                }
            } else {
                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", packageName, null)
                }
            }
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
        startActivityForResult(intent, APP_SETTING_RC)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        setResult(resultCode, data)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (dialogFragment?.isVisible == true) {
            dialogFragment?.dismissAllowingStateLoss()
        }
    }

    companion object {
        private const val PERMISSION = "permission"
        private const val REASON = "reason"

        fun createIntent(
            context: Context,
            permission: String,
            reason: String
        ): Intent {
            return Intent(context, AppSettingsHolderActivity::class.java).apply {
                putExtra(PERMISSION, permission)
                putExtra(REASON, reason)
            }
        }
    }
}