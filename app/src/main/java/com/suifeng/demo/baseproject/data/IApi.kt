package com.suifeng.demo.baseproject.data

import com.suifeng.sdk.network.model.DataResponse
import retrofit2.http.*

interface IApi {

    @GET("getAllPackInfo")
    suspend fun getPackageInfo(@Query("package") packageName: String): DataResponse<MyPackageInfo>
}