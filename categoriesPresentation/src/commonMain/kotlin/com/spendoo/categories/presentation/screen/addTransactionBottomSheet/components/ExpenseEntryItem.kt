package com.spendoo.categories.presentation.screen.addTransactionBottomSheet.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.spendoo.categories.presentation.screen.addCategoryBottomSheet.toDrawableResource
import com.spendoo.categories.presentation.screen.addTransactionBottomSheet.TransactionEntryUiState
import com.spendoo.categories.presentation.screen.addTransactionBottomSheet.filterSuggestedTitles
import com.spendoo.designsystem.components.icon.Icon
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.components.textField.CustomTextField
import com.spendoo.designsystem.modifier.clickableNoRipple
import com.spendoo.designsystem.modifier.shimmerEffect
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.SpendooPreview
import com.spendoo.designsystem.utils.asString
import com.spendoo.designsystem.utils.extentions.asString
import com.spendoo.designsystem.utils.extentions.painter
import com.spendoo.shared.domain.utils.toCleanDoubleOrNull
import com.spendoo.shared.domain.utils.toCleanString
import org.jetbrains.compose.resources.stringResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.choose_category
import spendoo.designsystem.generated.resources.enter_a_title
import spendoo.designsystem.generated.resources.enter_your_amount
import spendoo.designsystem.generated.resources.ic_arrow_down
import spendoo.designsystem.generated.resources.ic_cancel
import spendoo.designsystem.generated.resources.write_a_note_optional

@Composable
fun ExpenseEntryItem(
    index: Int,
    entry: TransactionEntryUiState,
    savedTitles: List<String> = emptyList(),
    onCategoryClicked: (id: String) -> Unit,
    onEntryChanged: (id: String, entry: TransactionEntryUiState) -> Unit,
    onRemoveClicked: (id: String) -> Unit,
    showRemoveButton: Boolean,
    modifier: Modifier = Modifier,
    onHeightChanged: ((Dp) -> Unit)? = null,
) {
    val density = LocalDensity.current
    val suggestions = remember(entry.title, savedTitles) {
        filterSuggestedTitles(entry.title, savedTitles)
    }

    Column(
        modifier = modifier
            .widthIn(min = 280.dp)
            .width(IntrinsicSize.Min)
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = 1.dp,
                color = Theme.colorScheme.border.tertiary,
                shape = RoundedCornerShape(12.dp)
            )
            .background(Theme.colorScheme.background.tertiary)
            .padding(12.dp),
    ) {
        Column(
            modifier = Modifier.onSizeChanged { size ->
                onHeightChanged?.invoke(with(density) { size.height.toDp() + 24.dp })
            },
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "#${index + 1}",
                    style = Theme.typography.body.small,
                    color = Theme.colorScheme.text.titleSmall
                )

                if (showRemoveButton) {
                    Icon(
                        painter = Res.drawable.ic_cancel.painter(),
                        contentDescription = "Remove",
                        tint = Theme.colorScheme.text.label,
                        modifier = Modifier
                            .size(12.dp)
                            .clickableNoRipple { onRemoveClicked(entry.id) }
                    )
                }
            }

            CustomTextField(
                value = entry.title,
                onValueChange = { onEntryChanged(entry.id, entry.copy(title = it)) },
                hint = Res.string.enter_a_title.asString(),
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                errorText = entry.titleError?.asString(),
                singleLine = true
            )

            if (suggestions.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    suggestions.forEach { suggestion ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(Theme.colorScheme.button.secondary)
                                .clickableNoRipple {
                                    onEntryChanged(entry.id, entry.copy(title = suggestion))
                                }
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = suggestion,
                                style = Theme.typography.label.medium.small,
                                color = Theme.colorScheme.button.onSecondary
                            )
                        }
                    }
                }
            }

            CustomTextField(
                value = entry.amount.toCleanString(),
                onValueChange = {
                    onEntryChanged(
                        entry.id,
                        entry.copy(amount = it.toCleanDoubleOrNull())
                    )
                },
                hint = Res.string.enter_your_amount.asString(),
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                errorText = entry.amountError?.asString()
            )

            CustomTextField(
                value = entry.categoryName ?: "",
                onValueChange = { },
                hint = Res.string.choose_category.asString(),
                enabled = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickableNoRipple { onCategoryClicked(entry.id) },
                onTrailingIconClick = { onCategoryClicked(entry.id) },
                trailingIcon = Res.drawable.ic_arrow_down.painter(),
                trailingIconColor = Theme.colorScheme.text.label,
                prefix = entry.categoryIcon?.let { icon ->
                    {
                        Icon(
                            painter = icon.toDrawableResource().painter(),
                            contentDescription = null,
                            tint = Theme.colorScheme.button.onSecondary,
                            modifier = Modifier.size(20.dp).padding(end = 8.dp)
                        )
                    }
                },
                errorText = entry.categoryError?.asString()
            )

            CustomTextField(
                value = entry.note,
                onValueChange = { onEntryChanged(entry.id, entry.copy(note = it)) },
                hint = stringResource(Res.string.write_a_note_optional),
                modifier = Modifier
                    .fillMaxWidth(),
                singleLine = false,
                minLines = 3,
                errorText = entry.noteError?.asString()
            )
        }
    }
}

@Composable
fun ExpenseEntryItemShimmer(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .widthIn(min = 280.dp)
            .width(IntrinsicSize.Min)
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = 1.dp,
                color = Theme.colorScheme.border.tertiary,
                shape = RoundedCornerShape(12.dp)
            )
            .background(Theme.colorScheme.background.tertiary)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(30.dp)
                    .height(16.dp)
                    .shimmerEffect()
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .shimmerEffect()
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .shimmerEffect()
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .shimmerEffect()
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .shimmerEffect()
        )
    }
}



@Composable
@Preview
fun ExpenseEntryItemPreview() = SpendooPreview {
    ExpenseEntryItem(
        index = 0,
        entry = TransactionEntryUiState(
            title = "Groceries",
            amount = 50.00,
            categoryName = "Food",
            categoryIcon = null
        ),
        onCategoryClicked = {},
        onRemoveClicked = {},
        onEntryChanged = { _, _ -> },
        showRemoveButton = true
    )
}
