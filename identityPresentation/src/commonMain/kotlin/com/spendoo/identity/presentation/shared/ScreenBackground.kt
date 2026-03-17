package com.spendoo.identity.presentation.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.util.extentions.painter
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.img_screenTemplate_dark
import spendoo.designsystem.generated.resources.img_screenTemplate_light

@Composable
fun ScreenBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box {
        Image(
            painter = if (Theme.isDarkTheme) Res.drawable.img_screenTemplate_dark.painter() else Res.drawable.img_screenTemplate_light.painter(),
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop
        )
        Box(modifier = modifier) {
            content()
        }
    }
}