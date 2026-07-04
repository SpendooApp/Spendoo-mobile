package com.spendoo.categories.data.dataSource.remote.dto.transaction

import com.spendoo.categories.domain.entity.transaction.FrequencyItem
import com.spendoo.shared.domain.entity.CategoryIcon
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FrequencyItemDto(
    @SerialName("itemName")
    val itemName: String,
    @SerialName("frequency")
    val frequency: Long,
    @SerialName("totalAmount")
    val totalAmount: Double,
    @SerialName("categoryId")
    val categoryId: String,
    @SerialName("categoryIcon")
    val categoryIcon: CategoryIcon = CategoryIcon.DEFAULT,
)

fun FrequencyItemDto.toDomain(): FrequencyItem = FrequencyItem(
    itemName = itemName,
    frequency = frequency,
    totalAmount = totalAmount,
    categoryId = categoryId,
    categoryIcon = categoryIcon,
)
