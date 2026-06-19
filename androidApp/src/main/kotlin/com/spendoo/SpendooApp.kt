package com.spendoo

import android.app.Application
import org.koin.android.ext.koin.androidContext

class SpendooApp : Application() {
    override fun onCreate() {
        super.onCreate()

        AppEnvironment.internalBaseUrl = BuildConfig.BASE_URL
        AppEnvironment.internalVersionName = BuildConfig.VERSION_NAME

        initKoin {
            androidContext(this@SpendooApp)
        }
    }
}
