package com.suifeng.sdk.utils.storage

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor

open class BaseSharePreferences constructor(context: Context, name: String): ISharePreferences {

    private var sharedPreferences: SharedPreferences

    private var editor: Editor

    init {
        sharedPreferences = context.getSharedPreferences(name, Activity.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    override fun putString(key: String, value: String): Editor {
        return editor.putString(key, value)
    }

    override fun putStringSet(key: String, value: Set<String>): Editor {
        return editor.putStringSet(key, value)
    }

    override fun putInt(key: String, value: Int): Editor {
        return editor.putInt(key, value)
    }

    override fun putLong(key: String, value: Long): Editor {
        return editor.putLong(key, value)
    }

    override fun putFloat(key: String, value: Float): Editor {
        return editor.putFloat(key, value)
    }

    override fun putBoolean(key: String, value: Boolean): Editor {
        return editor.putBoolean(key, value)
    }

    override fun remove(key: String): Editor {
        return editor.remove(key)
    }

    override fun clear(): Editor {
        return editor.clear()
    }

    override fun commit(): Boolean {
        return editor.commit()
    }

    override fun apply() {
        return editor.apply()
    }

    override fun getAll(): Map<String, *> {
        return sharedPreferences.all
    }

    override fun getString(key: String, default: String): String? {
        return sharedPreferences.getString(key, default)
    }

    override fun getStringSet(key: String, default: Set<String>): Set<String>? {
        return sharedPreferences.getStringSet(key, default)
    }

    override fun getInt(key: String, default: Int): Int {
        return sharedPreferences.getInt(key, default)
    }

    override fun getLong(key: String, default: Long): Long {
        return sharedPreferences.getLong(key, default)
    }

    override fun getFloat(key: String, default: Float): Float {
        return sharedPreferences.getFloat(key, default)
    }

    override fun getBoolean(key: String, default: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, default)
    }

    override fun contains(key: String): Boolean {
        return sharedPreferences.contains(key)
    }


}