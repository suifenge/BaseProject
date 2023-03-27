package com.suifeng.sdk.permission

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class PermissionData(
    val permission: String,
    val explain: String,
    var desc: PermissionDesc? = null
) : Parcelable

@Keep
@Parcelize
data class PermissionDesc(
    val title: String,
    val content: String
) : Parcelable
