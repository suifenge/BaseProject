package com.suifeng.demo.baseproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.suifeng.demo.baseproject.databinding.*
import com.suifeng.sdk.base.ui.BaseActivity
import com.suifeng.sdk.utils.Utils
import com.suifeng.sdk.utils.ext.click
import com.suifeng.sdk.utils.log.LogUtil

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val viewModel: TestViewModel by viewModels()

    private val linearAdapter: LinearAdapter by lazy {
        LinearAdapter(this)
    }

    private val mHeader1: View by lazy {
        LayoutHeader1Binding.inflate(
            LayoutInflater.from(this)
        ).root
    }
    private val mHeader2: View by lazy {
        LayoutHeader2Binding.inflate(
            LayoutInflater.from(this)
        ).root
    }
    private var headerCount = 0

    private val mFooter1: View by lazy {
        LayoutFooter1Binding.inflate(
            LayoutInflater.from(this)
        ).root
    }
    private val mFooter2: View by lazy {
        LayoutFooter2Binding.inflate(
            LayoutInflater.from(this)
        ).root
    }
    private var footerCount = 0

    private var isLinear: Boolean = true

    override fun initViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun init(savedInstanceState: Bundle?) {
        Utils.init(this.application)
        LogUtil.init("wtf", true)
        initButton()
        initRv()
    }

    private fun initButton() {
        mBinding.btnAddHeader.click {
            if(headerCount == 0) {
                linearAdapter.addHeader(mHeader1)
            } else if(headerCount == 1) {
                linearAdapter.addHeader(mHeader2)
            }
            headerCount = linearAdapter.getHeaderCount()
        }
        mBinding.btnDelHeader.click {
            if (headerCount == 2) {
                linearAdapter.removeHeader(mHeader2)
            } else if(headerCount == 1) {
                linearAdapter.removeHeader(mHeader1)
            }
            headerCount = linearAdapter.getHeaderCount()
        }
        mBinding.btnAddFooter.click {
            if(footerCount == 0) {
                linearAdapter.addFooter(mFooter1)
            } else if(footerCount == 1) {
                linearAdapter.addFooter(mFooter2)
            }
            footerCount = linearAdapter.getFooterCount()
        }
        mBinding.btnDelFooter.click {
            if(footerCount == 2) {
                linearAdapter.removeFooter(mFooter2)
            } else if(footerCount == 1) {
                linearAdapter.removeFooter(mFooter1)
            }
            footerCount = linearAdapter.getFooterCount()
        }
        mBinding.btnGrid.click {
            mBinding.rv.apply {
                layoutManager = if(isLinear) {
                    isLinear = false
                    GridLayoutManager(this@MainActivity, 2)
                } else {
                    isLinear = true
                    LinearLayoutManager(this@MainActivity)
                }
                adapter = linearAdapter
            }
        }
    }

    private fun initRv() {
        val dataList = mutableListOf<TestBean>()
        for (i in 0 .. 20) {
            val bean = TestBean(i)
            dataList.add(bean)
        }
        linearAdapter.setNewData(dataList)
        headerCount = linearAdapter.getHeaderCount()
        footerCount = linearAdapter.getFooterCount()
        mBinding.rv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = linearAdapter
        }
    }

}