package com.spendoo.categories.screen.addCategory

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.button.AppButton
import com.spendoo.designsystem.components.button.AppButtonType
import com.spendoo.designsystem.components.icon.Icon
import com.spendoo.designsystem.components.surface.Surface
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.components.textField.CustomTextField
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.painter
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.ic_arrow_right
import spendoo.designsystem.generated.resources.ic_car
import spendoo.designsystem.generated.resources.ic_categories
import spendoo.designsystem.generated.resources.ic_date
import spendoo.designsystem.generated.resources.ic_food
import spendoo.designsystem.generated.resources.ic_gift
import spendoo.designsystem.generated.resources.ic_mobile
import spendoo.designsystem.generated.resources.ic_pet

@Composable
fun AddCategoryScreen(
    modifier: Modifier = Modifier,
) {
    var categoryName by remember { mutableStateOf("") }
    var budget by remember { mutableStateOf("") }
    var budgetStartDate by remember { mutableStateOf("") }
    var leftoverFundsAction by remember { mutableStateOf("") }
    var selectedPriorityIndex by remember { mutableIntStateOf(1) }
    var selectedIconIndex by remember { mutableIntStateOf(0) }
    var selectedCycleIndex by remember { mutableIntStateOf(2) }
    val priorityOptions = listOf("Low", "Medium", "High")
    val resetCycleOptions = listOf("Daily", "Weekly", "Monthly", "Yearly", "Custom")
    val categoryIcons = listOf(
        Res.drawable.ic_categories,
        Res.drawable.ic_pet,
        Res.drawable.ic_mobile,
        Res.drawable.ic_food,
        Res.drawable.ic_gift,
        Res.drawable.ic_car,
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 10.dp, vertical = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 4.dp)
                .width(70.dp)
                .height(5.dp)
                .background(
                    color = Theme.colorScheme.border.primary,
                    shape = RoundedCornerShape(100.dp)
                )
        )

        Text(
            text = "Add New Category",
            style = Theme.typography.heading.medium,
            color = Theme.colorScheme.text.title,
            modifier = Modifier.padding(top = 22.dp, bottom = 18.dp)
        )

        CustomTextField(
            value = categoryName,
            onValueChange = { categoryName = it },
            hint = "Enter Category Name",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )

        CustomTextField(
            value = budget,
            onValueChange = { budget = it },
            hint = "Enter Budget",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )

        CustomTextField(
            value = budgetStartDate,
            onValueChange = { budgetStartDate = it },
            hint = "Enter Budget Start Date",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            trailingIcon = Res.drawable.ic_date.painter(),
            trailingIconColor = Theme.colorScheme.text.link
        )

        CustomTextField(
            value = leftoverFundsAction,
            onValueChange = { leftoverFundsAction = it },
            hint = "Leftover Funds Action",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 14.dp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            trailingIcon = Res.drawable.ic_arrow_right.painter(),
            trailingIconColor = Theme.colorScheme.text.link
        )

        Text(
            text = "Priority",
            style = Theme.typography.label.semiBold.medium,
            color = Theme.colorScheme.text.title,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Theme.colorScheme.background.tertiary,
                    shape = RoundedCornerShape(14.dp)
                )
                .padding(6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            priorityOptions.forEachIndexed { index, title ->
                ToggleChip(
                    text = title,
                    selected = selectedPriorityIndex == index,
                    onClick = { selectedPriorityIndex = index },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Text(
            text = "Icon",
            style = Theme.typography.label.semiBold.medium,
            color = Theme.colorScheme.text.title,
            modifier = Modifier.padding(top = 14.dp, bottom = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            categoryIcons.forEachIndexed { index, icon ->
                IconChip(
                    icon = icon,
                    selected = selectedIconIndex == index,
                    onClick = { selectedIconIndex = index }
                )
            }
        }

        Text(
            text = "Reset Budget Cycle",
            style = Theme.typography.label.semiBold.medium,
            color = Theme.colorScheme.text.title,
            modifier = Modifier.padding(top = 14.dp, bottom = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            resetCycleOptions.forEachIndexed { index, title ->
                CycleChip(
                    text = title,
                    selected = selectedCycleIndex == index,
                    onClick = { selectedCycleIndex = index }
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            AppButton(
                type = AppButtonType.Secondary,
                onClick = {},
                text = "Cancel",
                modifier = Modifier.weight(1f)
            )
            AppButton(
                type = AppButtonType.Primary,
                onClick = {},
                text = "Add Category",
                modifier = Modifier.weight(1.45f)
            )
        }
    }
}

@Composable
private fun ToggleChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = if (selected) Theme.colorScheme.button.primary else Theme.colorScheme.background.tertiary
    ) {
        Box(
            modifier = Modifier.padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = Theme.typography.title.medium,
                color = if (selected) Theme.colorScheme.button.onPrimary else Theme.colorScheme.text.link
            )
        }
    }
}

@Composable
private fun IconChip(
    icon: DrawableResource,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(14.dp),
        color = if (selected) Theme.colorScheme.button.primary else Theme.colorScheme.background.tertiary,
        modifier = Modifier.size(56.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                painter = icon.painter(),
                contentDescription = null,
                tint = if (selected) Theme.colorScheme.button.onPrimary else Theme.colorScheme.button.primary,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
private fun CycleChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        color = if (selected) Theme.colorScheme.background.primary else Theme.colorScheme.background.tertiary,
        border = androidx.compose.foundation.BorderStroke(
            width = if (selected) 1.dp else 0.dp,
            color = if (selected) Theme.colorScheme.button.primary else Theme.colorScheme.background.tertiary
        )
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = Theme.typography.title.medium,
                color = if (selected) Theme.colorScheme.button.primary else Theme.colorScheme.text.link
            )
        }
    }
}

@Preview(heightDp = 820, widthDp = 400)
@Composable
private fun AddCategoryScreenPreview() = SpendooTheme {
    AddCategoryScreen()
}
