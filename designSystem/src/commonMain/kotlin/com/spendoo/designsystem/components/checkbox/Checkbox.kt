package com.spendoo.designsystem.components.checkbox

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.CheckboxColors as M3CheckboxColors

@Composable
fun Checkbox(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: CheckboxColors = CheckboxColors.default(),
    interactionSource: MutableInteractionSource? = null,
) {
    Checkbox(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        enabled = enabled,
        colors = colors.toM3(),
        interactionSource = interactionSource
    )
}

data class CheckboxColors(
    val checkedCheckmarkColor: Color,
    val uncheckedCheckmarkColor: Color,
    val disabledCheckmarkColor: Color,
    val checkedBoxColor: Color,
    val uncheckedBoxColor: Color,
    val disabledCheckedBoxColor: Color,
    val disabledUncheckedBoxColor: Color,
    val disabledIndeterminateBoxColor: Color,
    val checkedBorderColor: Color,
    val uncheckedBorderColor: Color,
    val disabledBorderColor: Color,
    val disabledUncheckedBorderColor: Color,
    val disabledIndeterminateBorderColor: Color,
) {
    companion object {
        @Composable
        fun default(
            checkedColor: Color = Color.Unspecified,
            uncheckedColor: Color = Color.Unspecified,
            checkmarkColor: Color = Color.Unspecified,
            disabledCheckedColor: Color = Color.Unspecified,
            disabledUncheckedColor: Color = Color.Unspecified,
            disabledIndeterminateColor: Color = Color.Unspecified,
        ): CheckboxColors = CheckboxDefaults.colors(
            checkedColor,
            uncheckedColor,
            checkmarkColor,
            disabledCheckedColor, disabledUncheckedColor, disabledIndeterminateColor
        ).toCheckboxColors()
    }
}

fun CheckboxColors.toM3(): M3CheckboxColors {
    return M3CheckboxColors(
        checkedCheckmarkColor = checkedCheckmarkColor,
        uncheckedCheckmarkColor = uncheckedCheckmarkColor,
        disabledCheckmarkColor = disabledCheckmarkColor,
        checkedBoxColor = checkedBoxColor,
        uncheckedBoxColor = uncheckedBoxColor,
        disabledCheckedBoxColor = disabledCheckedBoxColor,
        disabledUncheckedBoxColor = disabledUncheckedBoxColor,
        disabledIndeterminateBoxColor = disabledIndeterminateBoxColor,
        checkedBorderColor = checkedBorderColor,
        uncheckedBorderColor = uncheckedBorderColor,
        disabledBorderColor = disabledBorderColor,
        disabledUncheckedBorderColor = disabledUncheckedBorderColor,
        disabledIndeterminateBorderColor = disabledIndeterminateBorderColor
    )
}

fun M3CheckboxColors.toCheckboxColors() = CheckboxColors(
    checkedCheckmarkColor = checkedCheckmarkColor,
    uncheckedCheckmarkColor = uncheckedCheckmarkColor,
    disabledCheckmarkColor = disabledCheckmarkColor,
    checkedBoxColor = checkedBoxColor,
    uncheckedBoxColor = uncheckedBoxColor,
    disabledCheckedBoxColor = disabledCheckedBoxColor,
    disabledUncheckedBoxColor = disabledUncheckedBoxColor,
    disabledIndeterminateBoxColor = disabledIndeterminateBoxColor,
    checkedBorderColor = checkedBorderColor,
    uncheckedBorderColor = uncheckedBorderColor,
    disabledBorderColor = disabledBorderColor,
    disabledUncheckedBorderColor = disabledUncheckedBorderColor,
    disabledIndeterminateBorderColor = disabledIndeterminateBorderColor
)