package com.spendoo.categories.presentation.screen.transactionDetails

import com.spendoo.categories.domain.entity.transaction.Transaction
import com.spendoo.categories.domain.entity.transaction.TransactionType
import com.spendoo.shared.domain.entity.CategoryIcon
import kotlinx.datetime.LocalDateTime
import kotlin.math.absoluteValue

data class TransactionDetailsUiState(
    val isLoading: Boolean = true,
    val transactionId: String = "",
    val title: String = "",
    val amount: String = "",
    val categoryId: String = "",
    val categoryName: String = "",
    val categoryIcon: CategoryIcon = CategoryIcon.DEFAULT,
    val dateText: String = "",
    val timeText: String = "",
    val note: String? = null,
    val type: TransactionType = TransactionType.EXPENSE,
    val isDeletable: Boolean = true
)

fun Transaction.toDetailsUiState(): TransactionDetailsUiState {
    val categoryName = category?.categoryName ?: ""
    val categoryIcon = category?.categoryIcon ?: CategoryIcon.DEFAULT
    val amtInt = amount.toInt().absoluteValue
    val amtStr = if (amount % 1.0 == 0.0) amtInt.toString() else amount.toString()

    return TransactionDetailsUiState(
        isLoading = false,
        transactionId = id,
        title = title,
        amount = amtStr,
        categoryId = category?.categoryId ?: "",
        categoryName = categoryName,
        categoryIcon = categoryIcon,
        dateText = formatDetailsDate(date),
        timeText = formatDetailsTime(date),
        note = note,
        type = type,
        isDeletable = type != TransactionType.BUDGET
    )
}

fun formatDetailsDate(dateStr: String): String {
    val localDateTime = try {
        LocalDateTime.parse(dateStr)
    } catch (e: Exception) {
        return dateStr
    }
    val monthName = localDateTime.month.name.lowercase().replaceFirstChar { it.uppercase() }.take(3)
    val day = localDateTime.day
    val year = localDateTime.year
    return "$monthName $day, $year"
}

fun formatDetailsTime(dateStr: String): String {
    val localDateTime = try {
        LocalDateTime.parse(dateStr)
    } catch (e: Exception) {
        return ""
    }
    val hour24 = localDateTime.hour
    val minute = localDateTime.minute.toString().padStart(2, '0')
    val amPm = if (hour24 >= 12) "PM" else "AM"
    val hour12 = when {
        hour24 == 0 -> 12
        hour24 > 12 -> hour24 - 12
        else -> hour24
    }
    return "$hour12:$minute $amPm"
}
