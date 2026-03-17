package com.spendoo.identity.presentation.screen.signup.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.modifier.clickableNoRipple
import com.spendoo.designsystem.modifier.thenIf
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.util.extentions.asString
import com.spendoo.designsystem.util.extentions.painter
import com.spendoo.identity.domain.entity.Gender
import com.spendoo.identity.presentation.screen.signup.toResIcon
import com.spendoo.identity.presentation.screen.signup.toResString
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SelectableGenderButton(
    gender: Gender,
    isSelected: Boolean,
    shape: Shape = RoundedCornerShape(16.dp),
    onClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(85.dp)
            .height(76.dp)
            .thenIf(isSelected) {
                background(Theme.colorScheme.background.senary, shape)
            }
            .clickableNoRipple(onClick = onClick)
            .border(
                width = 1.dp,
                color = if (isSelected)
                    Theme.colorScheme.button.primary
                else
                    Theme.colorScheme.border.secondary,
                shape = shape
            )
            .padding(8.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = gender.toResIcon().painter(),
            contentDescription = gender.name,
            modifier = Modifier.padding(bottom = 8.dp).size(30.dp)
        )
        Text(
            text = gender.toResString().asString(),
            color = if (isSelected) Theme.colorScheme.button.primary else Theme.colorScheme.text.title,
            style = Theme.typography.label.medium.medium
        )
    }
}

@Composable
@Preview
fun SelectableGenderButtonPreview() = SpendooTheme {
    SelectableGenderButton(
        gender = Gender.MALE,
        isSelected = true,
        onClick = {}
    )
}

@Composable
@Preview
fun SelectableGenderButtonPreviewNotSelected() = SpendooTheme {
    SelectableGenderButton(
        gender = Gender.MALE,
        isSelected = false,
        onClick = {}
    )
}