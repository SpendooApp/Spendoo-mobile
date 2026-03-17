package com.spendoo.identity.presentation.navigation.effector

import androidx.navigation.NavOptions
import com.spendoo.identity.presentation.navigation.BaseRoute

sealed class Effect {
    data class Navigate(val route: BaseRoute, val navOptions: NavOptions? = null) : Effect()
    data class PopBackStack(val arguments: Map<String, Any> = emptyMap()) : Effect()
    data class PopUpTo(
        val route: BaseRoute,
        val inclusive: Boolean = false,
        val saveState: Boolean = false
    ) : Effect()
    data class SetBackStackArgs(val arguments: Map<String, Any> = emptyMap()) : Effect()
    data class UpdateBottomNavigationVisibility(val isVisible: Boolean) : Effect()
}