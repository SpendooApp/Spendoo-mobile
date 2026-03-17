package com.spendoo.designsystem.components.textField

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.modifier.clickableNoRipple
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.ic_date
import spendoo.designsystem.generated.resources.ic_eye_closed

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String = "",
    leadingIcon: Painter? = null,
    trailingIcon: Painter? = null,
    trailingIconColor: Color? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    errorText: String? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    onTrailingIconClick: () -> Unit = {},
    showTrailingDivider: Boolean = false
) {
    val colors = Theme.colorScheme
    val typography = Theme.typography

    val interaction = remember { MutableInteractionSource() }
    val isFocused by interaction.collectIsFocusedAsState()

    val shouldFloat = isFocused || value.isNotEmpty()
    val showError = !errorText.isNullOrBlank()

    val borderColor by animateColorAsState(
        targetValue = when {
            showError -> colors.additional.onError
            isFocused -> colors.border.active
            else -> colors.border.secondary
        },
        animationSpec = tween(durationMillis = 150)
    )

    val labelTopPadding by animateDpAsState(
        targetValue = if (shouldFloat) 20.dp else 0.dp,
        animationSpec = tween(durationMillis = 200, easing = FastOutSlowInEasing)
    )

    val textTopPadding by animateDpAsState(
        targetValue = if (shouldFloat) 24.dp else 0.dp,
        animationSpec = tween(durationMillis = 200, easing = FastOutSlowInEasing)
    )

    val shape = RoundedCornerShape(16.dp)

    val currentDirection = LocalLayoutDirection.current
    val isRtl = remember { currentDirection == LayoutDirection.Rtl }

    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(1.dp, borderColor, shape)
                .background(colors.background.quinary, shape)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            leadingIcon?.let { painter ->
                Icon(
                    painter = painter,
                    contentDescription = null,
                    tint = colors.text.label,
                    modifier = Modifier.size(24.dp).scale(
                        scaleX = if (isRtl) -1f else 1f,
                        scaleY = 1f
                    )
                )

                Box(
                    Modifier
                        .padding(horizontal = 12.dp)
                        .width(1.dp)
                        .height(30.dp)
                        .background(colors.border.secondary)
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
            ) {
                if (hint.isNotEmpty()) {
                    Text(
                        text = hint,
                        color = when {
                            shouldFloat && isFocused -> colors.border.active
                            shouldFloat -> colors.border.active
                            else -> colors.text.label
                        },
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(bottom = labelTopPadding),
                        style = if (shouldFloat) typography.body.extraSmall else typography.body.small,
                        textAlign = TextAlign.Start
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopStart)
                        .padding(top = textTopPadding)
                        .height(24.dp)
                ) {
                    BasicTextField(
                        value = value,
                        onValueChange = onValueChange,
                        enabled = enabled,
                        singleLine = singleLine,
                        maxLines = maxLines,
                        minLines = minLines,
                        readOnly = readOnly,
                        interactionSource = interaction,
                        keyboardOptions = keyboardOptions,
                        textStyle = typography.body.small.copy(
                            color = colors.text.title,
                            textAlign = TextAlign.Start
                        ),
                        visualTransformation = visualTransformation,
                        cursorBrush = SolidColor(colors.border.active),
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterStart)
                    )
                }
            }

            trailingIcon?.let { painter ->
                Spacer(Modifier.width(8.dp))
                if (showTrailingDivider) {
                    Box(
                        Modifier
                            .padding(horizontal = 12.dp)
                            .width(1.dp)
                            .height(30.dp)
                            .background(colors.border.secondary)
                    )
                }

                val trailingIconColor = trailingIconColor ?: animateColorAsState(
                    targetValue = when {
                        showError -> colors.additional.onError
                        else -> colors.text.label
                    },
                    animationSpec = tween(durationMillis = 150)
                ).value

                Icon(
                    painter = painter,
                    contentDescription = null,
                    tint = trailingIconColor,
                    modifier = Modifier.size(24.dp).scale(
                        scaleX = if (isRtl) -1f else 1f,
                        scaleY = 1f
                    ).clickableNoRipple(onClick = onTrailingIconClick)
                )
            }
        }

        // Animated spacer
        AnimatedVisibility(
            visible = !errorText.isNullOrBlank(),
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Error text
        AnimatedVisibility(
            visible = !errorText.isNullOrBlank(),
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Text(
                text = errorText ?: "",
                color = Theme.colorScheme.additional.onError,
                modifier = Modifier.padding(start = 4.dp),
                style = Theme.typography.body.small,
                textAlign = TextAlign.Start
            )
        }
    }
}

@Composable
@Preview
fun CustomTextFieldPreview() = SpendooTheme {
    Column (
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ){
        CustomTextField(
            value = "Hello",
            onValueChange = {},
            hint = "Enter text",
            leadingIcon = painterResource(Res.drawable.ic_date),
            trailingIcon = painterResource(Res.drawable.ic_eye_closed),
//            errorText = "This field is required"
        )
        CustomTextField(
            value = "",
            onValueChange = {},
            hint = "Enter text",
            leadingIcon = painterResource(Res.drawable.ic_date),
            trailingIcon = painterResource(Res.drawable.ic_eye_closed),
//            errorText = "This field is required"
        )
    }
}