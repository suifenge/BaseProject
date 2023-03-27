package com.suifeng.sdk.network.model

import androidx.annotation.Keep

@Keep
open class BaseResponse(
    var success: Boolean = false,
    var code: String = "",
    var msg: String = "",
    var httpStatusCode: Int = 0,
    var httpStatusMsg: String = ""
)
