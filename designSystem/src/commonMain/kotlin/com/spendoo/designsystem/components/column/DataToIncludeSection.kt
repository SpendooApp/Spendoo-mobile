package com.spendoo.designsystem.components.column

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.checkbox.CustomRadioButton
import com.spendoo.designsystem.components.divider.HorizontalDivider
import com.spendoo.designsystem.components.icon.Icon
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.modifier.clickableNoRipple
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.painter
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.data_to_include
import spendoo.designsystem.generated.resources.ic_arrow_right
import kotlin.enums.EnumEntries

@Composable
fun <T : Enum<T>> SelectOptionSection(
    title: StringResource,
    selected: T,
    onSelected: (T) -> Unit,
    getName: (T) -> StringResource,
    getIcon: (T) -> DrawableResource,
    entries: EnumEntries<T>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(title),
            style = Theme.typography.heading.tiny,
            color = Theme.colorScheme.text.body
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Theme.colorScheme.background.tertiary, RoundedCornerShape(24.dp))
                .border(1.dp, Theme.colorScheme.border.tertiary, RoundedCornerShape(24.dp))
                .clip(RoundedCornerShape(24.dp))
        ) {
            entries.forEach { dataToInclude ->
                SelectionItemRow(
                    title = stringResource(getName(dataToInclude)),
                    icon = getIcon(dataToInclude),
                    isSelected = selected == dataToInclude,
                    onClick = { onSelected(dataToInclude) }
                )
                if (dataToInclude != entries.last()) {
                    HorizontalDivider(
                        color = Theme.colorScheme.border.primary,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
private fun SelectionItemRow(
    title: String,
    icon: DrawableResource,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(65.dp)
            .clickableNoRipple(onClick = onClick)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Theme.colorScheme.button.secondary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = icon.painter(),
                    contentDescription = null,
                    tint = Theme.colorScheme.button.primary,
                    modifier = Modifier.size(24.dp)
                )
            }
            Text(
                text = title,
                style = Theme.typography.body.medium,
                color = Theme.colorScheme.text.titleSmall
            )
        }

        CustomRadioButton(
            selected = isSelected,
            onClick = onClick
        )
    }
}

internal enum class TestEnum {
    OPTION_ONE,
    OPTION_TWO,
    OPTION_THREE
}

internal fun TestEnum.toStringResource(): StringResource = when (this) {
    TestEnum.OPTION_ONE -> Res.string.data_to_include
    TestEnum.OPTION_TWO -> Res.string.data_to_include
    TestEnum.OPTION_THREE -> Res.string.data_to_include
}

internal fun TestEnum.toIconResource(): DrawableResource = when (this) {
    TestEnum.OPTION_ONE -> Res.drawable.ic_arrow_right
    TestEnum.OPTION_TWO -> Res.drawable.ic_arrow_right
    TestEnum.OPTION_THREE -> Res.drawable.ic_arrow_right
}

@Composable
@Preview
private fun SelectOptionSectionPreview() = SpendooTheme {
    SelectOptionSection(
        title = Res.string.data_to_include,
        selected = TestEnum.OPTION_ONE,
        onSelected = {},
        entries = TestEnum.entries,
        getName = { it.toStringResource() },
        getIcon = { it.toIconResource() }
    )
}