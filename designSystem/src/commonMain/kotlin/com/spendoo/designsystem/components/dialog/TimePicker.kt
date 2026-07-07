package com.spendoo.designsystem.components.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_TYPE_NORMAL
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import kotlinx.datetime.LocalTime
import org.jetbrains.compose.resources.stringResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.cancel
import spendoo.designsystem.generated.resources.ok
import spendoo.designsystem.generated.resources.select_time

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePicker(
    showDialog: Boolean,
    selectedTime: LocalTime?,
    onTimeSelected: (LocalTime) -> Unit,
    onDismiss: () -> Unit,
    confirmText: String = stringResource(Res.string.ok),
    dismissText: String = stringResource(Res.string.cancel),
    containerColor: Color = Theme.colorScheme.background.tertiary,
    contentColor: Color = Theme.colorScheme.text.body,
    brandColor: Color = Theme.colorScheme.icon.primary,
) {
    if (showDialog) {
        val initialTime = selectedTime ?: LocalTime(12, 0)
        val timePickerState = rememberTimePickerState(
            initialHour = initialTime.hour,
            initialMinute = initialTime.minute,
            is24Hour = false
        )

        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = containerColor,
                modifier = Modifier
                    .width(IntrinsicSize.Min)
                    .padding(24.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(Res.string.select_time),
                        style = Theme.typography.label.medium.medium,
                        color = Theme.colorScheme.text.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp)
                    )

                    TimePicker(
                        state = timePickerState,
                        colors = TimePickerDefaults.colors(
                            clockDialColor = Theme.colorScheme.background.secondary,
                            clockDialSelectedContentColor = Theme.colorScheme.text.title,
                            clockDialUnselectedContentColor = Theme.colorScheme.text.body,
                            selectorColor = brandColor,
                            periodSelectorBorderColor = Theme.colorScheme.border.secondary,
                            periodSelectorSelectedContainerColor = brandColor,
                            periodSelectorUnselectedContainerColor = Color.Transparent,
                            periodSelectorSelectedContentColor = Theme.colorScheme.text.title,
                            periodSelectorUnselectedContentColor = Theme.colorScheme.text.body,
                            timeSelectorSelectedContainerColor = brandColor.copy(alpha = 0.2f),
                            timeSelectorUnselectedContainerColor = Theme.colorScheme.background.secondary,
                            timeSelectorSelectedContentColor = brandColor,
                            timeSelectorUnselectedContentColor = Theme.colorScheme.text.body
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        TextButton(
                            colors = ButtonDefaults.textButtonColors(contentColor = contentColor),
                            onClick = onDismiss
                        ) {
                            Text(dismissText, style = Theme.typography.label.medium.small)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        TextButton(
                            colors = ButtonDefaults.textButtonColors(contentColor = contentColor),
                            onClick = {
                                onTimeSelected(LocalTime(timePickerState.hour, timePickerState.minute))
                            }
                        ) {
                            Text(confirmText, style = Theme.typography.label.medium.small)
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(widthDp = 360, heightDp = 640, name = "Dark", uiMode = UI_MODE_NIGHT_YES or UI_MODE_TYPE_NORMAL)
private fun TimePickerDialogPreview() = SpendooTheme(darkTheme = true) {
    TimePicker(
        showDialog = true,
        selectedTime = LocalTime(12, 30),
        onTimeSelected = {},
        onDismiss = {}
    )
}
