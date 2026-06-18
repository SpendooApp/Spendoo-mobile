package com.spendoo.designsystem.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.asString
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.high_priority
import spendoo.designsystem.generated.resources.low_priority
import spendoo.designsystem.generated.resources.medium_priority
import spendoo.designsystem.generated.resources.done

enum class Priority {
    LOW, MEDIUM, HIGH, Done
}

fun Priority.toText() = when (this) {
    Priority.LOW -> Res.string.low_priority
    Priority.MEDIUM -> Res.string.medium_priority
    Priority.HIGH -> Res.string.high_priority
    Priority.Done -> Res.string.done
}

@Composable
fun Priority.toBackGroundColor() = when (this) {
    Priority.LOW -> Theme.colorScheme.additional.success
    Priority.MEDIUM -> Theme.colorScheme.additional.warning
    Priority.HIGH -> Theme.colorScheme.additional.error
    Priority.Done -> Theme.colorScheme.additional.done
}

@Composable
fun Priority.toTextColor() = when (this) {
    Priority.LOW -> Theme.colorScheme.additional.onSuccess
    Priority.MEDIUM -> Theme.colorScheme.additional.onWarning
    Priority.HIGH -> Theme.colorScheme.additional.onError
    Priority.Done -> Theme.colorScheme.additional.onDone
}

@Composable
fun PriorityCard(
    priority: Priority,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(priority.toBackGroundColor())
            .padding(horizontal = 8.dp, vertical = 2.dp)
    ) {
        Text(
            text = priority.toText().asString(),
            style = Theme.typography.label.medium.small,
            color = priority.toTextColor()
        )
    }
}

@Composable
@PreviewLightDark
fun PriorityCardPreview() = SpendooTheme {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        PriorityCard(Priority.LOW)
        PriorityCard(Priority.MEDIUM)
        PriorityCard(Priority.HIGH)
        PriorityCard(Priority.Done)
    }
}