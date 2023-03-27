package com.suifeng.sdk.base.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.suifeng.sdk.base.vm.BaseViewModel

/**
 * @author ljc
 * @data 2018/6/20
 * @describe
 */
abstract class BaseFragment<V : ViewDataBinding, VM : BaseViewModel>(
    private val layoutResId: Int
) : Fragment() {

    private var mRootView: View? = null
    private var isInit = false
    protected lateinit var binding: V
    protected lateinit var viewModel: VM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mRootView == null) {
            binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
            binding.lifecycleOwner = this
            mRootView = binding.root
        }
        if (!isInit) {
            isInit = true
            viewModel = getViewModelInstance(aspectViewModelClass())
            init(mRootView, savedInstanceState)
        }
        return mRootView
    }

    /**
     * 初始化
     */
    abstract fun init(rootView: View?, savedInstanceState: Bundle?)

    /**
     * 当前ViewModel的Class
     */
    abstract fun aspectViewModelClass(): Class<VM>

    /**
     * 获取ViewModel实例
     */
    private fun getViewModelInstance(action: Class<VM>): VM {
        return ViewModelProvider(this)[action]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
    }
}