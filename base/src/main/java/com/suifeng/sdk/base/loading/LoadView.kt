package com.suifeng.sdk.base.loading

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import com.suifeng.sdk.base.R
import com.suifeng.sdk.base.livedata.PageState
import com.suifeng.sdk.base.vm.BaseViewModel
import com.suifeng.sdk.utils.ext.gone
import com.suifeng.sdk.utils.ext.visible
import com.suifeng.sdk.utils.log.LogUtil

class LoadView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0):
    FrameLayout(context, attrs, defStyleAttr) {

    private var mLoadingViewBinding: ViewBinding? = null

    private var mEmptyViewBinding: ViewBinding? = null

    private var mErrorViewBinding: ViewBinding? = null

    private var isTransparentBg: Boolean = false        //是否为透明背景

    init {

        this.gone() //默认隐藏
        try {
            attrs?.let {
                val typeArray = context.obtainStyledAttributes(it, R.styleable.LoadView)
                isTransparentBg = typeArray.getBoolean(R.styleable.LoadView_isTransparent, false)
                val loadingLayoutId = typeArray.getResourceId(R.styleable.LoadView_loadingView, -1)
                val emptyLayoutId = typeArray.getResourceId(R.styleable.LoadView_emptyView, -1)
                val errorLayoutId = typeArray.getResourceId(R.styleable.LoadView_errorView, -1)
                typeArray.recycle()
                if(loadingLayoutId != -1) {
//                    mLoadingViewBinding =
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun observerViewModel(lifecycleOwner: LifecycleOwner, vm: BaseViewModel) {
        vm.pageState.observe(lifecycleOwner) {
            when(it) {
                PageState.Loading() -> {
                    showLoading()
                }
                PageState.Empty() -> {

                }
                PageState.Error() -> {

                }
                else -> {
                    this.gone()
                }
            }
        }
    }

    private fun showLoading() {
        if(mLoadingViewBinding == null ) return
        this.visible()

    }
}