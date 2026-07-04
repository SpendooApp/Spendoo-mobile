package com.spendoo.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.shapes.HexagonShape
import com.spendoo.designsystem.shapes.RibbonShape
import com.spendoo.designsystem.theme.color.scheme.toBrush
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.painter
import org.jetbrains.compose.resources.DrawableResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.img_coin

@Composable
fun AchievementShape(
    icon: DrawableResource,
    isUnlocked: Boolean,
    modifier: Modifier = Modifier,
    shapeHexagon: Shape = HexagonShape(),
    shapeRibbon: Shape = RibbonShape(),
    level: Int
) {
    val backgroundColor = if (isUnlocked){
        if (Theme.isDarkTheme){
            Theme.colorScheme.background.septenary.toBrush()
        } else {
            Theme.colorScheme.gradient.brand
        }
    }else {
        if (Theme.isDarkTheme){
            Theme.colorScheme.brand.primaryVariant.toBrush()
        } else {
            Theme.colorScheme.text.link.toBrush()
        }
    }
    val border = if (isUnlocked){
        if (Theme.isDarkTheme){
            Theme.colorScheme.background.septenary.toBrush()
        } else {
            Theme.colorScheme.gradient.brand
        }
    }else {
        if (Theme.isDarkTheme){
            Theme.colorScheme.brand.primaryVariant.toBrush()
        } else {
            Theme.colorScheme.text.link.toBrush()
        }
    }
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .size(155.dp, 175.dp)
                .background(backgroundColor, shapeHexagon)
            .border(
                width = 3.dp,
                brush = Theme.colorScheme.gradient.brandVertical,
                shape = shapeHexagon
            ),
        contentAlignment = Alignment.Center
        ) {
        Box(
            modifier = Modifier
                .size(95.dp)
                .background(Theme.colorScheme.gradient.brandVertical, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = icon.painter(),
                contentDescription = "coin image",
                modifier = Modifier.size(67.dp)
            )
        }
    }
        Box(
            modifier = Modifier
                .size(156.dp, 40.dp)
                .background(Theme.colorScheme.gradient.brandVertical, shapeRibbon),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Level $level",
                style = Theme.typography.heading.medium,
                color = Theme.colorScheme.brand.onPrimary,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun AchievementShapePreview() {
    SpendooTheme {
        AchievementShape(
            level = 1,
            icon = Res.drawable.img_coin,
            isUnlocked = true
        )
    }
}
