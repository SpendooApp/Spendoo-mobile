package com.spendoo.designsystem.components.bottomNavigation

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.icon.Icon
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.utils.up4DropShadow
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.ic_home
import spendoo.designsystem.generated.resources.ic_home_selected
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun BottomNavigationBarContent(
    items: List<BottomNavigationItem>,
    centerItem: CenterNavigationItem?,
    selectedItemIndex: Int,
    onItemClick: (BottomNavigationItem) -> Unit,
    modifier: Modifier = Modifier
) {
    if (items.isEmpty()) return

    val barHeight = 74.dp
    val fabSize = 60.dp
    val cutoutRadius = 48.dp

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(barHeight + 24.dp)
    ) {
        // Background with cutout
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(barHeight)
                .dropShadow(
                    shape = BottomNavShape(cutoutRadius),
                    shadow = up4DropShadow
                )
                .background(
                    color = Theme.colorScheme.background.secondary,
                    shape = BottomNavShape(cutoutRadius)
                )
        )

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(barHeight),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val middleIndex = items.size / 2

            items.forEachIndexed { index, item ->
                if (index == middleIndex && centerItem != null) {
                    Box(modifier = Modifier.weight(1f))
                }

                BottomNavigationBarItem(
                    isSelected = index == selectedItemIndex,
                    selectedIcon = item.selectedIcon,
                    unselectedIcon = item.notSelectedIcon,
                    title = item.title,
                    onClick = { onItemClick(item) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Indicator
        val middleIndex = items.size / 2
        val totalSlots = if (centerItem != null) items.size + 1 else items.size

        // Calculate the slot index for the selected item
        val selectedSlotIndex = if (centerItem != null && selectedItemIndex >= middleIndex) {
            selectedItemIndex + 1
        } else {
            selectedItemIndex
        }

        val indicatorHeight = 4.dp

        BoxWithConstraints(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(barHeight)
        ) {
            val slotWidth = maxWidth / totalSlots
            val indicatorWidth = 40.dp
            val indicatorOffset by animateDpAsState(
                targetValue = (slotWidth * selectedSlotIndex) + (slotWidth - indicatorWidth) / 2
            )

            Box(
                Modifier
                    .offset(x = indicatorOffset, y = 0.dp)
                    .clip(RoundedCornerShape(bottomEnd = 4.dp, bottomStart = 4.dp))
                    .background(Theme.colorScheme.icon.primary)
                    .size(indicatorWidth, indicatorHeight)
            )
        }

        // Centre FAB
        if (centerItem != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .size(fabSize)
                    .shadow(elevation = 8.dp, shape = CircleShape)
                    .background(Theme.colorScheme.icon.primary, shape = CircleShape)
                    .clickable(onClick = centerItem.entry),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = centerItem.icon,
                    contentDescription = "Action",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }
}

class BottomNavShape(
    private val cutoutRadius: Dp,
    private val cornerRadius: Dp = 16.dp
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(Path().apply {
            val cornerRadiusPx = with(density) { cornerRadius.toPx() }
            val cutoutRadiusPx = with(density) { cutoutRadius.toPx() }
            val middleX = size.width / 2

            moveTo(0f, cornerRadiusPx)
            arcTo(Rect(0f, 0f, cornerRadiusPx * 2, cornerRadiusPx * 2), 180f, 90f, false)

            val cutoutWidth = cutoutRadiusPx * 2.8f
            lineTo(middleX - cutoutWidth / 2, 0f)

            cubicTo(
                middleX - cutoutWidth / 4, 0f,
                middleX - cutoutWidth / 4, cutoutRadiusPx,
                middleX, cutoutRadiusPx
            )
            cubicTo(
                middleX + cutoutWidth / 4, cutoutRadiusPx,
                middleX + cutoutWidth / 4, 0f,
                middleX + cutoutWidth / 2, 0f
            )

            lineTo(size.width - cornerRadiusPx, 0f)
            arcTo(Rect(size.width - cornerRadiusPx * 2, 0f, size.width, cornerRadiusPx * 2), 270f, 90f, false)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        })
    }
}

@Preview
@Composable
private fun PreviewBottomNavigationBar() {
    SpendooTheme {
        val items = listOf(
            BottomNavigationItem(
                selectedIcon = painterResource(Res.drawable.ic_home_selected),
                notSelectedIcon = painterResource(Res.drawable.ic_home),
                title = "Home",
                entry = {}
            ),
            BottomNavigationItem(
                selectedIcon = painterResource(Res.drawable.ic_home_selected),
                notSelectedIcon = painterResource(Res.drawable.ic_home),
                title = "Dukan",
                entry = {}
            ),
            BottomNavigationItem(
                selectedIcon = painterResource(Res.drawable.ic_home_selected),
                notSelectedIcon = painterResource(Res.drawable.ic_home),
                title = "Trends",
                entry = {}
            ),
            BottomNavigationItem(
                selectedIcon = painterResource(Res.drawable.ic_home_selected),
                notSelectedIcon = painterResource(Res.drawable.ic_home),
                title = "Profile",
                entry = {}
            )
        )

        BottomNavigationBarContent(
            items = items,
            centerItem = CenterNavigationItem(
                icon = painterResource(Res.drawable.ic_home),
                entry = {}
            ),
            selectedItemIndex = 0,
            onItemClick = {},
            modifier = Modifier.background(Theme.colorScheme.background.primary)
        )
    }
}
