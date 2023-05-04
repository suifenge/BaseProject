package com.suifeng.sdk.network.log

import com.suifeng.sdk.utils.log.LogUtil
import okhttp3.logging.HttpLoggingInterceptor

class HttpLogger: HttpLoggingInterceptor.Logger {

    private val mMessage = StringBuilder()

    override fun log(message: String) {
        var mMsg = message
        if(mMsg.startsWith("--> POST")) {
            mMessage.setLength(0)
        }
        // 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
        if((mMsg.startsWith("{") && mMsg.endsWith("}"))
            || (mMsg.startsWith("[") && mMsg.endsWith("]"))) {
            mMsg = JsonUtil.formatJson(mMsg)
        }
        mMessage.append(mMsg+"\n")
        if(mMsg.startsWith("<-- END HTTP")) {
            LogUtil.d(mMessage.toString())
        }
    }
}