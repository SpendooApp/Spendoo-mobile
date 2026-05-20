package com.spendoo.categories.data.dataSource.remote.dto.category

import com.spendoo.categories.domain.entity.category.CategoryIcon
import com.spendoo.categories.domain.entity.category.LeftOverOption
import com.spendoo.categories.domain.entity.category.CategoryLite
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryLiteDto(
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
)

fun CategoryLiteDto.toDomain(): CategoryLite = CategoryLite(
    categoryId = categoryId,
    categoryName = categoryName,
    categoryIcon = categoryIcon,
    priority = priority,
    leftOverOptions = leftOverOptions,
)