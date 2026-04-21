package com.spendoo.categories.data.dataSource.remote.dto.transaction

import com.spendoo.categories.domain.entity.transaction.IncomeEntry
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IncomeEntryDto(
    @SerialName("title")
    val title: String,
    @SerialName("amount")
    val amount: Double,
    @SerialName("transactionDate")
    val transactionDate: String,
    @SerialName("note")
    val note: String? = null,
)

fun IncomeEntry.toDto(): IncomeEntryDto = IncomeEntryDto(title, amount, transactionDate, note)
