package com.spendoo.home.presentation.screen

import com.spendoo.categories.domain.entity.category.CategoryIcon
import com.spendoo.categories.domain.entity.transaction.BalanceSummary
import com.spendoo.categories.domain.entity.transaction.CategorySpending
import com.spendoo.goals.domain.entity.Goal
import com.spendoo.offers.domain.entity.Offer
import com.spendoo.offers.domain.entity.Spending
import org.jetbrains.compose.resources.DrawableResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.ic_car
import spendoo.designsystem.generated.resources.ic_categories
import spendoo.designsystem.generated.resources.ic_cinema
import spendoo.designsystem.generated.resources.ic_drink
import spendoo.designsystem.generated.resources.ic_food
import spendoo.designsystem.generated.resources.ic_gift
import spendoo.designsystem.generated.resources.ic_gym
import spendoo.designsystem.generated.resources.ic_health
import spendoo.designsystem.generated.resources.ic_loan
import spendoo.designsystem.generated.resources.ic_mobile
import spendoo.designsystem.generated.resources.ic_pet
import spendoo.designsystem.generated.resources.ic_shopping
import spendoo.designsystem.generated.resources.ic_stats
import spendoo.designsystem.generated.resources.ic_thunder
import spendoo.designsystem.generated.resources.ic_transportation
import spendoo.designsystem.generated.resources.ic_travel
import spendoo.designsystem.generated.resources.ic_wifi

data class HomeUiState(
    val isBalanceLoading: Boolean = false,
    val isUserLoading: Boolean = false,
    val isOffersLoading: Boolean = false,
    val isGoalsLoading: Boolean = false,
    val isTopSpendingLoading: Boolean = false,
    val isNotificationsLoading: Boolean = false,
    val balanceSummary: BalanceSummaryUiState = BalanceSummaryUiState(),
    val offers: List<OfferUiState> = emptyList(),
    val goals: List<GoalUiState> = emptyList(),
    val topSpending: List<SpendingUiState> = emptyList(),
    val userData: UserData = UserData(
        userName = "",
        userImageUrl = null,
        notificationsCount = 0
    )
//    val error: String? = null
)

data class BalanceSummaryUiState(
    val totalBalance: Double = 0.0,
    val income: Double = 0.0,
    val expenses: Double = 0.0,
)

fun BalanceSummary.toUiState(): BalanceSummaryUiState {
    return BalanceSummaryUiState(
        totalBalance = totalBalance,
        income = income,
        expenses = expenses
    )
}

data class OfferUiState(
    val id: String,
    val discountPercent: Int?,
    val imageUrl: String?
)

fun Offer.toUiState(): OfferUiState {
    return OfferUiState(
        id = id,
        discountPercent = discountPercent,
        imageUrl = imageUrl
    )
}

data class GoalUiState(
    val id: String,
    val name: String,
    val icon: CategoryIcon,
    val progress: Float
)

fun Goal.toUiState(): GoalUiState {
    return GoalUiState(
        id = id,
        name = name,
        icon = icon,
        progress = progress
    )
}

fun CategoryIcon.toDrawableResource(): DrawableResource {
    return when (this) {
        CategoryIcon.DEFAULT -> Res.drawable.ic_categories
        CategoryIcon.FOOD -> Res.drawable.ic_food
        CategoryIcon.TRANSPORT -> Res.drawable.ic_travel
        CategoryIcon.ENTERTAINMENT -> Res.drawable.ic_cinema
        CategoryIcon.HEALTHCARE -> Res.drawable.ic_health
        CategoryIcon.EDUCATION -> Res.drawable.ic_stats
        CategoryIcon.SHOPPING -> Res.drawable.ic_shopping
        CategoryIcon.TRAVEL -> Res.drawable.ic_transportation
        CategoryIcon.CAR -> Res.drawable.ic_car
        CategoryIcon.MOBILE -> Res.drawable.ic_mobile
        CategoryIcon.FINANCE -> Res.drawable.ic_loan
        CategoryIcon.COFFEE -> Res.drawable.ic_drink
        CategoryIcon.GIFTS -> Res.drawable.ic_gift
        CategoryIcon.PETS -> Res.drawable.ic_pet
        CategoryIcon.FITNESS -> Res.drawable.ic_gym
        CategoryIcon.UTILITIES -> Res.drawable.ic_thunder
        CategoryIcon.WIFI -> Res.drawable.ic_wifi
    }
}

data class SpendingUiState(
    val id: String,
    val categoryName: String,
    val amount: Double,
    val icon: CategoryIcon
)

fun CategorySpending.toUiState(): SpendingUiState {
    return SpendingUiState(
        id = categoryId,
        categoryName = categoryName,
        amount = totalAmount,
        icon = categoryIcon
    )
}

data class UserData(
    val userName: String,
    val userImageUrl: String?,
    val notificationsCount: Int
)
