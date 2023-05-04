package com.suifeng.demo.baseproject.data

import com.suifeng.demo.baseproject.BuildConfig
import com.suifeng.sdk.network.retrofit.RetrofitClient

object RetrofitFactory {

    val mApi = RetrofitClient.Builder(
        cls = IApi::class.java,
        debug = BuildConfig.DEBUG,
        baseUrl = "http://192.253.230.44/",
        readTimeout = 20,
        connectionTimeout = 20,
        headers = {
            hashMapOf()
        }
    ).create()
}