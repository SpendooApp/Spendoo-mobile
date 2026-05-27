package com.spendoo.designsystem.components.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.spendoo.designsystem.components.appBar.SpendooIconButton
import com.spendoo.designsystem.components.button.AppButton
import com.spendoo.designsystem.components.button.AppButtonType
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.SpendooPreview
import com.spendoo.designsystem.utils.extentions.painter
import org.jetbrains.compose.resources.DrawableResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.ic_cancel
import spendoo.designsystem.generated.resources.img_microphone_record

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppAlert(
    modifier: Modifier = Modifier,
    iconRes: DrawableResource,
    title: String,
    description: String,
    actionText: String,
    onActionClick: () -> Unit,
    dismissText: String,
    onDismissRequest: () -> Unit,
) {
    BasicAlertDialog(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
        content = {
            AppAlertContent(
                modifier = modifier,
                iconRes = iconRes,
                title = title,
                description = description,
                actionText = actionText,
                onActionClick = onActionClick,
                dismissText = dismissText,
                onDismissRequest = onDismissRequest
            )
        }
    )
}

@Composable
fun AppAlertContent(
    modifier: Modifier = Modifier,
    iconRes: DrawableResource,
    title: String,
    description: String,
    actionText: String,
    onActionClick: () -> Unit,
    dismissText: String,
    onDismissRequest: () -> Unit,
) {
    Box(
        modifier = modifier
            .width(328.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Theme.colorScheme.background.tertiary)
            .padding(vertical = 16.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title,
                        style = Theme.typography.heading.small,
                        color = Theme.colorScheme.text.title,
                        modifier = Modifier.weight(1f).padding(end = 8.dp)
                    )
                    SpendooIconButton(
                        iconRes = Res.drawable.ic_cancel,
                        contentDescription = "Close",
                        onClick = onDismissRequest,
                    )
                }
            }

            bodySection(
                iconRes = iconRes,
                description = description,
                actionText = actionText,
                onActionClick = onActionClick,
                dismissText = dismissText,
                onDismissRequest = onDismissRequest
            )
        }
    }
}


private fun LazyListScope.bodySection(
    iconRes: DrawableResource,
    description: String,
    actionText: String,
    onActionClick: () -> Unit,
    dismissText: String,
    onDismissRequest: () -> Unit
) {
    item {
        Image(
            painter = iconRes.painter(),
            contentDescription = null,
            modifier = Modifier.size(100.dp),
            contentScale = ContentScale.Crop
        )
    }

    item {
        Text(
            text = description,
            style = Theme.typography.body.medium,
            color = Theme.colorScheme.text.body,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(296.dp)
        )
    }

    item {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AppButton(
                type = AppButtonType.Primary,
                onClick = onActionClick,
                text = actionText,
                modifier = Modifier.fillMaxWidth()
            )
            AppButton(
                type = AppButtonType.Secondary,
                text = dismissText,
                onClick = onDismissRequest,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


@PreviewLightDark
@Composable
fun AppAlertContentPreview() = SpendooPreview {
    AppAlertContent(
        iconRes = Res.drawable.img_microphone_record,
        title = "Title Title Title Title Title Title Title Title Title Title Title Title",
        description = "bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla",
        actionText = "Action",
        onActionClick = {},
        dismissText = "Dismiss",
        onDismissRequest = {},
    )
}