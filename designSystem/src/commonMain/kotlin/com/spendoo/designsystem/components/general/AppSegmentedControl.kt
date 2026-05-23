package com.spendoo.designsystem.components.general

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.modifier.clickableNoRipple
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.SpendooPreview
import com.spendoo.designsystem.utils.extentions.asString
import org.jetbrains.compose.ui.tooling.preview.Preview
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.expenses
import spendoo.designsystem.generated.resources.income
import kotlin.math.roundToInt

@Composable
fun <T : Enum<T>> AppSegmentedControl(
    options: List<GenSelectableOption<T>>,
    selectedOption: T,
    onOptionSelected: (T) -> Unit,
    modifier: Modifier = Modifier
) {
    var containerWidth by remember { mutableStateOf(0) }
    val animatedOffset = remember { Animatable(0f) }

    val itemWidth = if (containerWidth > 0 && options.isNotEmpty()) {
        containerWidth / options.size
    } else 0

    LaunchedEffect(selectedOption, containerWidth) {
        if (containerWidth > 0 && options.isNotEmpty()) {
            val selectedIndex = options.indexOfFirst { it.elem == selectedOption }
            val targetOffset = selectedIndex * itemWidth
            animatedOffset.animateTo(
                targetValue = targetOffset.toFloat(),
                animationSpec = tween(durationMillis = 300)
            )
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .onGloballyPositioned { layoutCoordinates ->
                containerWidth = layoutCoordinates.size.width
            }
            .clip(RoundedCornerShape(12.dp))
            .background(Theme.colorScheme.button.secondary)
    ) {
        if (itemWidth > 0) {
            Box(
                modifier = Modifier
                    .offset {
                        IntOffset(
                            x = animatedOffset.value.roundToInt(),
                            y = 0
                        )
                    }
                    .width(with(LocalDensity.current) { itemWidth.toDp() })
                    .fillMaxHeight()
                    .padding(4.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Theme.colorScheme.button.primary)
            )
        }

        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            options.forEach { option ->
                val isSelected = option.elem == selectedOption
                val textColor by animateColorAsState(
                    targetValue = if (isSelected) {
                        Theme.colorScheme.button.onPrimary
                    } else {
                        Theme.colorScheme.button.onTertiary
                    },
                    animationSpec = tween(durationMillis = 300)
                )

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickableNoRipple {
                            onOptionSelected(option.elem)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = option.name.asString(),
                        style = Theme.typography.label.medium.medium,
                        color = textColor
                    )
                }
            }
        }
    }
}


enum class SampleEnum {
    OPTION1, OPTION2, OPTION3
}

@Preview
@Composable
fun AppSegmentedControlPreview() = SpendooPreview {
    val options = listOf(
        GenSelectableOption(SampleEnum.OPTION1, Res.string.income),
        GenSelectableOption(SampleEnum.OPTION2, Res.string.expenses),
        GenSelectableOption(SampleEnum.OPTION3, Res.string.income)
    )
    var selectedOption by remember {
        mutableStateOf(
            SampleEnum.OPTION1
        )
    }
    AppSegmentedControl(
        options = options,
        selectedOption = selectedOption,
        onOptionSelected = { selectedOption = it }
    )
}