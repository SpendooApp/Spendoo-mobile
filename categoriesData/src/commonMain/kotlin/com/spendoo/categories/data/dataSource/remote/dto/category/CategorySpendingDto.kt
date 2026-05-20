package com.spendoo.categories.data.dataSource.remote.dto.category

import com.spendoo.categories.domain.entity.category.CategoryIcon
import com.spendoo.categories.domain.entity.transaction.CategorySpending
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategorySpendingDto(
    @SerialName("categoryName")
    val categoryName: String,
    @SerialName("categoryIcon")
    val categoryIcon: CategoryIcon,
    @SerialName("totalAmount")
    val totalAmount: Double,
)

fun CategorySpendingDto.toDomain(): CategorySpending = CategorySpending(
    categoryName = categoryName,
    categoryIcon = categoryIcon,
    totalAmount = totalAmount,
)
