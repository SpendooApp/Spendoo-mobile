package com.spendoo

import android.app.Application
import org.koin.android.ext.koin.androidContext

class SpendooApp : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin{
            androidContext(this@SpendooApp)
        }
    }
}