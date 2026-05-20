package com.spendoo.categories.data.dataSource.remote.dto.transaction

import com.spendoo.categories.domain.entity.transaction.UpdateTransaction
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateTransactionDto(
    @SerialName("title")
    val title: String,
    @SerialName("transactionDate")
    val transactionDate: String,
    @SerialName("note")
    val note: String? = null,
    @SerialName("amount")
    val amount: Double,
    @SerialName("categoryId")
    val categoryId: String? = null,
)

fun UpdateTransaction.toDto(): UpdateTransactionDto =
    UpdateTransactionDto(title, transactionDate, note, amount, categoryId)