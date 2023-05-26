package com.suifeng.sdk.permission

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class PermissionData(
    val permission: String,
    val explain: AlwaysDeniedDesc?,
    var desc: PermissionDesc? = null
) : Parcelable

@Keep
@Parcelize
data class PermissionDesc(
    val title: String,
    val content: String,
    val buttonText: String? = null
) : Parcelable

@Keep
@Parcelize
data class AlwaysDeniedDesc(
    val message: String,
    val settingText: String? = null,
    val cancelText: String? = null
): Parcelable
