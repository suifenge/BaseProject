package com.suifeng.sdk.permission.widget

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.suifeng.sdk.dialog.HintDialog
import com.suifeng.sdk.permission.PermissionCallback
import com.suifeng.sdk.permission.PermissionConstant.APP_SETTING_RC
import com.suifeng.sdk.permission.PermissionData
import com.suifeng.sdk.permission.hasPermission
import java.util.LinkedList

internal class PermissionFragment : Fragment(), ActivityResultCallback<Map<String, Boolean>> {

    var callback: PermissionCallback? = null

    var permissions: List<PermissionData>? = null

    private val grantedPermissions = mutableListOf<PermissionData>()
    private val deniedPermissions = mutableListOf<PermissionData>()
    private val alwaysDeniedPermissions = mutableListOf<PermissionData>()

    private val unHandledDeniedPermissions = LinkedList<String>()

    private var appSettingPermission = ""
    private val requestPermissionLauncher: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissions?.forEach {
            if (hasPermission(it.permission)) {
                grantedPermissions.add(it)
            } else {
                deniedPermissions.add(it)
            }
        }
        if (deniedPermissions.isEmpty()) {
            handleCallback()
            return
        }
        for (p in deniedPermissions) {
            unHandledDeniedPermissions.add(p.permission)
        }
        deniedPermissions.clear()
        handleNextPermission()
    }

    private fun handleNextPermission() {
        if (unHandledDeniedPermissions.isNotEmpty()) {
            requestUnHandledPermission()
        } else {
            handleCallback()
        }
    }

    private fun requestUnHandledPermission() {
        val permission = unHandledDeniedPermissions.poll()
        permission?.let {
            val permissionData = getPermissionData(it)
            if (hasPermission(permission)) {
                // 有可能该权限已经授权了（比如读、写sdcard同属于一个权限组的两个权限都申请时，只有一个权限弹窗）
                grantedPermissions.add(permissionData)
                handleNextPermission()
            } else {
                //是否需要展示说明申请用途弹窗
                if (permissionData.desc != null) {
                    //弹出说明对话框
                    showPermissionDescriptionDialog(permissionData)
                } else {
                    //申请权限
                    requestPermissionLauncher.launch(arrayOf(permission))
                }
            }
        } ?: handleCallback()
    }

    private fun handleCallback() {
        callback?.let {
            it(grantedPermissions, deniedPermissions)
        }
    }

    private fun getPermissionData(permission: String): PermissionData {
        permissions?.forEach {
            if (it.permission == permission) {
                return it
            }
        }
        return PermissionData(permission, "")
    }

    private fun showPermissionDescriptionDialog(permissionData: PermissionData) {
        val desc = permissionData.desc
        desc?.let {
            HintDialog.newInstance(
                it.content, it.title,
                cancelText = null
            ).show(this, {
                requestPermissionLauncher.launch(arrayOf(permissionData.permission))
            })
        } ?: requestPermissionLauncher.launch(arrayOf(permissionData.permission))
    }

    override
    fun onActivityResult(result: Map<String, Boolean>?) {
        result?.let {
            for (permission in it.keys) {
                if (it[permission] == true) {
                    grantedPermissions.add(getPermissionData(permission))
                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                                requireActivity(), permission
                            )
                    ) {
                        alwaysDeniedPermissions.add(getPermissionData(permission))
                        //系统说明弹窗
                        handleShowRationaleAndSettings()
                        return
                    } else {
                        deniedPermissions.add(getPermissionData(permission))
                        // 有一个失败了，就不走后续权限申请，并将其添加到拒绝列表中，
                        // 避免出现过度索取权限，导致隐私不合规的问题
                        if (unHandledDeniedPermissions.isNotEmpty()) {
                            deniedPermissions.addAll(getUnHandlePermission())
                        }
                        handleCallback()
                        return
                    }
                }
            }
            // 看是否仍然有未处理的权限，有的话进行下一个权限的处理
            handleNextPermission()
        } ?: handleCallback()
    }

    /**
     * 点击拒绝不再提醒的解释弹窗
     */
    private fun handleShowRationaleAndSettings() {
        if (alwaysDeniedPermissions.isEmpty()) {
            handleNextPermission()
        } else {
            val permissionData = alwaysDeniedPermissions.removeAt(0)
            appSettingPermission = permissionData.permission
            val intent = AppSettingsHolderActivity.createIntent(
                requireContext(),
                appSettingPermission,
                permissionData.explain
            )
            startActivityForResult(intent, APP_SETTING_RC)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == APP_SETTING_RC) {
            if (hasPermission(appSettingPermission)) {
                grantedPermissions.add(getPermissionData(appSettingPermission))
            } else {
                deniedPermissions.add(getPermissionData(appSettingPermission))
            }
            handleNextPermission()
        }
    }

    /**
     * 获取剩余未处理的权限列表
     */
    private fun getUnHandlePermission(): List<PermissionData> {
        val result = mutableListOf<PermissionData>()
        while (unHandledDeniedPermissions.isNotEmpty()) {
            val permission = unHandledDeniedPermissions.poll()
            if (permission != null) {
                result.add(getPermissionData(permission))
            } else {
                return result
            }
        }
        return result
    }
}