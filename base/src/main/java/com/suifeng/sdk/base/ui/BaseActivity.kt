package com.suifeng.sdk.base.ui

import android.os.Bundle
import androidx.annotation.Keep
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.suifeng.sdk.base.R
import com.suifeng.sdk.base.vm.BaseViewModel

/**
 * @author ljc
 * @data 2018/6/19
 * @describe
 */
abstract class BaseActivity<V : ViewDataBinding, VM : BaseViewModel>(
    //自定义布局的id
    private val layoutResId: Int
) : AppCompatActivity() {

    protected lateinit var binding: V

    protected lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        if (toggleOverridePendingTransition()) {
            when (getOverridePendingTransitionMode()) {
                TransitionMode.LEFT -> {
                    overridePendingTransition(R.anim.left_in, R.anim.left_out)
                }
                TransitionMode.RIGHT -> {
                    overridePendingTransition(R.anim.right_in, R.anim.right_out)
                }
                TransitionMode.TOP -> {
                    overridePendingTransition(R.anim.top_in, R.anim.top_out)
                }
                TransitionMode.BOTTOM -> {
                    overridePendingTransition(R.anim.bottom_in, R.anim.bottom_out)
                }
                TransitionMode.SCALE -> {
                    overridePendingTransition(R.anim.scale_in, R.anim.scale_out)
                }
                TransitionMode.FADE -> {
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                }
                TransitionMode.NONE -> {
                    overridePendingTransition(R.anim.anim_none, R.anim.anim_none)
                }
            }
        }
        beforeSuperCreate(savedInstanceState)
        super.onCreate(savedInstanceState)
        AppManager.get().addActivity(this)
        initViewDataBinding()
        viewModel = getViewModelInstance(aspectViewModelClass())
        //初始化Activity
        init(savedInstanceState)
    }

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

    /**
     * 注入绑定
     */
    private fun initViewDataBinding() {
        //DataBindingUtil类需要在project的build中配置 dataBinding {enabled true }, 同步后会自动关联android.databinding包
        binding = DataBindingUtil.setContentView(this, layoutResId)
        binding.lifecycleOwner = this //绑定LiveData并对Binding设置LifecycleOwner
    }

    open fun beforeSuperCreate(savedInstanceState: Bundle?) {}

    abstract fun init(savedInstanceState: Bundle?)

    override fun finish() {
        super.finish()
        AppManager.get().removeActivity(this)
        if (toggleOverridePendingTransition()) {
            when (getOverridePendingTransitionMode()) {
                TransitionMode.LEFT -> {
                    overridePendingTransition(R.anim.left_in, R.anim.left_out)
                }
                TransitionMode.RIGHT -> {
                    overridePendingTransition(R.anim.right_in, R.anim.right_out)
                }
                TransitionMode.TOP -> {
                    overridePendingTransition(R.anim.top_in, R.anim.top_out)
                }
                TransitionMode.BOTTOM -> {
                    overridePendingTransition(R.anim.bottom_in, R.anim.bottom_out)
                }
                TransitionMode.SCALE -> {
                    overridePendingTransition(R.anim.scale_in, R.anim.scale_out)
                }
                TransitionMode.FADE -> {
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                }
                TransitionMode.NONE -> {
                    overridePendingTransition(R.anim.anim_none, R.anim.anim_none)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }

    open fun toggleOverridePendingTransition(): Boolean {
        return false
    }

    open fun getOverridePendingTransitionMode(): TransitionMode? {
        return null
    }

    @Keep
    enum class TransitionMode {
        LEFT, RIGHT, TOP, BOTTOM, SCALE, FADE, NONE
    }
}