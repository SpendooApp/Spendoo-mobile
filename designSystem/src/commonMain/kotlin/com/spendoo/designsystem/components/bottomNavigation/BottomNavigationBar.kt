package com.spendoo.designsystem.components.bottomNavigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.spendoo.designsystem.theme.theme.SpendooTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.ic_home
import spendoo.designsystem.generated.resources.ic_home_selected

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    selectedItemIndex: Int = 0,
    content: @Composable BottomNavigationScope.() -> Unit = {},
) {
    val scope = remember { BottomNavigationScopeImpl() }.apply {
        items.clear()
        centerItem = null
        content()
    }

    BottomNavigationBarContent(
        items = scope.items,
        centerItem = scope.centerItem,
        selectedItemIndex = selectedItemIndex,
        onItemClick = { item ->
            val index = scope.items.indexOf(item)
            scope.items[index].entry.invoke()
        },
        modifier = modifier
    )
}

@Preview
@Composable
private fun PreviewBottomNavigationBar() {
    SpendooTheme {
        Box(Modifier.fillMaxWidth()) {
            BottomNavigationBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
            ) {
                bottomNavigationItem(
                    selectedIcon = painterResource(Res.drawable.ic_home_selected),
                    notSelectedIcon = painterResource(Res.drawable.ic_home),
                    title = "Home",
                    entry = { }
                )

                bottomNavigationItem(
                    selectedIcon = painterResource(Res.drawable.ic_home_selected),
                    notSelectedIcon = painterResource(Res.drawable.ic_home),
                    title = "Categories",
                    entry = { }
                )
            }
        }
    }
}