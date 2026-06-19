package com.spendoo.designsystem.components.bottomNavigation

import androidx.compose.ui.graphics.painter.Painter

interface BottomNavigationScope {
    fun bottomNavigationItem(
        notSelectedIcon: Painter,
        selectedIcon: Painter,
        title: String,
        entry: () -> Unit,
    ) {
        error("The method is not implemented")
    }

    fun centerItem(
        icon: Painter,
        entry: () -> Unit,
    )
}

data class BottomNavigationItem(
    val notSelectedIcon: Painter,
    val selectedIcon: Painter,
    val title: String,
    val entry: () -> Unit,
)

internal class BottomNavigationScopeImpl : BottomNavigationScope {
    val items = mutableListOf<BottomNavigationItem>()
    var centerItem: CenterNavigationItem? = null

    override fun bottomNavigationItem(
        notSelectedIcon: Painter,
        selectedIcon: Painter,
        title: String,
        entry: () -> Unit,
    ) {
        items.add(BottomNavigationItem(notSelectedIcon, selectedIcon, title, entry))
    }

    override fun centerItem(icon: Painter, entry: () -> Unit) {
        centerItem = CenterNavigationItem(icon, entry)
    }
}

data class CenterNavigationItem(
    val icon: Painter,
    val entry: () -> Unit
)
