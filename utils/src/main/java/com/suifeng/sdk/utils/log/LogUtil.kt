package com.suifeng.sdk.utils.log

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy


object LogUtil {

    @JvmStatic
    fun init(
        tag: String, debug: Boolean,
        showThreadInfo: Boolean = true
    ) {
        val formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(showThreadInfo)
                .tag(tag)
                .build()
        val logAdapter = object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return debug
            }
        }
        Logger.addLogAdapter(logAdapter)
    }

    @JvmStatic
    fun d(@Nullable message: Any?) {
        Logger.d(message)
    }

    @JvmStatic
    fun d(@NonNull message: String, @Nullable vararg args: Any?) {
        Logger.d(message, args)
    }

    @JvmStatic
    fun i(@NonNull message: String, @Nullable vararg args: Any?) {
        Logger.i(message, args)
    }

    @JvmStatic
    fun v(@NonNull message: String, @Nullable vararg args: Any?) {
        Logger.v(message, args)
    }

    @JvmStatic
    fun e(@NonNull message: String, @Nullable vararg args: Any?) {
        Logger.e(message, args)
    }

    @JvmStatic
    fun w(@NonNull message: String, @Nullable vararg args: Any?) {
        Logger.w(message, args)
    }

    @JvmStatic
    fun wtf(@NonNull message: String, @Nullable vararg args: Any?) {
        Logger.wtf(message, args)
    }

    @JvmStatic
    fun json(@Nullable json: String?) {
        Logger.json(json)
    }

    @JvmStatic
    fun xml(@Nullable xml: String?) {
        Logger.xml(xml)
    }
}