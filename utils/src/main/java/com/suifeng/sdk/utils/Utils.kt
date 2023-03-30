package com.suifeng.sdk.utils

import android.app.Application
import com.suifeng.sdk.utils.phone.PhoneInfoManager
import com.suifeng.sdk.utils.storage.MMKVUtils

class Utils {
    companion object {
        lateinit var application: Application
        fun init(app: Application) {
            application = app
            PhoneInfoManager.init(app)
            MMKVUtils.init(app)
        }
    }
}