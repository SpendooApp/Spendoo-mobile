package com.spendoo.identity.presentation.navigation.effector

import androidx.navigation.NavOptions
import com.spendoo.identity.presentation.navigation.BaseRoute
import kotlinx.coroutines.flow.Flow

interface Effector {
    val effect: Flow<Effect>
    val backStackArgsFlow: Flow<Map<String, Any>>

    suspend fun navigate(
        route: BaseRoute,
        navOptions: NavOptions? = null,
        forceNavigate: Boolean = false
    )

    suspend fun popBackStack(vararg arguments: Pair<String, Any>)
    suspend fun popUpTo(route: BaseRoute, inclusive: Boolean = false, saveState: Boolean = false)
    suspend fun setBackStackArgs(vararg arguments: Pair<String, Any>)
    suspend fun updateBottomNavigationVisibility(isVisible: Boolean)
}