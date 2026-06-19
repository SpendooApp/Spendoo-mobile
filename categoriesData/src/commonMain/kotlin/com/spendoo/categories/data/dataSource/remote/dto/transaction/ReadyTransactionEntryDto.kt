package com.spendoo.categories.data.dataSource.remote.dto.transaction

import com.spendoo.shared.domain.entity.CategoryIcon
import com.spendoo.categories.domain.entity.transaction.ReadyTransactionEntry
import kotlinx.serialization.Serializable

@Serializable
data class EnrichedAiExtractionResponseDto(
    val items: List<EnrichedAiExtractionItemDto> = emptyList(),
    val grandTotal: Double? = null,
    val model: String? = null,
)

@Serializable
data class EnrichedAiExtractionItemDto(
    val id: Long? = null,
    val itemName: String? = null,
    val price: Double? = null,
    val category: String? = null,
    val categoryId: String? = null,
    val categoryName: String? = null,
    val categoryIcon: CategoryIcon? = null
)

fun EnrichedAiExtractionItemDto.toDomain() = ReadyTransactionEntry(
    title = itemName.orEmpty(),
    amount = price ?: 0.0,
    categoryId = categoryId,
    note = category,
    categoryName = categoryName,
    categoryIcon = categoryIcon
)
