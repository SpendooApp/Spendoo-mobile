package com.spendoo

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mmk.kmpnotifier.KMPNotifier
import com.mmk.kmpnotifier.extensions.onCreateOrOnNewIntent
import com.mmk.kmpnotifier.permission.permissionUtil
import com.spendoo.util.AppLocalizer
import org.koin.android.ext.android.inject
import space.kodio.core.Kodio
import space.kodio.core.initialize
import space.kodio.core.onRequestPermissionsResult

class MainActivity : ComponentActivity() {
    private val localizer: AppLocalizer by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        Kodio.initialize(this)
        localizer.applyLocaleToContext()
        KMPNotifier.onCreateOrOnNewIntent(intent)
        val permissionUtil by permissionUtil()
        permissionUtil.askNotificationPermission()

        setContent {
            App()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String?>,
        grantResults: IntArray, deviceId: Int
    ) {
        Kodio.onRequestPermissionsResult(requestCode, grantResults)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        KMPNotifier.onCreateOrOnNewIntent(intent)
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}