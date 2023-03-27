package com.suifeng.sdk.network.model

import androidx.annotation.Keep

@Keep
data class DataResponse<out T>(val data: T) : BaseResponse()
