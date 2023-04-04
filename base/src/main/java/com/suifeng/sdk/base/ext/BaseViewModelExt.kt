package com.suifeng.sdk.base.ext

import com.suifeng.sdk.base.livedata.PageState
import com.suifeng.sdk.base.vm.BaseViewModel

fun BaseViewModel.showLoading(msg: String = "") {
    pageState.value = PageState.Loading.apply {
        mMessage = msg
    }
}

fun BaseViewModel.showEmpty(msg: String = "") {
    pageState.value = PageState.Empty.apply {
        mMessage = msg
    }
}

fun BaseViewModel.showError(code: String = "", msg: String = "") {
    pageState.value = PageState.Error.apply {
        mCode = code
        mMessage = msg
    }
}

fun BaseViewModel.showSuccess() {
    pageState.value = PageState.Content
}