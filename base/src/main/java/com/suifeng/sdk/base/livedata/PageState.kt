package com.suifeng.sdk.base.livedata

import androidx.annotation.Keep

/**
 * 页面状态值
 */
@Keep
sealed class PageState {

    var mCode: String = ""
    var mMessage: String = ""

    object Loading : PageState()

    object Empty : PageState()

    object Content : PageState()

    object Error : PageState()
}