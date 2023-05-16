package com.suifeng.sdk.base.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<T, VB: ViewBinding>(context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_HEADER = -100
        const val VIEW_TYPE_FOOTER = -101
    }

    val list: ArrayList<T> = ArrayList()

    private val layoutInflater = LayoutInflater.from(context)

    private var mHeaderLayout: LinearLayout? = null
    private var mFooterLayout: LinearLayout? = null

    override fun getItemViewType(position: Int): Int {
        mHeaderLayout?.let {    //Header布局只有一个且必须是第0位
            if(position == 0 && getHeaderCount() > 0) return VIEW_TYPE_HEADER
        }
        mFooterLayout?.let {//Footer布局是在最末的位置上
            if(position == getHeaderLayoutCount() + list.size) return VIEW_TYPE_FOOTER
        }
        return getDefItemViewType(position - getHeaderLayoutCount())   //取在数据中的下标
    }

    open fun getDefItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            VIEW_TYPE_HEADER -> {
                createCommonViewHolder(mHeaderLayout!!)
            }
            VIEW_TYPE_FOOTER -> {
                createCommonViewHolder(mFooterLayout!!)
            }
            else -> {
                createBindingViewHolder(parent, viewType)
            }
        }
    }

    private fun createCommonViewHolder(itemView: View): CommonViewHolder {
        return CommonViewHolder(itemView)
    }

    /**
     * 数据项多布局时可以重写这个方法实现，并重写getDefItemViewType方法
     */
    open fun createBindingViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<VB> {
        return BaseViewHolder(initViewBinding(parent))
    }

    override fun getItemCount(): Int {
        return list.size + getHeaderLayoutCount() + getFooterLayoutCount()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)) {
            VIEW_TYPE_HEADER -> {}  //undo
            VIEW_TYPE_FOOTER -> {}  //undo
            else -> {
                onBindBindingViewHolder(holder as BaseViewHolder<VB>, position - getHeaderLayoutCount())
            }
        }
    }

    open fun onBindBindingViewHolder(holder: BaseViewHolder<VB>, position: Int) {
        bindData(holder, list[position], position)
    }

    abstract fun initViewBinding(parent: ViewGroup): VB

    abstract fun bindData(holder: BaseViewHolder<VB>, item: T, position: Int)

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val layoutManager = recyclerView.layoutManager
        if (layoutManager is GridLayoutManager) {
            layoutManager.spanSizeLookup = object: GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val viewType = getItemViewType(position)
                    if(isFixedViewType(viewType)) {
                        return layoutManager.spanCount
                    }
                    return 1
                }
            }
        }
    }

    private fun isFixedViewType(viewType: Int): Boolean {
        return when(viewType) {
            VIEW_TYPE_HEADER, VIEW_TYPE_FOOTER -> true
            else -> false
        }
    }


    fun addHeader(view: View, index: Int = -1): Int {
        if(mHeaderLayout == null) {
            mHeaderLayout = LinearLayout(view.context).apply {
                orientation = LinearLayout.VERTICAL
                layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            }
        }
        var insetIndex = 0
        mHeaderLayout?.let {
            val childCount = it.childCount
            insetIndex = if(index < 0 || index > childCount) {
                childCount
            } else {
                index
            }
            it.addView(view, insetIndex)
            if(it.childCount == 1) {
                val position = getHeaderViewPosition()
                if(position != -1) {
                    notifyItemInserted(position)
                }
            }
        }
        return insetIndex
    }

    fun addFooter(view: View, index: Int = -1): Int {
        if(mFooterLayout == null) {
            mFooterLayout = LinearLayout(view.context).apply {
                orientation = LinearLayout.VERTICAL
                layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            }
        }
        var insertIndex = 0
        mFooterLayout?.let {
            val childCount = it.childCount
            insertIndex = if(index < 0 || index > childCount) {
                childCount
            } else {
                index
            }
            it.addView(view, insertIndex)
            if(it.childCount == 1) {
                val position = getFooterViewPosition()
                if(position != -1) {
                    notifyItemInserted(position)
                }
            }
        }
        return insertIndex
    }

    fun removeHeader(view: View) {
        mHeaderLayout?.let {
            it.removeView(view)
            if(it.childCount == 0) {
                val position = getHeaderViewPosition()
                if(position != -1) {
                    notifyItemRemoved(position)
                }
            }
        }
    }

    fun removeFooter(view: View) {
        mFooterLayout?.let {
            it.removeView(view)
            if(it.childCount == 0) {
                val position = getFooterViewPosition()
                if(position != -1) {
                    notifyItemRemoved(position)
                }
            }
        }
    }

    /**
     * 获取是否添加了header布局
     */
    private fun getHeaderLayoutCount(): Int {
        return mHeaderLayout?.let {
            if(getHeaderCount() > 0) 1 else 0
        } ?: 0
    }

    /**
     * 获取是否添加了Footer布局
     */
    private fun getFooterLayoutCount(): Int {
        return mFooterLayout?.let {
            if (getFooterCount() > 0) 1 else 0
        } ?: 0
    }

    /**
     * 获取添加的Header个数
     */
    fun getHeaderCount(): Int {
        return mHeaderLayout?.childCount ?: 0
    }

    /**
     * 获取添加的Footer个数
     */
    fun getFooterCount(): Int {
        return mFooterLayout?.childCount ?: 0
    }

    private fun getHeaderViewPosition(): Int {
        mHeaderLayout?.let {
            return 0
        }
        return -1
    }

    private fun getFooterViewPosition(): Int {
        mFooterLayout?.let {
            return getHeaderCount() + list.size
        }
        return -1
    }
}