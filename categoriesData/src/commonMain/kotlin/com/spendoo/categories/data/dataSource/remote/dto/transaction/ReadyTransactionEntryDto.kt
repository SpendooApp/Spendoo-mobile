package com.spendoo.categories.data.dataSource.remote.dto.transaction

import com.spendoo.categories.domain.entity.transaction.ReadyTransactionEntry
import kotlinx.serialization.Serializable

@Serializable
data class ReadyTransactionEntryDto(
    val title: String,
    val amount: Double,
    val categoryId: String? = null,
    val note: String? = null
)

fun ReadyTransactionEntryDto.toDomain() = ReadyTransactionEntry(
    title = title,
    amount = amount,
    categoryId = categoryId,
    note = note
)
