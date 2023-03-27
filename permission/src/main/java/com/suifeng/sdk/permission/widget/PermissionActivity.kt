package com.suifeng.sdk.permission.widget

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.Keep
import androidx.appcompat.app.AppCompatActivity
import com.suifeng.sdk.permission.PermissionCallback
import com.suifeng.sdk.permission.PermissionData
import com.suifeng.sdk.permission.handleRequestPermission
import kotlinx.android.parcel.Parcelize

class PermissionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(0, 0)
        val inputData = intent.getParcelableExtra<InputData>(INPUT_DATA)
        inputData?.let {
            handleRequestPermission(
                this,
                it.permissions
            ) { granted, denied ->
                finish()
                globalCallback?.let {
                    it(granted, denied)
                }
            }
        } ?: finish()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, 0)
    }

    companion object {
        private const val INPUT_DATA = "input_data"
        private var globalCallback: PermissionCallback? = null

        fun startIntent(
            context: Context,
            permissions: List<PermissionData>,
            callback: PermissionCallback
        ) {
            globalCallback = callback
            val inputData = InputData(permissions)
            context.startActivity(Intent(context, PermissionActivity::class.java).apply {
                putExtra(INPUT_DATA, inputData)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            })
        }
    }
}

@Keep
@Parcelize
data class InputData(
    val permissions: List<PermissionData>
) : Parcelable