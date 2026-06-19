package com.spendoo.goals.data.dataSource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AssignAmountRequestDto(
    @SerialName("amount")
    val amount: Double
)
