package com.suifeng.sdk.utils.storage

interface IPreference {

    fun getPreferences(): ISharePreferences

    fun getPreferenceName(): String
}