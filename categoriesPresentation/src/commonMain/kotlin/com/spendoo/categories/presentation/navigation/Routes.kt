package com.spendoo.categories.presentation.navigation

import kotlinx.serialization.Serializable

interface BaseRoute

@Serializable
data object CategoriesRoute : BaseRoute

@Serializable
data object AddTransactionRoute : BaseRoute