package com.spendoo.designsystem.utils.extentions

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun StringResource.asString(): String {
    return stringResource(this)
}

@Composable
fun StringResource.asString(vararg formatArgs: Any): String {
    return stringResource(this, *formatArgs)
}