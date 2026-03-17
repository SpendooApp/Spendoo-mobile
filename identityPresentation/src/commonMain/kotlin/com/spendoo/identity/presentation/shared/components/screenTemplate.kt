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
import com.spendoo.designsystem.components.button.AppButtonType
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.spendoo.identity.presentation.shared.ScreenTemplate as OtherScreenTemplate


@Composable
fun ScreenTemplate(
    upperContent: @Composable () -> Unit,
    onClick: () -> Unit,
    buttonText: String,
    modifier: Modifier = Modifier,
    underActionButtonContent: @Composable () -> Unit = {},
    lowerContent: @Composable ColumnScope. () -> Unit,
) {

    OtherScreenTemplate {
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 54.dp),
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
                    onClick = onClick,
                    text = buttonText,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
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
        onClick = {},
        buttonText = "Action Button",
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
