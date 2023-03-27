package com.suifeng.sdk.utils.ext

import android.text.TextUtils.isEmpty
import com.suifeng.sdk.utils.other.IDCardFormat
import java.text.NumberFormat
import java.util.Locale
import java.util.regex.Pattern

/**
 * 是否为数字
 */
fun String.isNumber(): Boolean {
    return if (this.isNotEmpty()) {
        val p = Pattern.compile("[0-9]*")
        val m = p.matcher(this)
        m.matches()
    } else {
        false
    }
}

/** 是否为double类型 */
fun String.isDouble(): Boolean {
    if (this.isEmpty()) {
        return false
    }
    val p = Pattern.compile("[0-9]*.[0-9]*")
    val m = p.matcher(this)
    return m.matches()
}

/** 是否为手机号码 */
fun String.isMobile(): Boolean {
    val p = Pattern.compile("^[1][3,4,5,7,8,9][0-9]{9}$") // 验证手机号
    val m = p.matcher(this)
    return m.matches()
}

/**
 * 为身份张格式
 */
fun String.isIDCard(): Boolean {
    return !this.isNotIDCard()
}

/**
 * 非身份张格式
 */
public inline fun String.isNotIDCard(): Boolean {
    val validate = IDCardFormat.IDCardValidate(this)
    return !isEmpty(validate)
}

/**
 * 是否为密码
 */
fun String.isPassWord(): Boolean {
    val p = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$")
    val m = p.matcher(this)
    return m.matches()
}

/**
 * 判断邮箱是否合法
 */
fun String.isEmail(): Boolean {
    if ("" == this) return false
    val p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*")//复杂匹配
    val m = p.matcher(this)
    return m.matches()
}

fun Double.convertMoney(): String {
    val numberFormat = NumberFormat.getIntegerInstance(Locale.CHINA)
    return numberFormat.format(this)
}