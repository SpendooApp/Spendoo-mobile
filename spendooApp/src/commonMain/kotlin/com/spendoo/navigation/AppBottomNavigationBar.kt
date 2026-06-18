package com.spendoo.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.navigation3.runtime.NavKey
import com.spendoo.appEntryPoint.MainEntryInteractionListener
import com.spendoo.categories.api.CategoriesRoute
import com.spendoo.designsystem.components.bottomNavigation.BottomNavigationBar
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.asString
import com.spendoo.home.api.ChatBotRoute
import com.spendoo.home.api.HomeRoute
import com.spendoo.statistics.api.StatisticsRoute
import org.jetbrains.compose.resources.painterResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.categories
import spendoo.designsystem.generated.resources.chatbot
import spendoo.designsystem.generated.resources.home
import spendoo.designsystem.generated.resources.ic_categories
import spendoo.designsystem.generated.resources.ic_home
import spendoo.designsystem.generated.resources.ic_home_selected
import spendoo.designsystem.generated.resources.ic_plus
import spendoo.designsystem.generated.resources.ic_robot
import spendoo.designsystem.generated.resources.ic_stats2
import spendoo.designsystem.generated.resources.stats

@Composable
fun BoxScope.AppBottomNavigationBar(
    showBottomNavigation: Boolean,
    activeFeature: NavKey?,
    interactionListener: MainEntryInteractionListener
) {
    val animationSpec = tween<Float>(easing = EaseOut)
    val animationSpecs = tween<IntOffset>(easing = EaseOut)

    AnimatedVisibility(
        showBottomNavigation,
        enter = fadeIn(animationSpec) + slideInVertically(animationSpecs) { it },
        exit = fadeOut(animationSpec) + slideOutVertically(animationSpecs) { it },
        modifier = Modifier.align(Alignment.BottomCenter)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            BottomNavigationBar(
                selectedItemIndex = getSelectedNavigationIndex(activeFeature),
            ) {
                bottomNavigationItem(
                    selectedIcon = painterResource(Res.drawable.ic_home_selected),
                    notSelectedIcon = painterResource(Res.drawable.ic_home),
                    title = Res.string.home.asString(),
                    entry = {
                        interactionListener.resetToRoute(HomeRoute)
                    }
                )

                bottomNavigationItem(
                    selectedIcon = painterResource(Res.drawable.ic_categories),
                    notSelectedIcon = painterResource(Res.drawable.ic_categories),
                    title = Res.string.categories.asString(),
                    entry = {
                        interactionListener.resetToRoute(CategoriesRoute)
                    }
                )

                centerItem(
                    icon = painterResource(Res.drawable.ic_plus),
                    entry = {
                        interactionListener.onAddTransactionClicked()
                    }
                )

                bottomNavigationItem(
                    selectedIcon = painterResource(Res.drawable.ic_stats2),
                    notSelectedIcon = painterResource(Res.drawable.ic_stats2),
                    title = Res.string.stats.asString(),
                    entry = {
                        interactionListener.resetToRoute(StatisticsRoute)
                    }
                )

                bottomNavigationItem(
                    selectedIcon = painterResource(Res.drawable.ic_robot),
                    notSelectedIcon = painterResource(Res.drawable.ic_robot),
                    title = Res.string.chatbot.asString(),
                    entry = {
                        interactionListener.resetToRoute(ChatBotRoute)
                    }
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Theme.colorScheme.background.secondary)
                    .navigationBarsPadding()
            )
        }
    }
}

private fun getSelectedNavigationIndex(route: NavKey?): Int {
    return when (route) {
        is HomeRoute -> 0
        is CategoriesRoute -> 1
        is StatisticsRoute -> 2
        is ChatBotRoute -> 3
        else -> -1
    }
}