package com.spendoo.categories.data.dataSource.remote.dto.category

import com.spendoo.categories.domain.entity.category.CategoryIcon
import com.spendoo.categories.domain.entity.category.LeftOverOption
import com.spendoo.categories.domain.entity.category.CreateCategory
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateCategoryDto(
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

fun CreateCategory.toDto(): CreateCategoryDto =
    CreateCategoryDto(categoryName, categoryIcon, leftOverOptions, priority, budget.toDto())