package com.suifeng.demo.baseproject.data

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class MyPackageInfo(
    @SerializedName("package")
    val packageName: String,
    val pluginInfo: MyPluginInfo
)

@Keep
data class MyPluginInfo(
    val pluginPackage: String,
    val downloadUrl: String
)

