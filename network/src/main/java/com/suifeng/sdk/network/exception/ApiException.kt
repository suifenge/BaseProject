package com.suifeng.sdk.network.exception

/**
 * 服务器非200状态，对应异常
 */
class ApiException(
    val code: String,
    message: String
) : RuntimeException(message) {

    private val params = mutableMapOf<String, Any>()

    fun param(key: String, value: Any): ApiException {
        params[key] = value
        return this
    }

    fun allParams(): Map<String, Any> {
        return params
    }

    fun hasParams(): Boolean = params.isNotEmpty()

}