package com.suifeng.sdk.utils.display

import android.util.TypedValue
import com.suifeng.sdk.utils.Utils

/**
 * dp转像素点
 * @param dpValue Float型数据
 * @return 像素 Float型
 */
fun dpToPx(dpValue: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, dpValue,
        Utils.application.resources.displayMetrics
    )
}

/**
 * dp转像素
 * @param dpValue Int型数据
 * @return 像素 Int型
 */
fun dpToPx(dpValue: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, dpValue.toFloat(),
        Utils.application.resources.displayMetrics
    ).toInt()
}

/**
 * sp转像素
 * @param spValue Float型数据
 * @return 像素 Float型
 */
fun spToPx(spValue: Float): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP, spValue,
        Utils.application.resources.displayMetrics
    )
}