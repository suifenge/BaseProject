package com.suifeng.sdk.utils.storage

import android.content.Context

/**
 * 名字默认为“default_preferences”的sp工具类，如果需要其他名字的，
 * 可以继承BaseSharePreferences类，并实现IPreference接口，
 * 或自己定义更细的接口继承IPreference
 */
class SpUtils(context: Context): IPreference {

    private val mPreferenceName = "default_preferences"

    private var sharePreferences: BaseSharePreferences

    init {
        sharePreferences = BaseSharePreferences(context, getPreferenceName())
    }

    override fun getPreferences(): ISharePreferences {
        return sharePreferences
    }

    override fun getPreferenceName(): String {
        return mPreferenceName
    }
}