package com.spendoo.identity.presentation.shared.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.button.AppButton
import com.spendoo.designsystem.components.button.AppButtonSize
import com.spendoo.designsystem.components.button.AppButtonState
import com.spendoo.designsystem.components.button.AppButtonType
import com.spendoo.designsystem.components.indicator.DotsProgressIndicator
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import androidx.compose.ui.tooling.preview.Preview
import com.spendoo.identity.presentation.shared.ScreenBackground


@Composable
fun ScreenTemplate(
    upperContent: @Composable () -> Unit,
    onClickActionButton: () -> Unit,
    actionButtonText: String,
    modifier: Modifier = Modifier,
    actionButtonState: AppButtonState = AppButtonState.Enabled,
    underActionButtonContent: @Composable () -> Unit = {},
    lowerContent: @Composable ColumnScope. () -> Unit,
) {

    ScreenBackground {
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 16.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                upperContent()
            }

            // Content Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                lowerContent()

                // Sign Up Button
                AppButton(
                    type = AppButtonType.Primary,
                    size = AppButtonSize.Large,
                    onClick = onClickActionButton,
                    text = actionButtonText,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                    state = actionButtonState,
                    loadingIcon = {
                        DotsProgressIndicator()
                    }
                )
                underActionButtonContent()
            }
        }
    }
}

@Preview(heightDp = 800, widthDp = 360)
@Composable
fun ScreenTemplatePreview() = SpendooTheme {
    ScreenTemplate(
        upperContent = {
            Text(
                text = "Upper Content",
                color = Theme.colorScheme.text.headingBlue,
                style = Theme.typography.heading.large
            )
        },
        onClickActionButton = {},
        actionButtonText = "Action Button",
        underActionButtonContent = {
            Text(
                text = "Under Action Button Content",
                color = Color.Black,
                style = Theme.typography.label.medium.medium
            )
        }
    ) {
        Column(modifier = Modifier.fillMaxSize().weight(1f)) {
            Text("Lower Content Item 1", color = Color.Black, style = Theme.typography.title.large)
            Text("Lower Content Item 2", color = Color.Black, style = Theme.typography.title.large)
            Text("Lower Content Item 3", color = Color.Black, style = Theme.typography.title.large)
        }
    }
}


