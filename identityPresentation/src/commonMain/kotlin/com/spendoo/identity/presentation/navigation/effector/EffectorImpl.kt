package com.spendoo.identity.presentation.navigation.effector

import androidx.navigation.NavOptions
import com.spendoo.identity.presentation.navigation.BaseRoute
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class EffectorImpl : Effector {
    private val _effect = MutableSharedFlow<Effect>()
    override val effect = _effect.asSharedFlow()
    private val mutex = Mutex()
    private var lastNavigateTime = 0L
    private var lastPopBackStackTime = 0L
    private var lastPopUpToTime = 0L

    override val backStackArgsFlow: Flow<Map<String, Any>> =
        effect.filterIsInstance<Effect.PopBackStack>().map { it.arguments }

    override suspend fun navigate(
        route: BaseRoute,
        navOptions: NavOptions?,
        forceNavigate: Boolean
    ) {
        mutex.withLock {
            val now = getNow()
            if (forceNavigate || now - lastNavigateTime >= EFFECT_DEBOUNCE_MS) {
                lastNavigateTime = now
                _effect.emit(Effect.Navigate(route = route, navOptions = navOptions))
            }
        }
    }

    override suspend fun popBackStack(vararg arguments: Pair<String, Any>) {
        mutex.withLock {
            val now = getNow()
            if (now - lastPopBackStackTime >= EFFECT_DEBOUNCE_MS) {
                lastPopBackStackTime = now
                _effect.emit(Effect.PopBackStack(arguments.toMap()))
            }
        }
    }

    override suspend fun popUpTo(
        route: BaseRoute,
        inclusive: Boolean,
        saveState: Boolean
    ) {
        mutex.withLock {
            val now = getNow()
            if (now - lastPopUpToTime >= EFFECT_DEBOUNCE_MS) {
                lastPopUpToTime = now
                _effect.emit(
                    Effect.PopUpTo(
                        route = route,
                        inclusive = inclusive,
                        saveState = saveState
                    )
                )
            }
        }
    }

    override suspend fun setBackStackArgs(vararg arguments: Pair<String, Any>) {
        _effect.emit(Effect.SetBackStackArgs(arguments.toMap()))
    }

    companion object {
        private const val EFFECT_DEBOUNCE_MS = 300L
    }

    @OptIn(ExperimentalTime::class)
    fun getNow(): Long = Clock.System.now().toEpochMilliseconds()
}