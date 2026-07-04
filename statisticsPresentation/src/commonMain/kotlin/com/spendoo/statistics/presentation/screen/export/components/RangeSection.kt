package com.spendoo.statistics.presentation.screen.export.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.components.textField.CustomTextField
import com.spendoo.designsystem.modifier.clickableNoRipple
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.SpendooPreview
import com.spendoo.designsystem.utils.extentions.painter
import org.jetbrains.compose.resources.stringResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.end_date
import spendoo.designsystem.generated.resources.ic_date
import spendoo.designsystem.generated.resources.range
import spendoo.designsystem.generated.resources.start_date

@Composable
fun RangeSection(
    startDateText: String?,
    endDateText: String?,
    onStartDateClicked: () -> Unit,
    onEndDateClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(Res.string.range),
            style = Theme.typography.heading.tiny,
            color = Theme.colorScheme.text.body
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DateSelectionField(
                placeholder = stringResource(Res.string.start_date),
                dateText = startDateText,
                onClick = onStartDateClicked,
                modifier = Modifier.weight(1f)
            )
            DateSelectionField(
                placeholder = stringResource(Res.string.end_date),
                dateText = endDateText,
                onClick = onEndDateClicked,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun DateSelectionField(
    placeholder: String,
    dateText: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

    CustomTextField(
        value = dateText ?: "",
        onValueChange = { },
        hint = placeholder,
        trailingIcon = Res.drawable.ic_date.painter(),
        trailingIconColor = Theme.colorScheme.text.label,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
        ),
        enabled = false,
        onTrailingIconClick = {
            focusManager.clearFocus()
            onClick()
        },
        backgroundColor = Theme.colorScheme.background.tertiary,
        textColor = if (dateText.isNullOrEmpty()) Theme.colorScheme.text.label else Theme.colorScheme.text.titleSmall,
        modifier = modifier
            .padding(bottom = 12.dp)
            .fillMaxWidth()
            .clickableNoRipple {
                focusManager.clearFocus()
                onClick()
            }
    )
}

@Composable
@Preview
private fun RangeSectionPreview() = SpendooPreview {
    RangeSection(
        startDateText = "",
        endDateText = "31/12/2024",
        onStartDateClicked = {},
        onEndDateClicked = {}
    )
}
