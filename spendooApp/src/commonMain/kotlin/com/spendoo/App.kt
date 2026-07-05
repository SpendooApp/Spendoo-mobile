package com.spendoo

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mmk.kmpnotifier.KMPNotifier
import com.mmk.kmpnotifier.local.localNotifier
import com.mmk.kmpnotifier.notification.PayloadData
import com.mmk.kmpnotifier.push.PushListener
import com.mmk.kmpnotifier.push.firebase.addPushListener
import com.spendoo.appEntryPoint.EntryPoint
import com.spendoo.categories.api.FinancialActionRoute
import com.spendoo.designsystem.navigation.effector.Effector
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.identity.domain.repository.AuthenticationRepository
import com.spendoo.identity.domain.repository.SettingsRepository
import com.spendoo.identity.domain.util.AppLocalizer
import com.spendoo.identity.domain.util.AppTheme
import com.spendoo.util.SetSystemBarsAppearance
import com.spendoo.util.toStringMap
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Preview
@Composable
fun App(
    isSystemDarkTheme: Boolean = isSystemInDarkTheme(),
    settingsRepository: SettingsRepository = koinInject(),
    appLocalizer: AppLocalizer = koinInject(),
    authenticationRepository: AuthenticationRepository = koinInject(),
    effector: Effector = koinInject()
) {
    val currentTheme by settingsRepository.observeAppTheme().collectAsStateWithLifecycle()
    val currentLanguage by settingsRepository.observeAppLanguage().collectAsStateWithLifecycle()

    val isDarkTheme = when (currentTheme) {
        AppTheme.SYSTEM -> isSystemDarkTheme
        AppTheme.DARK -> true
        AppTheme.LIGHT -> false
    }

    val coroutineScope = rememberCoroutineScope()
    val exceptionHandler = CoroutineExceptionHandler { _, _ -> }

    LaunchedEffect(Unit) {
        KMPNotifier.addPushListener(object : PushListener {
            override fun onNewToken(token: String) {
                coroutineScope.launch(exceptionHandler) {
                    authenticationRepository.sendDeviceToken(token)
                }
            }

            override fun onPushNotificationWithPayloadData(
                title: String?,
                body: String?,
                data: PayloadData
            ) {
                if (data.isEmpty()) {
                    KMPNotifier.localNotifier.notify {
                        this.title = title.orEmpty()
                        this.body = body.orEmpty()
                    }
                } else {
                    coroutineScope.launch(exceptionHandler) {
                        effector.navigate(
                            FinancialActionRoute(
                                tile = title.orEmpty(),
                                body = body.orEmpty(),
                                payload = data.toStringMap()
                            ),
                            forceNavigate = true
                        )
                    }
                }
            }
        })
    }

    SpendooTheme(
        language = currentLanguage.iso,
        darkTheme = isDarkTheme,
        content = {
            SetSystemBarsAppearance(currentTheme)
            EntryPoint()
        }
    )
}
