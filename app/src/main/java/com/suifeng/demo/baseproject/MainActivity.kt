package com.suifeng.demo.baseproject

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.suifeng.demo.baseproject.databinding.ActivityMainBinding
import com.suifeng.sdk.base.livedata.PageState
import com.suifeng.sdk.base.loading.ILoadStateListener
import com.suifeng.sdk.base.ui.BaseActivity
import com.suifeng.sdk.utils.Utils
import kotlin.random.Random.Default.nextInt

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val viewModel: TestViewModel by viewModels()

    override fun initViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun init(savedInstanceState: Bundle?) {
        Utils.init(this.application)

        mBinding.loadView.apply {
            observerViewModel(this@MainActivity, viewModel)
            setOnLoadStateChangeListener(object: ILoadStateListener {
                override fun onStateChange(state: PageState, view: View) {

                }

            })
        }

        mBinding.changeStateBtn

        mBinding.changeStateBtn.setOnClickListener {
            val index = nextInt(4)
            viewModel.loadData(index)
        }
    }

}