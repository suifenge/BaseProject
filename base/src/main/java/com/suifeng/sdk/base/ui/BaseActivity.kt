package com.suifeng.sdk.base.ui

import android.os.Bundle
import androidx.annotation.Keep
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.suifeng.sdk.base.R

/**
 * @author ljc
 * @data 2018/6/19
 * @describe
 */
abstract class BaseActivity<V : ViewBinding>: AppCompatActivity() {

    protected val mBinding: V by lazy {
        initViewBinding()
    }

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
        setContentView(mBinding.root)
        //初始化Activity
        init(savedInstanceState)
    }

    abstract fun initViewBinding(): V

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