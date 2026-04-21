package com.spendoo.categories.data.dataSource.remote.dto.transaction

import com.spendoo.categories.domain.entity.transaction.ExpenseEntry
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExpenseEntryDto(
    @SerialName("title")
    val title: String,
    @SerialName("amount")
    val amount: Double,
    @SerialName("categoryId")
    val categoryId: String,
    @SerialName("transactionDate")
    val transactionDate: String,
    @SerialName("note")
    val note: String? = null,
)

fun ExpenseEntry.toDto(): ExpenseEntryDto = ExpenseEntryDto(title, amount, categoryId, transactionDate, note)
