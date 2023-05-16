package com.suifeng.sdk.base.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class BaseViewHolder<out VB: ViewBinding>(val binding: VB): RecyclerView.ViewHolder(binding.root)