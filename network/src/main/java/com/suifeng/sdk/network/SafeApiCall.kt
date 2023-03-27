package com.suifeng.sdk.network

import com.suifeng.sdk.network.exception.ApiException
import com.suifeng.sdk.network.model.DataResponse
import com.suifeng.sdk.utils.display.getStringFromResource
import com.suifeng.sdk.utils.log.LogUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

const val COMMON_ERROR = "Common_Error"
const val NO_NETWORK_ERROR = "No_Network_Error"

suspend inline fun <T> apiCall(
    callback: IApiErrorCallback,
    crossinline responseFunction: suspend () -> T
): T? {
    return try {
        val response = withContext(Dispatchers.IO) {
            responseFunction.invoke()
        }
        response as DataResponse<*>
        if (response.success && response.code != "0") {
            throw ApiException(response.code, response.msg)
        }
        response
    } catch (e: Throwable) {
        LogUtil.d(e)
        withContext(Dispatchers.Main) {
            when (e) {
                is HttpException -> {
                    callback.onError(
                        COMMON_ERROR,
                        getStringFromResource(R.string.server_exception)
                    )
                }
                is ApiException -> {
                    callback.onError(e.code, e.message ?: "")
                }
                is IOException -> {
                    callback.onError(
                        NO_NETWORK_ERROR,
                        getStringFromResource(R.string.network_error_exception)
                    )
                }
                else -> {
                    callback.onError(
                        COMMON_ERROR,
                        getStringFromResource(R.string.server_exception)
                    )
                }
            }
        }
        null
    }
}