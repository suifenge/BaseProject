package com.suifeng.sdk.base.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.suifeng.sdk.base.livedata.PageState
import com.suifeng.sdk.base.livedata.RecyclerViewState

/**
 * @author ljc
 * @data 2018/8/6
 * @describe
 */
open class BaseViewModel : ViewModel() {

    /**
     * 页面状态LiveData
     */
    val pageState: MutableLiveData<PageState> = MutableLiveData()

    /**
     * RecyclerView刷新状态
     */
    @RecyclerViewState
    val refreshState: MutableLiveData<Int> = MutableLiveData()

    /**
     * 当ViewModel回调onCleared时暴露出去给需要的组件使用
     */
    var onViewModelClearedListener: (() -> Unit)? = null

    override fun onCleared() {
        super.onCleared()
        onViewModelClearedListener?.invoke()
    }
}