package com.spendoo.categories.presentation.screen.addCategoryBottomSheet

import androidx.compose.runtime.Composable
import com.spendoo.categories.domain.entity.category.Category
import com.spendoo.categories.domain.entity.category.CategoryIcon
import com.spendoo.categories.domain.entity.category.CreateBudget
import com.spendoo.categories.domain.entity.category.CreateCategory
import com.spendoo.categories.domain.entity.category.LeftOverOption
import com.spendoo.categories.domain.entity.category.PriorityOption
import com.spendoo.categories.domain.entity.category.ResetCycleOption
import com.spendoo.categories.domain.entity.category.UpdateCategory
import com.spendoo.categories.domain.entity.category.toInt
import com.spendoo.categories.presentation.shared.getToday
import com.spendoo.designsystem.components.general.GenSelectableOption
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.daily
import spendoo.designsystem.generated.resources.high
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
import spendoo.designsystem.generated.resources.low
import spendoo.designsystem.generated.resources.medium
import spendoo.designsystem.generated.resources.monthly
import spendoo.designsystem.generated.resources.move_to_next_period
import spendoo.designsystem.generated.resources.move_to_savings
import spendoo.designsystem.generated.resources.reset_to_original_amount
import spendoo.designsystem.generated.resources.weekly
import spendoo.designsystem.generated.resources.yearly
import kotlin.time.Instant

data class AddEditCategoryUiState(
    val categoryId: String? = null,
    val categoryName: String = "",
    val budget: Double? = null,
    val budgetStartDate: LocalDate? = null,
    val leftoverFundsAction: LeftOverOption = LeftOverOption.MOVE_TO_SAVINGS,
    val priority: PriorityOption = PriorityOption.MEDIUM,
    val icon: CategoryIcon = CategoryIcon.DEFAULT,
    val resetCycle: ResetCycleOption = ResetCycleOption.MONTHLY,
    val showDatePicker: Boolean = false,
    val showLeftoverFundsActionSheet: Boolean = false,
)

fun AddEditCategoryUiState.toCreateCategory(): CreateCategory {
    return CreateCategory(
        categoryName = categoryName,
        categoryIcon = icon,
        priority = priority.ordinal,
        leftOverOptions = leftoverFundsAction,
        budget = CreateBudget(
            amount = budget ?: 0.0,
            period = resetCycle.toInt(),
            startDate = budgetStartDate ?: getToday()
        )
    )
}

fun AddEditCategoryUiState.toUpdateCategory(): UpdateCategory {
    return UpdateCategory(
        categoryName = categoryName,
        categoryIcon = icon,
        priority = priority.ordinal,
        leftOverOptions = leftoverFundsAction,
        budget = CreateBudget(
            amount = budget ?: 0.0,
            period = resetCycle.toInt(),
            startDate = budgetStartDate ?: getToday()
        )
    )
}

fun Category.toAddEditCategoryUiState(): AddEditCategoryUiState {
    return AddEditCategoryUiState(
        categoryId = categoryId,
        categoryName = categoryName,
        budget = budget.amount,
        budgetStartDate = budget.startDate.date,
        leftoverFundsAction = leftOverOption,
        priority = priority,
        icon = categoryIcon,
        resetCycle = budget.period
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

fun LeftOverOption.toStringResource(): StringResource {
    return when (this) {
        LeftOverOption.MOVE_TO_NEXT_PERIOD -> Res.string.move_to_next_period
        LeftOverOption.RESET_TO_ORIGINAL_AMOUNT -> Res.string.reset_to_original_amount
        LeftOverOption.MOVE_TO_SAVINGS -> Res.string.move_to_savings
    }
}

fun ResetCycleOption.toStringResource(): StringResource {
    return when (this) {
        ResetCycleOption.DAILY -> Res.string.daily
        ResetCycleOption.WEEKLY -> Res.string.weekly
        ResetCycleOption.MONTHLY -> Res.string.monthly
        ResetCycleOption.YEARLY -> Res.string.yearly
    }
}

fun PriorityOption.toStringResource(): StringResource {
    return when (this) {
        PriorityOption.LOW -> Res.string.low
        PriorityOption.MEDIUM -> Res.string.medium
        PriorityOption.HIGH -> Res.string.high
    }
}

@Composable
fun LeftOverOption.toSelectableOptions(): GenSelectableOption<LeftOverOption> {
    return when (this) {
        LeftOverOption.MOVE_TO_NEXT_PERIOD -> GenSelectableOption(
            LeftOverOption.MOVE_TO_NEXT_PERIOD,
            Res.string.move_to_next_period
        )

        LeftOverOption.RESET_TO_ORIGINAL_AMOUNT -> GenSelectableOption(
            LeftOverOption.RESET_TO_ORIGINAL_AMOUNT,
            Res.string.reset_to_original_amount
        )

        LeftOverOption.MOVE_TO_SAVINGS -> GenSelectableOption(
            LeftOverOption.MOVE_TO_SAVINGS,
            Res.string.move_to_savings
        )
    }
}

@Composable
fun List<LeftOverOption>.toSelectableOptions(): List<GenSelectableOption<LeftOverOption>> {
    return this.map { it.toSelectableOptions() }
}