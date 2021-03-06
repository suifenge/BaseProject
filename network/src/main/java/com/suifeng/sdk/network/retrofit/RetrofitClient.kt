package com.suifeng.sdk.network.retrofit

import com.suifeng.sdk.network.interceptor.HeaderInterceptor
import com.suifeng.sdk.network.log.HttpLogger
import com.suifeng.sdk.network.ssl.CustomX509TrustManager
import com.suifeng.sdk.utils.log.LogUtil
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author ljc
 * @data 2018/6/22
 * @describe
 */
class RetrofitClient {
    companion object {

        private const val LOG_TAG = "RetrofitClient"

        /**
         * 初始化创建Retrofit 对象
         */
        fun <T> createRetrofit(builder: Builder<T>, cls: Class<T>): T {
            // 构建 log
            val loggingInterceptor = if (builder.debug) {
                HttpLoggingInterceptor(HttpLogger())
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                null
            }

            //初始化OkHttp
            val okHttpBuilder = OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)
                    .readTimeout(builder.readTimeout, TimeUnit.SECONDS)
                    .writeTimeout(builder.readTimeout, TimeUnit.SECONDS)
                    .connectTimeout(builder.connectionTimeout, TimeUnit.SECONDS)
            //ssl
            val trustManager = CustomX509TrustManager()
            okHttpBuilder.sslSocketFactory(trustManager.getSSLContext().socketFactory, trustManager)
            // header
            okHttpBuilder.addInterceptor(HeaderInterceptor(builder.headers))
            // log
            loggingInterceptor?.apply {
                level = HttpLoggingInterceptor.Level.BODY
                okHttpBuilder.addInterceptor(this)
            }
            builder.interceptor?.apply {
                okHttpBuilder.addInterceptor(this)
            }
            // 构建
            return Retrofit.Builder()
                    //增加返回值为String的支持
                    .addConverterFactory(ScalarsConverterFactory.create())
                    //增加返回值为Gson的支持(以实体类返回)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpBuilder.build())
                    // 多域名
                    .baseUrl(builder.baseUrl)
                    .build()
                    .create(cls)
        }

        /**
         * 如果外部不使用LogUtil打印日志时需要手动初始化下网络库里面的日志打印
         */
        fun initLog(tag: String? = null) {
            LogUtil.init(tag ?: LOG_TAG, true)
        }
    }


    /**
     * Builder 模式构建配置
     */
    class Builder<out T>(
        private val cls: Class<T>,
        // 是否开启调试输出
        val debug: Boolean = false,
        // 当前http网络请求
        val baseUrl: String,
        // 读取超时 - 默认6秒
        val readTimeout: Long = 15,
        // 超时链接 - 默认6秒
        val connectionTimeout: Long = 15,
        // 头部
        val headers: () -> HashMap<String, String>,
        //业务拦截器
        val interceptor: Interceptor? = null
    ) {

        fun create() = createRetrofit(this, cls)
    }
}