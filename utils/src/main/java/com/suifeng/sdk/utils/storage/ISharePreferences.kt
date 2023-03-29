package com.suifeng.sdk.utils.storage

import android.content.SharedPreferences

interface ISharePreferences {

    fun putString(key: String, value: String): SharedPreferences.Editor

    fun putStringSet(key: String, value: Set<String>): SharedPreferences.Editor

    fun putInt(key: String, value: Int): SharedPreferences.Editor

    fun putLong(key: String, value: Long): SharedPreferences.Editor

    fun putFloat(key: String, value: Float): SharedPreferences.Editor

    fun putBoolean(key: String, value: Boolean): SharedPreferences.Editor

    fun remove(key: String): SharedPreferences.Editor

    fun clear(): SharedPreferences.Editor

    fun commit(): Boolean

    fun apply()

    fun getAll(): Map<String, *>

    fun getString(key: String, default: String): String?

    fun getStringSet(key: String, default: Set<String>): Set<String>?

    fun getInt(key: String, default: Int): Int

    fun getLong(key: String, default: Long): Long

    fun getFloat(key: String, default: Float): Float

    fun getBoolean(key: String, default: Boolean): Boolean

    fun contains(key: String): Boolean


}