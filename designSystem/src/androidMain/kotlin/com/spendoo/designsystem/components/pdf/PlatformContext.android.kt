package com.spendoo.designsystem.components.pdf

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun rememberPlatformContext(): Any? = LocalContext.current
