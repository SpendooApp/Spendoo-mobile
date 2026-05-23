package com.spendoo.appEntryPoint

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.IntOffset
import com.spendoo.categories.api.CategoriesFeatureApi
import com.spendoo.designsystem.components.bottomNavigation.BottomNavigationBar
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.asString
import com.spendoo.home.api.HomeFeatureApi
import com.spendoo.identity.api.IdentityFeatureApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.categories
import spendoo.designsystem.generated.resources.ic_categories
import spendoo.designsystem.generated.resources.ic_home
import spendoo.designsystem.generated.resources.ic_home_selected
import spendoo.designsystem.generated.resources.ic_plus
import spendoo.designsystem.generated.resources.ic_robot
import spendoo.designsystem.generated.resources.ic_stats2
import spendoo.designsystem.generated.resources.home
import spendoo.designsystem.generated.resources.stats
import spendoo.designsystem.generated.resources.chatbot

@Composable
fun LoggedInContainer(
    state: MainEntryState,
    listener: MainEntryInteractionListener,
) {
    val animationSpec = tween<Float>(easing = EaseOut)
    val animationSpecs = tween<IntOffset>(easing = EaseOut)

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        FeatureContent(
            activeFeature = state.activeFeature,
            updateBottomNavigationVisibility = listener::onBottomNavigationChanged,
            showSnackBar = listener::showSnackBar,
            reloadRequestId = state.reloadRequestId,
            reloadTarget = state.reloadTarget,
            isAddTransactionBottomSheetVisible = state.isAddTransactionBottomSheetVisible,
            onAddTransactionDismissed = listener::onAddTransactionDismissed,
            onTransactionAdded = listener::onTransactionAdded
        )

        AnimatedVisibility(
            state.showBottomNavigation,
            enter = fadeIn(animationSpec) + slideInVertically(animationSpecs) { it },
            exit = fadeOut(animationSpec) + slideOutVertically(animationSpecs) { it },
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                BottomNavigationBar(
                    selectedItemIndex = getSelectedNavigationIndex(state.activeFeature),
                ) {
                    bottomNavigationItem(
                        selectedIcon = painterResource(Res.drawable.ic_home_selected),
                        notSelectedIcon = painterResource(Res.drawable.ic_home),
                        title = Res.string.home.asString(),
                        entry = { listener.setActiveFeature(Feature.Home) }
                    )

                    bottomNavigationItem(
                        selectedIcon = painterResource(Res.drawable.ic_categories),
                        notSelectedIcon = painterResource(Res.drawable.ic_categories),
                        title = Res.string.categories.asString(),
                        entry = { listener.setActiveFeature(Feature.Categories) }
                    )

                    centerItem(
                        icon = painterResource(Res.drawable.ic_plus),
                        entry = { listener.onAddTransactionRequested() }
                    )

                    bottomNavigationItem(
                        selectedIcon = painterResource(Res.drawable.ic_stats2),
                        notSelectedIcon = painterResource(Res.drawable.ic_stats2),
                        title = Res.string.stats.asString(),
                        entry = { listener.setActiveFeature(Feature.Stats) }
                    )

                    bottomNavigationItem(
                        selectedIcon = painterResource(Res.drawable.ic_robot),
                        notSelectedIcon = painterResource(Res.drawable.ic_robot),
                        title = Res.string.chatbot.asString(),
                        entry = { listener.setActiveFeature(Feature.ChatBot) }
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
}

@Composable
private fun FeatureContent(
    activeFeature: Feature,
    identityApi: IdentityFeatureApi = koinInject(),
    homeApi: HomeFeatureApi = koinInject(),
    categoriesApi: CategoriesFeatureApi = koinInject(),
    statsApi: HomeFeatureApi = koinInject(),
    chatBotApi: HomeFeatureApi = koinInject(),
    paymentsApi: HomeFeatureApi = koinInject(),
    modifier: Modifier = Modifier,
    updateBottomNavigationVisibility: (Boolean) -> Unit = {},
    showSnackBar: (String, String?, Boolean, Painter?, Long?, Color) -> Unit,
    reloadRequestId: Long,
    reloadTarget: Feature?,
    isAddTransactionBottomSheetVisible: Boolean,
    onAddTransactionDismissed: () -> Unit,
    onTransactionAdded: () -> Unit
) {
    Box(modifier) {
        Crossfade(targetState = activeFeature) { feature ->
            when (feature) {
                Feature.Home -> homeApi.TabEntry(
                    updateBottomNavigationVisibility = updateBottomNavigationVisibility,
                    showSnackBar = showSnackBar,
                    reloadSignal = reloadRequestId,
                    shouldReload = reloadTarget == Feature.Home
                )

                Feature.Categories -> categoriesApi.TabEntry(
                    updateBottomNavigationVisibility = updateBottomNavigationVisibility,
                    reloadSignal = reloadRequestId,
                    showSnackBar = showSnackBar,
                    shouldReload = reloadTarget == Feature.Categories
                )

                Feature.Stats -> statsApi.TabEntry(
                    updateBottomNavigationVisibility = updateBottomNavigationVisibility,
                    reloadSignal = reloadRequestId,
                    shouldReload = reloadTarget == Feature.Stats
                )

                Feature.ChatBot -> chatBotApi.TabEntry(
                    updateBottomNavigationVisibility = updateBottomNavigationVisibility,
                    reloadSignal = reloadRequestId,
                    shouldReload = reloadTarget == Feature.ChatBot
                )

                Feature.Profile -> identityApi.TabEntry(
                    updateBottomNavigationVisibility,
                    showSnackBar
                )

                Feature.Payments -> paymentsApi.TabEntry(
                    updateBottomNavigationVisibility = updateBottomNavigationVisibility,
                    reloadSignal = reloadRequestId,
                    shouldReload = reloadTarget == Feature.Payments
                )
            }
        }
    }
    if (isAddTransactionBottomSheetVisible) {
        categoriesApi.AddTransactionBottomSheet(
            isVisible = isAddTransactionBottomSheetVisible,
            onDismiss = onAddTransactionDismissed,
            onTransactionAdded = onTransactionAdded,
            showSnackBar = showSnackBar,
            updateBottomNavigationVisibility = updateBottomNavigationVisibility,
            reloadSignal = reloadRequestId,
            shouldReload = reloadTarget == Feature.Categories,
        )
    }
}

private fun getSelectedNavigationIndex(activeFeature: Feature): Int {
    return when (activeFeature) {
        Feature.Home -> 0
        Feature.Categories -> 1
        Feature.Stats -> 2
        Feature.ChatBot -> 3
        Feature.Profile -> -1 // Not in bottom nav
        Feature.Payments -> -1 // In centre FAB
    }
}
