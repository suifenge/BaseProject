package com.suifeng.sdk.permission

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class PermissionData(
    val permission: String,
    val explain: String,
    var desc: PermissionDesc? = null,
    var alwaysShow: Boolean = true  //控制权限弹框说明不会一直提示用户
) : Parcelable

@Keep
@Parcelize
data class PermissionDesc(
    val title: String,
    val content: String
) : Parcelable
