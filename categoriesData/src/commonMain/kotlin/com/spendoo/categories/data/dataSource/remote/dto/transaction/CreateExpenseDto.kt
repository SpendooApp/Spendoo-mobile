package com.spendoo.categories.data.dataSource.remote.dto.transaction

import com.spendoo.categories.domain.entity.transaction.CreateExpense
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateExpenseDto(
    @SerialName("entries")
    val entries: List<ExpenseEntryDto>
)

fun CreateExpense.toDto(): CreateExpenseDto = CreateExpenseDto(entries = entries.map { it.toDto() })
