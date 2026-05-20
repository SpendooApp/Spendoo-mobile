package com.spendoo.categories.data.dataSource.remote.dto.transaction

import com.spendoo.categories.domain.entity.transaction.CreateIncome
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateIncomeDto(
    @SerialName("entries")
    val entries: List<IncomeEntryDto>
)
fun CreateIncome.toDto(): CreateIncomeDto = CreateIncomeDto(entries = entries.map { it.toDto() })