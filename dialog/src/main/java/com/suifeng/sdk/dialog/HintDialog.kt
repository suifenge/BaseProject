package com.suifeng.sdk.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager


/**
 * @describe ios风格HintDialog
 */
class HintDialog : DialogFragment() {
    // rootview
    private lateinit var mInflate: View

    // 事件
    private var positiveListener: (View) -> Unit = {}
    private var negativeListener: (View) -> Unit = {}

    private var mShowTag: String = ""
    private var mLastShowTime: Long = 0L

    companion object {
        private const val DEFAULT_TAG = "HINT_DIALOG"

        /**
         * 创建构造方法
         */
        fun newInstance(
            message: String,
            title: String = "提示",
            sureText: String = "确定",
            cancelText: String? = "取消"
        ): HintDialog {
            val dialog = HintDialog()
            val bundle = Bundle()
            bundle.putString("title", title)
            bundle.putString("message", message)
            bundle.putString("sureText", sureText)
            if (cancelText != null) {
                bundle.putString("cancelText", cancelText)
            }
            dialog.arguments = bundle
            return dialog
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mInflate = inflater.inflate(R.layout.layout_dialog_hint, container, true)!!
        // 初始化View
        mInflate.findViewById<TextView>(R.id.dialog_tv_title).text = requireArguments().getString("title")
        mInflate.findViewById<TextView>(R.id.dialog_tv_message).text = requireArguments().getString("message")
        mInflate.findViewById<TextView>(R.id.dialog_tv_ensure).text = requireArguments().getString("sureText")
        val cancelText = requireArguments().getString("cancelText")
        if (cancelText != null && cancelText.isNotEmpty()) {
            mInflate.findViewById<TextView>(R.id.dialog_tv_cancel).text = cancelText
        } else {
            mInflate.findViewById<TextView>(R.id.dialog_tv_cancel).visibility = View.GONE
            mInflate.findViewById<View>(R.id.dialog_btn_divider).visibility = View.GONE
        }
        // 初始化事件
        mInflate.findViewById<TextView>(R.id.dialog_tv_ensure).setOnClickListener {
            dismiss()
            positiveListener(it)
        }
        // 取消
        mInflate.findViewById<TextView>(R.id.dialog_tv_cancel).setOnClickListener {
            dismiss()
            negativeListener(it)
        }
        return mInflate
    }

    /**
     * 显示HintDialog 带事件回调
     */
    fun show(
        manager: FragmentManager,
        tag: String,
        sureListener: (View) -> Unit,
        dismissListener: (View) -> Unit = {}
    ): HintDialog {
        if (!isRepeatedShow(tag)) {
            super.show(manager, tag)
        }
        this.positiveListener = sureListener
        this.negativeListener = dismissListener
        return this
    }

    fun show(
        fragment: Fragment,
        sureListener: (View) -> Unit,
        dismissListener: (View) -> Unit = {}
    ): HintDialog? {
        if (fragment.activity != null && fragment.activity?.isFinishing == false &&
                fragment.isAdded
        ) {
            return show(
                fragment.parentFragmentManager,
                fragment::class.simpleName ?: DEFAULT_TAG,
                sureListener, dismissListener
            )
        }
        return null
    }

    fun show(
        activity: FragmentActivity,
        sureListener: (View) -> Unit,
        dismissListener: (View) -> Unit = {}
    ): HintDialog? {
        if (!activity.isFinishing) {
            return show(
                activity.supportFragmentManager,
                activity.javaClass.simpleName,
                sureListener, dismissListener
            )
        }
        return null
    }

    private fun isRepeatedShow(tag: String): Boolean {
        val result = tag == mShowTag && (SystemClock.uptimeMillis() - mLastShowTime < 500)
        mShowTag = tag
        mLastShowTime = SystemClock.uptimeMillis()
        return result
    }

}
