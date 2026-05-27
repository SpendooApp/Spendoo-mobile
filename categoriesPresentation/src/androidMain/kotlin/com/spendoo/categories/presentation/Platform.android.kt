package com.spendoo.categories.presentation

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import org.koin.core.context.GlobalContext

actual fun platform() = "Android"

actual fun openAppSettings() {
    val context = GlobalContext.get().get<android.content.Context>()
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", context.packageName, null)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    context.startActivity(intent)
}