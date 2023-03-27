package com.suifeng.sdk.utils

import android.app.Application
import com.suifeng.sdk.utils.phone.PhoneInfoManager

class Utils {
    companion object {
        lateinit var application: Application
        fun init(app: Application) {
            application = app
            PhoneInfoManager.init(app)
        }
    }
}