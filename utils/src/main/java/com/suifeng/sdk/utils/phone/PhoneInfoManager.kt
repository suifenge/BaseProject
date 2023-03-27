package com.suifeng.sdk.utils.phone

import android.app.Application
import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager
import kotlin.math.max
import kotlin.math.min

/**
 * 手机信息
 */
object PhoneInfoManager {
    /**
     * 屏幕宽度（像素）
     */
    private var mScreenWidthPx = 0

    /**
     * 屏幕高度（像素）
     */
    private var mScreenHeightPx = 0

    /**
     * 屏幕宽度（dp）
     */
    private var mScreenWidthDp = 0

    /**
     * 屏幕高度（dp）
     */
    private var mScreenHeightDp = 0

    /**
     * density dpi
     */
    private var mDensityDpi = 0

    /**
     * density scale
     */
    private var mDensity = 0f

    /**
     * status bar height
     */
    private var mStatusBarHeight = 0

    /**
     * product type
     */
    private var mProductType: String? = null

    private var mIsBiggerScreen = false

    fun init(application: Application) {
        val windowManager =
            application.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        mScreenHeightPx = max(metrics.heightPixels, metrics.widthPixels)
        mScreenWidthPx = min(metrics.heightPixels, metrics.widthPixels)
        mIsBiggerScreen = mScreenHeightPx * 1.0 / mScreenWidthPx > 16.0 / 9
        mDensityDpi = metrics.densityDpi
        mDensity = metrics.density
        mScreenHeightDp = (mScreenHeightPx / mDensity).toInt()
        mScreenWidthDp = (mScreenWidthPx / mDensity).toInt()

        val resourceId =
            application.resources.getIdentifier("status_bar_height", "dimen", "android")
        mStatusBarHeight = application.resources.getDimensionPixelSize(resourceId)
        mProductType = createProductType()
    }

    fun getScreenWidthPx(): Int {
        return mScreenWidthPx
    }

    fun getScreenHeightPx(): Int {
        return mScreenHeightPx
    }

    fun getScreenContentHeightPx(): Int {
        return mScreenHeightPx - getStatusBarHeight()
    }

    fun getScreenWidthDp(): Int {
        return mScreenWidthDp
    }

    fun getScreenHeightDp(): Int {
        return mScreenHeightDp
    }

    fun getDensityDpi(): Int {
        return mDensityDpi
    }

    fun getDensity(): Float {
        return mDensity
    }

    fun getStatusBarHeight(): Int {
        return mStatusBarHeight
    }

    fun getProductType(): String? {
        return mProductType
    }

    private fun createProductType(): String {
        val model = Build.MODEL
        return model.replace("[:{} \\[\\]\"']*".toRegex(), "")
    }

    /**
     * 是否是超大屏幕
     */
    fun isBiggerScreen(): Boolean {
        return mIsBiggerScreen
    }

    /**
     * 获取手机品牌商
     */
    fun getDeviceBuildBrand(): String {
        return Build.BRAND ?: ""
    }

    /**
     * 获取手机型号
     */
    fun getDeviceBuildModel(): String {
        return Build.MODEL ?: ""
    }

    /**
     * 获取手机系统版本号
     */
    fun getDeviceBuildRelease(): String {
        return Build.VERSION.RELEASE ?: ""
    }
}