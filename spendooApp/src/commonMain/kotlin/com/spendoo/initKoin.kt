package com.spendoo

import com.mmk.kmpnotifier.KMPNotifier
import com.mmk.kmpnotifier.notification.PayloadData
import com.spendoo.di.apiModule
import com.spendoo.di.appModule
import com.spendoo.di.featureModule
import com.spendoo.di.networkModule
import com.spendoo.di.platformModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    KMPNotifier.addListener(object : KMPNotifier.Listener {
        override fun onNotificationClicked(data: PayloadData) {
            NotificationClickState.onNotificationClicked(data)
        }
    })

    startKoin {
        config?.invoke(this)

        modules(
            modules = appModule + platformModule + apiModule + featureModule + networkModule
        )
    }
}