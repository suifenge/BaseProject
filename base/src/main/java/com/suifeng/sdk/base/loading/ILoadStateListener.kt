package com.suifeng.sdk.base.loading

import android.view.View
import com.suifeng.sdk.base.livedata.PageState

interface ILoadStateListener {

    fun onStateChange(state: PageState, view: View)
}