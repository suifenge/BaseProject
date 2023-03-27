package com.suifeng.sdk.base.livedata

import androidx.annotation.IntDef
import com.suifeng.sdk.base.livedata.RecyclerViewState.Companion.LOAD_MORE
import com.suifeng.sdk.base.livedata.RecyclerViewState.Companion.LOAD_MORE_FAILED
import com.suifeng.sdk.base.livedata.RecyclerViewState.Companion.NORMAL
import com.suifeng.sdk.base.livedata.RecyclerViewState.Companion.NO_MORE_DATA
import com.suifeng.sdk.base.livedata.RecyclerViewState.Companion.REFRESH
import com.suifeng.sdk.base.livedata.RecyclerViewState.Companion.REFRESH_FAILED

@IntDef(NORMAL, REFRESH, REFRESH_FAILED, LOAD_MORE, LOAD_MORE_FAILED, NO_MORE_DATA)
@Retention(AnnotationRetention.SOURCE)
annotation class RecyclerViewState {
    companion object {
        const val NORMAL = 0        //初始状态
        const val REFRESH = 1       //下拉刷新结束
        const val REFRESH_FAILED = 2    //下拉刷新失败
        const val LOAD_MORE = 3         //上拉加载结束
        const val LOAD_MORE_FAILED = 4  //上拉加载失败
        const val NO_MORE_DATA = 5      //没有更多数据
    }
}