package com.suifeng.sdk.utils.ext

import android.app.Activity
import android.content.ContextWrapper
import android.os.SystemClock
import android.view.View
import com.suifeng.sdk.utils.R

fun View.gone() {
    if(this.visibility != View.GONE) {
        this.visibility = View.GONE
    }
}

fun View.invisible() {
    if(this.visibility != View.INVISIBLE) {
        this.visibility = View.INVISIBLE
    }
}

fun View.visible() {
    if(this.visibility != View.VISIBLE) {
        this.visibility = View.VISIBLE
    }
}

/**
 * @param isShareSingleClick 是否页面中的所有点击控件都共享一个点击时间，true:共享 false:控件独享
 */
fun View.singleClick(
    interval: Long = 1500,
    isShareSingleClick: Boolean = true,
    listener: (View) -> Unit
) {
    setOnClickListener {
        val target = if(isShareSingleClick) getActivity(this)?.window?.decorView ?: this else this
        val millis = target.getTag(R.id.single_click_tag_last_single_click_millis) as? Long ?: 0
        if(SystemClock.uptimeMillis() - millis >= interval) {
            target.setTag(
                R.id.single_click_tag_last_single_click_millis, SystemClock.uptimeMillis()
            )
            listener.invoke(this)
        }
    }
}

fun View.click(
    listener: (View) -> Unit
) {
    setOnClickListener {
        listener.invoke(it)
    }
}

private fun getActivity(view: View): Activity? {
    var context = view.context
    while(context is ContextWrapper) {
        if(context is Activity) {
            return context
        }
        context = context.baseContext
    }
    return null
}