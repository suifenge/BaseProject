package com.suifeng.sdk.base.livedata

import androidx.annotation.Keep

/**
 * 页面状态值
 */
@Keep
sealed class PageState {

    @Keep
    class Loading() : PageState()

    @Keep
    class Empty() : PageState()

    @Keep
    class Content() : PageState()

    @Keep
    class Error() : PageState()
}