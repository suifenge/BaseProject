package com.suifeng.demo.baseproject

import androidx.lifecycle.viewModelScope
import com.suifeng.demo.baseproject.data.RetrofitFactory
import com.suifeng.sdk.base.ext.showEmpty
import com.suifeng.sdk.base.ext.showError
import com.suifeng.sdk.base.ext.showLoading
import com.suifeng.sdk.base.ext.showSuccess
import com.suifeng.sdk.base.vm.BaseViewModel
import com.suifeng.sdk.network.IApiErrorCallback
import com.suifeng.sdk.network.apiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TestViewModel: BaseViewModel() {


    fun loadData(mode: Int) {
        viewModelScope.launch {
            showLoading()
            withContext(Dispatchers.IO) {
                delay(2000)
            }
            when(mode) {
                1 -> {
                    showSuccess()
                }
                2 -> {
                    showEmpty()
                }
                3 -> {
                    showError("404", "not found")
                }
                else -> {
                    showSuccess()
                }
            }
        }
    }

    fun getInfo() {
        viewModelScope.launch {
            val response = apiCall(object: IApiErrorCallback {
                override fun onError(code: String, msg: String) {

                }

            }) {
                RetrofitFactory.mApi.getPackageInfo("com.ylyd.junksweeper")
            }
            response?.let {

            }
        }
    }

}