package com.suifeng.sdk.utils.storage

import android.content.Context
import com.tencent.mmkv.MMKV

object MMKVUtils {

    sealed class KV {
        object User : KV()
        object Other : KV()
    }

    private val userKV by lazy { MMKV.mmkvWithID("user") }
    private val otherKV by lazy { MMKV.mmkvWithID("other") }

    /**
     * use this utils need call this method first
     */
    fun init(ctx: Context) {
        MMKV.initialize(ctx)
    }

    /**
     * return a specify object by user
     */
    fun getMMKVWithId(id: String): MMKV {
        return MMKV.mmkvWithID(id)
    }

    fun get(key: String, default: String = "", type: KV = KV.User): String {
        return if (type == KV.User) {
            userKV.getString(key, default) ?: ""
        } else {
            otherKV.getString(key, default) ?: ""
        }
    }

    fun get(key: String, default: Boolean = false, type: KV = KV.User): Boolean {
        return if (type == KV.User) {
            userKV.getBoolean(key, default)
        } else {
            otherKV.getBoolean(key, default)
        }
    }

    fun get(key: String, default: Int = 0, type: KV = KV.User): Int {
        return if (type == KV.User) {
            userKV.getInt(key, default)
        } else {
            otherKV.getInt(key, default)
        }
    }

    fun get(key: String, default: Float = 0f, type: KV = KV.User): Float {
        return if (type == KV.User) {
            userKV.getFloat(key, default)
        } else {
            otherKV.getFloat(key, default)
        }
    }

    fun get(key: String, default: Long = 0L, type: KV = KV.User): Long {
        return if (type == KV.User) {
            userKV.getLong(key, default)
        } else {
            otherKV.getLong(key, default)
        }
    }


    fun save(key: String, value: String = "", type: KV = KV.User) {
        if (type == KV.User) {
            userKV.putString(key, value)
        } else {
            otherKV.putString(key, value)
        }
    }

    fun save(key: String, value: Boolean = false, type: KV = KV.User) {
        if (type == KV.User) {
            userKV.putBoolean(key, value)
        } else {
            otherKV.putBoolean(key, value)
        }
    }

    fun save(key: String, value: Int = 0, type: KV = KV.User) {
        if (type == KV.User) {
            userKV.putInt(key, value)
        } else {
            otherKV.putInt(key, value)
        }
    }

    fun save(key: String, value: Float = 0f, type: KV = KV.User) {
        if (type == KV.User) {
            userKV.putFloat(key, value)
        } else {
            otherKV.putFloat(key, value)
        }
    }

    fun save(key: String, value: Long = 0L, type: KV = KV.User) {
        if (type == KV.User) {
            userKV.putLong(key, value)
        } else {
            otherKV.putLong(key, value)
        }
    }
}