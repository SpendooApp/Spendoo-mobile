package com.spendoo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
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
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}