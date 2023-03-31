package com.suifeng.sdk.utils.ext

import android.view.View

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