package com.suifeng.demo.baseproject

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.suifeng.demo.baseproject.databinding.LayoutItemBinding
import com.suifeng.sdk.base.adapter.BaseAdapter
import com.suifeng.sdk.base.adapter.BaseViewHolder
import com.suifeng.sdk.utils.ext.click
import com.suifeng.sdk.utils.log.LogUtil

class LinearAdapter(context: Context): BaseAdapter<TestBean, LayoutItemBinding>(context) {

    override fun initViewBinding(parent: ViewGroup): LayoutItemBinding {
        return LayoutItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
    }

    override fun bindData(
        holder: BaseViewHolder<LayoutItemBinding>,
        item: TestBean,
        position: Int
    ) {
        holder.binding.tvName.text = item.index.toString()
        holder.binding.root.click {
            LogUtil.i("item --- click --- position:${position}")
        }
    }

    fun setNewData(dataList: List<TestBean>) {
        if(dataList.isNotEmpty()) {
            list.clear()
            list.addAll(dataList)
            notifyDataSetChanged()
        }
    }

    fun getDataSize(): Int {
        return list.size
    }
}