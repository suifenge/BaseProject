package com.suifeng.sdk.utils.phone

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.text.TextUtils

object OSUtil {
    private const val KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name"
    private const val KEY_EMUI_VERSION_NAME = "ro.build.version.emui"
    private const val KEY_DISPLAY = "ro.build.display.id"

    /**
     * 判断是否为miui
     * Is miui boolean.
     *
     * @return the boolean
     */
    val isMIUI: Boolean
        get() {
            val property = getSystemProperty(
                KEY_MIUI_VERSION_NAME,
                ""
            )
            return !TextUtils.isEmpty(property)
        }

    /**
     * 判断miui版本是否大于等于6
     * Is miui 6 later boolean.
     *
     * @return the boolean
     */
    val isMIUI6Later: Boolean
        get() {
            val version = mIUIVersion
            val num: Int
            return if (!version.isEmpty()) {
                try {
                    num = Integer.valueOf(version.substring(1))
                    num >= 6
                } catch (e: NumberFormatException) {
                    false
                }
            } else false
        }

    /**
     * 获得miui的版本
     * Gets miui version.
     *
     * @return the miui version
     */
    val mIUIVersion: String
        get() = if (isMIUI) getSystemProperty(
            KEY_MIUI_VERSION_NAME,
            ""
        ) else ""

    /**
     * 判断是否为emui
     * Is emui boolean.
     *
     * @return the boolean
     */
    val isEMUI: Boolean
        get() {
            val property = getSystemProperty(
                KEY_EMUI_VERSION_NAME,
                ""
            )
            return !TextUtils.isEmpty(property)
        }

    /**
     * 得到emui的版本
     * Gets emui version.
     *
     * @return the emui version
     */
    val eMUIVersion: String
        get() = if (isEMUI) getSystemProperty(
            KEY_EMUI_VERSION_NAME,
            ""
        ) else ""

    /**
     * 判断是否为emui3.1版本
     * Is emui 3 1 boolean.
     *
     * @return the boolean
     */
    val isEMUI3_1: Boolean
        get() {
            val property = eMUIVersion
            return "EmotionUI 3" == property || property.contains("EmotionUI_3.1")
        }

    /**
     * 判断是否为emui3.0版本
     * Is emui 3 1 boolean.
     *
     * @return the boolean
     */
    val isEMUI3_0: Boolean
        get() {
            val property = eMUIVersion
            return property.contains("EmotionUI_3.0")
        }

    /**
     * 判断是否为emui3.x版本
     * Is emui 3 x boolean.
     *
     * @return the boolean
     */
    val isEMUI3_x: Boolean
        get() = isEMUI3_0 || isEMUI3_1

    /**
     * 判断是否为flymeOS
     * Is flyme os boolean.
     *
     * @return the boolean
     */
    val isFlymeOS: Boolean
        get() = flymeOSFlag.toLowerCase().contains("flyme")

    /**
     * 判断flymeOS的版本是否大于等于4
     * Is flyme os 4 later boolean.
     *
     * @return the boolean
     */
    val isFlymeOS4Later: Boolean
        get() {
            val version = flymeOSVersion
            val num: Int
            return if (version.isNotEmpty()) {
                try {
                    num = if (version.toLowerCase().contains("os")) {
                        Integer.valueOf(version.substring(9, 10))
                    } else {
                        Integer.valueOf(version.substring(6, 7))
                    }
                    num >= 4
                } catch (e: NumberFormatException) {
                    false
                }
            } else false
        }

    /**
     * 判断flymeOS的版本是否等于5
     * Is flyme os 5 boolean.
     *
     * @return the boolean
     */
    val isFlymeOS5: Boolean
        get() {
            val version = flymeOSVersion
            val num: Int
            return if (version.isNotEmpty()) {
                try {
                    num = if (version.toLowerCase().contains("os")) {
                        Integer.valueOf(version.substring(9, 10))
                    } else {
                        Integer.valueOf(version.substring(6, 7))
                    }
                    num == 5
                } catch (e: NumberFormatException) {
                    false
                }
            } else false
        }

    /**
     * 得到flymeOS的版本
     * Gets flyme os version.
     *
     * @return the flyme os version
     */
    val flymeOSVersion: String
        get() = if (isFlymeOS) getSystemProperty(
            KEY_DISPLAY,
            ""
        ) else ""

    private val flymeOSFlag: String
        private get() = getSystemProperty(
            KEY_DISPLAY,
            ""
        )

    private fun getSystemProperty(key: String, defaultValue: String): String {
        try {
            @SuppressLint("PrivateApi") val clz =
                Class.forName("android.os.SystemProperties")
            val method =
                clz.getMethod("get", String::class.java, String::class.java)
            return method.invoke(clz, key, defaultValue) as String
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return defaultValue
    }

    /**
     * 获取操作系统
     *
     * @return
     */
    fun getOS(): String {
        return "Android" + Build.VERSION.RELEASE
    }

    /**
     * 是否有刘海屏
     *
     * @return
     */
    fun hasNotchInScreen(activity: Activity): Boolean {
        // android  P 以上有标准 API 来判断是否有刘海屏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val displayCutout = activity.window.decorView.rootWindowInsets.displayCutout
            if (displayCutout != null) {
                // 说明有刘海屏
                return true
            }
        } else {
            // 通过其他方式判断是否有刘海屏  目前官方提供有开发文档的就 小米，vivo，华为（荣耀），oppo
            val manufacturer = Build.MANUFACTURER
            return if (TextUtils.isEmpty(manufacturer)) {
                false
            } else if (manufacturer.equals("HUAWEI", ignoreCase = true)) {
                hasNotchHw(activity)
            } else if (manufacturer.equals("xiaomi", ignoreCase = true)) {
                hasNotchXiaoMi(activity)
            } else if (manufacturer.equals("oppo", ignoreCase = true)) {
                hasNotchOPPO(activity)
            } else if (manufacturer.equals("vivo", ignoreCase = true)) {
                hasNotchVIVO(activity)
            } else {
                false
            }
        }
        return false
    }

    /**
     * 判断vivo是否有刘海屏
     * https://swsdl.vivo.com.cn/appstore/developer/uploadfile/20180328/20180328152252602.pdf
     *
     * @param activity
     * @return
     */
    private fun hasNotchVIVO(activity: Activity): Boolean {
        return try {
            val c = Class.forName("android.util.FtFeature")
            val get = c.getMethod("isFeatureSupport", Int::class.javaPrimitiveType)
            get.invoke(c, 0x20) as Boolean
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            false
        }
    }

    /**
     * 判断oppo是否有刘海屏
     * https://open.oppomobile.com/wiki/doc#id=10159
     *
     * @param activity
     * @return
     */
    private fun hasNotchOPPO(activity: Activity): Boolean {
        return activity.packageManager.hasSystemFeature("com.oppo.feature.screen.heteromorphism")
    }

    /**
     * 判断xiaomi是否有刘海屏
     * https://dev.mi.com/console/doc/detail?pId=1293
     *
     * @param activity
     * @return
     */
    private fun hasNotchXiaoMi(activity: Activity): Boolean {
        return try {
            val c = Class.forName("android.os.SystemProperties")
            val get = c.getMethod("getInt", String::class.java, Int::class.javaPrimitiveType)
            get.invoke(c, "ro.miui.notch", 0) as Int == 1
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            false
        }
    }

    /**
     * 判断华为是否有刘海屏
     * https://devcenter-test.huawei.com/consumer/cn/devservice/doc/50114
     *
     * @param activity
     * @return
     */
    private fun hasNotchHw(activity: Activity): Boolean {
        return try {
            val cl = activity.classLoader
            val HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil")
            val get = HwNotchSizeUtil.getMethod("hasNotchInScreen")
            get.invoke(HwNotchSizeUtil) as Boolean
        } catch (e: java.lang.Exception) {
            false
        }
    }
}