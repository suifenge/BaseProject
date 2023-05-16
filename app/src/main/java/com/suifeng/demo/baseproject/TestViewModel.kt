package com.suifeng.demo.baseproject

import androidx.lifecycle.viewModelScope
import com.suifeng.sdk.base.ext.showEmpty
import com.suifeng.sdk.base.ext.showError
import com.suifeng.sdk.base.ext.showLoading
import com.suifeng.sdk.base.ext.showSuccess
import com.suifeng.sdk.base.vm.BaseViewModel
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
}