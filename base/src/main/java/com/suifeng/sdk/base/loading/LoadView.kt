package com.suifeng.sdk.base.loading

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.lifecycle.LifecycleOwner
import com.suifeng.sdk.base.R
import com.suifeng.sdk.base.livedata.PageState
import com.suifeng.sdk.base.vm.BaseViewModel

class LoadView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0):
    FrameLayout(context, attrs, defStyleAttr) {

    private var mLoadingView: View? = null

    private var mEmptyView: View? = null

    private var mErrorView: View? = null

    private var mState: PageState = PageState.Content

    private var mLoadStateListener: ILoadStateListener? = null

    private val mChildParams: LayoutParams by lazy {
        LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT).apply {
            gravity = Gravity.CENTER
        }
    }

    init {
        this.visibility = View.GONE //默认隐藏
        this.isClickable = true
        try {
            attrs?.let {
                val typeArray = context.obtainStyledAttributes(it, R.styleable.LoadView)
                val loadingLayoutId = typeArray.getResourceId(R.styleable.LoadView_loadingView, -1)
                val emptyLayoutId = typeArray.getResourceId(R.styleable.LoadView_emptyView, -1)
                val errorLayoutId = typeArray.getResourceId(R.styleable.LoadView_errorView, -1)
                typeArray.recycle()
                if(loadingLayoutId != -1) {
                    mLoadingView = LayoutInflater.from(context).inflate(loadingLayoutId, this, false)
                }
                if(emptyLayoutId != -1) {
                    mEmptyView = LayoutInflater.from(context).inflate(emptyLayoutId, this, false)
                }
                if(errorLayoutId != -1) {
                    mErrorView = LayoutInflater.from(context).inflate(errorLayoutId, this, false)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setLoadingView(view: View): LoadView {
        mLoadingView = view
        return this
    }

    fun setEmptyView(view: View): LoadView {
        mEmptyView = view
        return this
    }

    fun setErrorView(view: View): LoadView {
        mErrorView = view
        return this
    }

    fun observerViewModel(lifecycleOwner: LifecycleOwner, vm: BaseViewModel) {
        vm.pageState.observe(lifecycleOwner) {
            when(it) {
                PageState.Loading -> {
                    showLoading()
                }
                PageState.Empty -> {
                    showEmpty()
                }
                PageState.Error -> {
                    showError()
                }
                else -> {
                    mState = PageState.Content
                    this.visibility = View.GONE
                }
            }
        }
    }

    private fun showLoading() {
        if(mLoadingView == null ) return
        if(mState == PageState.Loading) return
        visible()
        if(this.childCount > 0) {
            this.removeAllViews()
        }
        mLoadingView?.let {
            mState = PageState.Loading
            this.addView(it, mChildParams)
            mLoadStateListener?.onStateChange(PageState.Loading, it)
        }
    }

    private fun showEmpty() {
        if(mEmptyView == null ) return
        if(mState == PageState.Empty) return
        visible()
        if(this.childCount > 0) {
            this.removeAllViews()
        }
        mEmptyView?.let {
            mState = PageState.Empty
            this.addView(it, mChildParams)
            mLoadStateListener?.onStateChange(PageState.Empty, it)
        }
    }

    private fun showError() {
        if(mErrorView == null ) return
        if(mState == PageState.Error) return
        visible()
        if(this.childCount > 0) {
            this.removeAllViews()
        }
        mErrorView?.let {
            mState = PageState.Error
            this.addView(it, mChildParams)
            mLoadStateListener?.onStateChange(PageState.Error, it)
        }
    }

    fun setOnLoadStateChangeListener(listener: ILoadStateListener) {
        this.mLoadStateListener = listener
    }

    private fun visible() {
        if(this.visibility == View.VISIBLE) return
        this.visibility = View.VISIBLE
    }
}