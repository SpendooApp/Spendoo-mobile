package com.spendoo.designsystem.utils

import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

val customShadow = Shadow(
    radius = 50.dp,
    spread = (-12).dp,
    offset = DpOffset(0.dp , 25.dp),
    color = Color.Black,
    alpha = 0.25f,
    blendMode = BlendMode.SrcAtop
)

val up4DropShadow = Shadow(
    radius = 16.dp,
    color = Color.Black.copy(alpha = 0.35f),
    offset = DpOffset(x = 0.dp, y = 4.dp),
    spread = 0.dp
)