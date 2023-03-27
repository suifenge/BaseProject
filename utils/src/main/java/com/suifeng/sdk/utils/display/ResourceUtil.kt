package com.suifeng.sdk.utils.display

import android.graphics.drawable.ColorDrawable
import androidx.annotation.ArrayRes
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.suifeng.sdk.utils.Utils

/**
 * 通过array resource获取Array
 * @param arrayId 数组资源id
 */
fun getStringArrayFromResource(@ArrayRes arrayId: Int): Array<String> {
    return Utils.application.resources.getStringArray(arrayId)
}

/**
 * 通过string资源id获取string
 * @param stringId string资源id
 */
fun getStringFromResource(@StringRes stringId: Int): String {
    return Utils.application.resources.getString(stringId)
}

/**
 * 通过string资源id获取string
 * @param stringId string资源id
 * @param formatArgs 字符格式化参数
 */
fun getStringFromResource(@StringRes stringId: Int, vararg formatArgs: Any?): String {
    return Utils.application.getString(stringId, *formatArgs)
}

/**
 * 通过颜色资源id获取颜色
 * @param colorRes 颜色id
 */
fun getColorFromResource(@ColorRes colorRes: Int): Int {
    return ContextCompat.getColor(Utils.application, colorRes)
}

/**
 * 通过dimen资源id获取大小
 * @param dimenRes dimen id
 */
fun getDimensionFromResource(@DimenRes dimenRes: Int): Int {
    return Utils.application.resources
            .getDimensionPixelSize(dimenRes)
}

/**
 * 通过颜色资源id获取drawable
 * @param colorRes 颜色id
 */
fun getColorDrawable(@ColorRes colorRes: Int): ColorDrawable {
    return ColorDrawable(
        ContextCompat.getColor(
            Utils.application,
            colorRes
        )
    )
}