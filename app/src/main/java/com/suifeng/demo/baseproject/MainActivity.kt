package com.suifeng.demo.baseproject

import android.os.Bundle
import androidx.activity.viewModels
import com.suifeng.demo.baseproject.databinding.ActivityMainBinding
import com.suifeng.sdk.base.ui.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val viewModel: TestViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun initViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun init(savedInstanceState: Bundle?) {

    }
}