package com.spendoo.categories.data.dataSource.remote.dto.category

import com.spendoo.shared.domain.entity.CategoryIcon
import com.spendoo.categories.domain.entity.category.LeftOverOption
import com.spendoo.categories.domain.entity.category.Category
import com.spendoo.categories.domain.entity.category.toPriorityOption
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    @SerialName("categoryId")
    val categoryId: String,
    @SerialName("categoryName")
    val categoryName: String,
    @SerialName("categoryIcon")
    val categoryIcon: CategoryIcon,
    @SerialName("priority")
    val priority: Int,
    @SerialName("leftOverOptions")
    val leftOverOptions: LeftOverOption,
    @SerialName("budget")
    val budget: BudgetDto,
)

fun CategoryDto.toDomain(): Category = Category(
    categoryId = categoryId,
    categoryName = categoryName,
    categoryIcon = categoryIcon,
    priority = priority.toPriorityOption(),
    leftOverOption = leftOverOptions,
    budget = budget.toDomain(),
)