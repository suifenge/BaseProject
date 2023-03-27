package com.suifeng.sdk.network

interface IApiErrorCallback {
    fun onError(code: String, msg: String)
}