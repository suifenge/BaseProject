package com.suifeng.sdk.utils.phone

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat

object KeyboardUtil {

    fun hideSoftKeyboard(activity: Activity) {
        WindowCompat.getInsetsController(activity.window, activity.findViewById(android.R.id.content))
            ?.hide(WindowInsetsCompat.Type.ime())
    }

    fun showSoftKeyboard(activity: Activity) {
        WindowCompat.getInsetsController(activity.window, activity.findViewById(android.R.id.content))
            ?.show(WindowInsetsCompat.Type.ime())
    }

    fun focusEditShowKeyBoard(editText: EditText) {
        editText.isEnabled = true
        editText.isFocusable = true
        editText.isFocusableInTouchMode = true
        editText.requestFocus()
        val inputManager =
            editText.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.showSoftInput(editText, 0)
        editText.setSelection(editText.text.length)
    }

    fun isShowSoftKeyBoard(activity: Activity, isShow: Boolean = true) {
        if (isShow) {
            showSoftKeyboard(activity)
        } else {
            hideSoftKeyboard(activity)
        }
    }

    /**
     * 判断键盘是否建
     */
    fun isVisibleKeyBoard(activity: Activity): Boolean {
        val insets = ViewCompat.getRootWindowInsets(activity.findViewById(android.R.id.content))
        return insets?.isVisible(WindowInsetsCompat.Type.ime()) ?: false
    }


    fun addKeyBordHeightChangeCallBack(view: View, onAction: (height: Int) -> Unit) {
        var posBottom: Int
        if (Build.VERSION.SDK_INT >= 30) {
            val cb = object : WindowInsetsAnimation.Callback(DISPATCH_MODE_STOP) {
                override fun onProgress(
                    insets: WindowInsets,
                    animations: MutableList<WindowInsetsAnimation>
                ): WindowInsets {
                    posBottom = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom +
                            insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
                    onAction.invoke(posBottom)
                    return insets
                }
            }
            view.setWindowInsetsAnimationCallback(cb)
        } else {
            ViewCompat.setOnApplyWindowInsetsListener(view) { _, insets ->
                posBottom = insets.getInsets(WindowInsetsCompat.Type.ime()).bottom +
                        insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
                onAction.invoke(posBottom)
                insets
            }
        }
    }
}