package com.spendoo.designsystem.components.placeholder

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.button.AppButton
import com.spendoo.designsystem.components.button.AppButtonSize
import com.spendoo.designsystem.components.button.AppButtonType
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.an_error_occurred
import spendoo.designsystem.generated.resources.retry

import com.spendoo.designsystem.utils.UiText
import com.spendoo.designsystem.utils.asString
import com.spendoo.designsystem.utils.toUiText

@Composable
fun ErrorState(
    text: UiText,
    onActionText: StringResource,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.colorScheme.background.secondary, RoundedCornerShape(16.dp))
            .border(1.dp, Theme.colorScheme.border.secondary, RoundedCornerShape(16.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = text.asString(),
            style = Theme.typography.body.medium,
            color = Theme.colorScheme.additional.onError,
            textAlign = TextAlign.Center
        )
        AppButton(
            type = AppButtonType.Secondary,
            onClick = onRetry,
            text = stringResource(onActionText),
            size = AppButtonSize.Small,
            modifier = Modifier.fillMaxWidth(0.6f)
        )
    }
}

@Composable
fun ErrorState(
    text: StringResource,
    onActionText: StringResource,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    ErrorState(
        text = text.toUiText(),
        onActionText = onActionText,
        onRetry = onRetry,
        modifier = modifier
    )
}

@Composable
@Preview
private fun ErrorStatePreview() = SpendooTheme {
    ErrorState(
        text = Res.string.an_error_occurred,
        onActionText = Res.string.retry,
        onRetry = {}
    )
}