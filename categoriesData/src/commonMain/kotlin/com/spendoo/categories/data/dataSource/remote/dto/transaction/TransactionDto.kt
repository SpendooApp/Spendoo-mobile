package com.spendoo.categories.data.dataSource.remote.dto.transaction

import com.spendoo.categories.data.dataSource.remote.dto.category.CategoryLiteDto
import com.spendoo.categories.data.dataSource.remote.dto.category.toDomain
import com.spendoo.categories.domain.entity.transaction.Transaction
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransactionDto(
    @SerialName("id")
    val id: String,
    @SerialName("title")
    val title: String,
    @SerialName("amount")
    val amount: Double,
    @SerialName("note")
    val note: String? = null,
    @SerialName("date")
    val date: String,
    @SerialName("categoryResponse")
    val category: CategoryLiteDto? = null,
)

fun TransactionDto.toDomain(): Transaction = Transaction(
    id = id,
    title = title,
    amount = amount,
    note = note,
    date = date,
    category = category?.toDomain(),
)