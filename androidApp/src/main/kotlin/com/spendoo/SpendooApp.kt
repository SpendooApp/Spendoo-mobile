package com.spendoo

import android.app.Application
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.mmk.kmpnotifier.KMPNotifier
import com.mmk.kmpnotifier.notification.configuration.NotificationPlatformConfiguration
import com.mmk.kmpnotifier.push.firebase.FirebasePush
import org.koin.android.ext.koin.androidContext

class SpendooApp : Application() {
    override fun onCreate() {
        super.onCreate()

        AppEnvironment.internalBaseUrl = BuildConfig.BASE_URL
        AppEnvironment.internalVersionName = BuildConfig.VERSION_NAME

        KMPNotifier.initialize(
            configuration = NotificationPlatformConfiguration.Android(
                notificationIconResId = R.drawable.ic_money_notification,
                showPushNotification = false
            ),
            FirebasePush
        )

        if (!BuildConfig.DEBUG) {
            FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
        }

        initKoin {
            androidContext(this@SpendooApp)
        }
    }
}
