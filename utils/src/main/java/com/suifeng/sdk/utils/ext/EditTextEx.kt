package com.suifeng.sdk.utils.ext

import android.graphics.Rect
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.annotation.DrawableRes
import com.suifeng.sdk.utils.display.dpToPx
import com.suifeng.sdk.utils.other.SimpleTextWatcher


fun EditText.text(): String = text.toString().trim()

fun EditText.isEmpty(): Boolean = text.toString().trim().isEmpty()


/**
 *  edit 最大值设置
 */
fun EditText.setMax(max: Int) {
    addTextChangedListener(object : SimpleTextWatcher() {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.toString()?.let {
                if (it.isDouble()) {
                    if (it.toDouble() > max) {
                        setText(max.toString())
                    }
                }
            }
        }
    })
}

/**
 * 输入框时，对键盘进行一个弹出，不遮盖
 * @param target 目标对象，不会覆盖这个布局
 */
fun View.bindKeyboardLayout(target: View) {
    this.viewTreeObserver.addOnGlobalLayoutListener {
        val rect = Rect()
        this.getWindowVisibleDisplayFrame(rect)
        val layoutInvisibleHeight = this.rootView.height - rect.bottom
        // 存储以下变量
        val isShowKeyBoard = if (this.tag != null) {
            this.tag as Boolean
        } else {
            false
        }
        if (layoutInvisibleHeight > dpToPx(150f)) {
            if (!isShowKeyBoard) {
                this.tag = true
                val location = IntArray(2)
                target.getLocationInWindow(location)
                val scrollHeight = location[1] + target.height - rect.bottom
                this.scrollTo(0, scrollHeight + 20)
            }
        } else {
            if (isShowKeyBoard) {
                this.scrollTo(0, 0)
                this.tag = false
            }
        }
    }
}

/**
 * 绑定带清除功能的按钮
 */
fun EditText.bindClearButton(
    clearView: View?,
    showEtView: View? = null,
    @DrawableRes goneDrawable: Int = -1,
    @DrawableRes visibleDrawable: Int = -1
) {
    clearView?.setOnClickListener { this.text.clear() }
    showEtView?.setOnClickListener {
        val tag = it.tag
        if (tag != null) {
            //密码 TYPE_CLASS_TEXT 和 TYPE_TEXT_VARIATION_PASSWORD 必须一起使用
            this.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            if (goneDrawable != -1) {
                showEtView.setBackgroundResource(goneDrawable)
            }
            it.tag = null
        } else {
            //明文
            this.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            if (visibleDrawable != -1) {
                showEtView.setBackgroundResource(visibleDrawable)
            }
            it.tag = true
        }
    }
    // 文本改变清空隐藏等
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val text = s.toString() + ""
            if (text.isNotEmpty()) {
                clearView?.visibility = View.VISIBLE
            } else {
                clearView?.visibility = View.INVISIBLE
            }
        }

        override fun afterTextChanged(s: Editable?) {

        }

    })
    this.setOnFocusChangeListener { _, hasFocus ->
        if (!hasFocus) {
            clearView?.visibility = View.INVISIBLE
        } else {
            if (!this.isEmpty()) {
                clearView?.visibility = View.VISIBLE
            }
        }
    }
}