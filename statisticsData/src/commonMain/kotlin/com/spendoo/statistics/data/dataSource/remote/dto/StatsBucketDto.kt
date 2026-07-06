package com.spendoo.statistics.data.dataSource.remote.dto

import com.spendoo.shared.domain.utils.toLocalDateTimeOrDefault
import com.spendoo.statistics.domain.entity.StatsBucket
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StatsBucketDto(
    @SerialName("spending")
    val spending: Double,
    @SerialName("income")
    val income: Double,
    @SerialName("budget")
    val budget: Double,
    @SerialName("startDate")
    val startDate: String,
    @SerialName("predicted")
    val predicted: Boolean
)

fun StatsBucketDto.toDomain(): StatsBucket {
    return StatsBucket(
        spending = this.spending,
        income = this.income,
        budget = this.budget,
        startDate = this.startDate.toLocalDateTimeOrDefault(),
        isPredicted = this.predicted
    )
}
