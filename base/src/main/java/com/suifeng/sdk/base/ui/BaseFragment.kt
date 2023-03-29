package com.suifeng.sdk.base.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * @author ljc
 * @data 2018/6/20
 * @describe
 */
abstract class BaseFragment<V : ViewBinding>: Fragment() {

    private var mRootView: View? = null
    private var isInit = false
    protected lateinit var mBinding: V

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mRootView == null) {
            mBinding =initViewBinding(inflater, container)
            mRootView = mBinding.root
        }
        if (!isInit) {
            isInit = true
            initView(savedInstanceState)
        }
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData(savedInstanceState)
    }

    abstract fun initViewBinding(inflater: LayoutInflater, container: ViewGroup?): V

    /**
     * init view on create fragment view
     */
    abstract fun initView(savedInstanceState: Bundle?)

    /**
     * init view data on view created
     */
    abstract fun initData(savedInstanceState: Bundle?)
}