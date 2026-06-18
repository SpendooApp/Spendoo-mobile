package com.spendoo.designsystem.navigation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

//class Navigator(startDestination: Any) {
//    var backStack: SnapshotStateList<Any> = mutableStateListOf(startDestination)
//
//    fun navigate(route: Any) {
//        backStack.add(route)
//    }
//
//    fun pop(): Boolean {
//        return backStack.removeLastOrNull() != null
//    }
//
//    fun popTo(route: Any, inclusive: Boolean = false) {
//        val index = backStack.indexOfLast { it::class == route::class }
//        if (index != -1) {
//            val targetIndex = if (inclusive) index else index + 1
//            while (backStack.size > targetIndex) {
//                backStack.removeLastOrNull()
//            }
//        }
//    }
//
//    fun clearAndSet(route: Any) {
//        backStack.clear()
//        backStack.add(route)
//    }
//
//    fun replace(route: Any) {
//        backStack.add(route)
//        backStack.removeAt(backStack.size - 2)
//    }
//}
