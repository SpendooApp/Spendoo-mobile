package com.spendoo.identity.presentation.screen.onboarding.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.icon.Icon
import com.spendoo.designsystem.components.icon.OutlinedButton
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.util.extentions.asString
import com.spendoo.designsystem.util.extentions.painter
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.get_started
import spendoo.designsystem.generated.resources.ic_arrow_left
import spendoo.designsystem.generated.resources.ic_arrow_right

@Composable
fun BottomArrows(
    modifier: Modifier = Modifier,
    isLastPage: Boolean,
    isFirstPage: Boolean,
    onNextButtonClicked: () -> Unit,
    onPreviousButtonClicked: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (!isFirstPage) {
            ArrowButton(
                onClick = onPreviousButtonClicked,
                icon = Res.drawable.ic_arrow_left,
            )
        } else {
            Box(modifier = Modifier.size(56.dp))
        }

        if (isLastPage) {
            OutlinedButton(
                onClick = onNextButtonClicked,
                shape = CircleShape,
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp),
                modifier = Modifier.height(56.dp)
            ) {
                Text(
                    text = Res.string.get_started.asString(),
                    style = Theme.typography.body.medium,
                    color = Theme.colorScheme.primary.variant600,
                )
            }
        } else {
            ArrowButton(
                onClick = onNextButtonClicked,
                icon = Res.drawable.ic_arrow_right,
            )
        }
    }
}

@Composable
private fun ArrowButton(
    onClick: () -> Unit,
    icon: DrawableResource,
    modifier: Modifier = Modifier,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.size(56.dp),
        shape = CircleShape,
        contentPadding = PaddingValues(0.dp)
    ) {
        Icon(
            painter = icon.painter(),
            contentDescription = null,
            tint = Theme.colorScheme.primary.variant600,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
@Preview
fun BottomArrowsPreview() = SpendooTheme {
    BottomArrows(
        isLastPage = true,
        isFirstPage = false,
        onNextButtonClicked = {},
        onPreviousButtonClicked = {}
    )
}