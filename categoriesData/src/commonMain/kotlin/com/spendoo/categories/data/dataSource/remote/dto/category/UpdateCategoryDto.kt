package com.spendoo.categories.data.dataSource.remote.dto.category

import com.spendoo.categories.domain.entity.category.CategoryIcon
import com.spendoo.categories.domain.entity.category.LeftOverOption
import com.spendoo.categories.domain.entity.category.UpdateCategory
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateCategoryDto(
    @SerialName("categoryName")
    val categoryName: String,
    @SerialName("categoryIcon")
    val categoryIcon: CategoryIcon,
    @SerialName("leftOverOptions")
    val leftOverOptions: LeftOverOption,
    @SerialName("priority")
    val priority: Int,
    @SerialName("budget")
    val budget: CreateBudgetDto,
)

fun UpdateCategory.toDto(): UpdateCategoryDto =
    UpdateCategoryDto(categoryName, categoryIcon, leftOverOptions, priority, budget.toDto())