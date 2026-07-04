package com.spendoo.statistics.presentation.screen.statistics

import com.spendoo.categories.domain.entity.scheduledPayment.ScheduledPayment
import com.spendoo.categories.domain.entity.transaction.Transaction
import com.spendoo.categories.domain.entity.transaction.TransactionType
import com.spendoo.designsystem.utils.UiText
import com.spendoo.designsystem.utils.extentions.toTimeLeftText
import com.spendoo.shared.domain.entity.CategoryIcon
import com.spendoo.statistics.domain.entity.BudgetStatus
import com.spendoo.statistics.domain.entity.BudgetStatusBucket
import com.spendoo.statistics.domain.entity.CombinedStats
import com.spendoo.statistics.domain.entity.Granularity
import com.spendoo.statistics.domain.entity.StatsBucket
import com.spendoo.statistics.presentation.screen.statistics.components.StatisticsScheduledPaymentUiState
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toInstant
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.amount_ascending
import spendoo.designsystem.generated.resources.amount_descending
import spendoo.designsystem.generated.resources.charts
import spendoo.designsystem.generated.resources.daily
import spendoo.designsystem.generated.resources.date_ascending
import spendoo.designsystem.generated.resources.date_descending
import spendoo.designsystem.generated.resources.expenses_last_day
import spendoo.designsystem.generated.resources.expenses_last_month
import spendoo.designsystem.generated.resources.expenses_last_week
import spendoo.designsystem.generated.resources.expenses_last_year
import spendoo.designsystem.generated.resources.ic_car
import spendoo.designsystem.generated.resources.ic_categories
import spendoo.designsystem.generated.resources.ic_cinema
import spendoo.designsystem.generated.resources.ic_drink
import spendoo.designsystem.generated.resources.ic_food
import spendoo.designsystem.generated.resources.ic_gift
import spendoo.designsystem.generated.resources.ic_gym
import spendoo.designsystem.generated.resources.ic_health
import spendoo.designsystem.generated.resources.ic_loan
import spendoo.designsystem.generated.resources.ic_mobile
import spendoo.designsystem.generated.resources.ic_pet
import spendoo.designsystem.generated.resources.ic_shopping
import spendoo.designsystem.generated.resources.ic_stats
import spendoo.designsystem.generated.resources.ic_thunder
import spendoo.designsystem.generated.resources.ic_transportation
import spendoo.designsystem.generated.resources.ic_travel
import spendoo.designsystem.generated.resources.ic_wifi
import spendoo.designsystem.generated.resources.month_apr
import spendoo.designsystem.generated.resources.month_aug
import spendoo.designsystem.generated.resources.month_day_apr
import spendoo.designsystem.generated.resources.month_day_aug
import spendoo.designsystem.generated.resources.month_day_dec
import spendoo.designsystem.generated.resources.month_day_feb
import spendoo.designsystem.generated.resources.month_day_jan
import spendoo.designsystem.generated.resources.month_day_jul
import spendoo.designsystem.generated.resources.month_day_jun
import spendoo.designsystem.generated.resources.month_day_mar
import spendoo.designsystem.generated.resources.month_day_may
import spendoo.designsystem.generated.resources.month_day_nov
import spendoo.designsystem.generated.resources.month_day_oct
import spendoo.designsystem.generated.resources.month_day_sep
import spendoo.designsystem.generated.resources.month_dec
import spendoo.designsystem.generated.resources.month_feb
import spendoo.designsystem.generated.resources.month_jan
import spendoo.designsystem.generated.resources.month_jul
import spendoo.designsystem.generated.resources.month_jun
import spendoo.designsystem.generated.resources.month_mar
import spendoo.designsystem.generated.resources.month_may
import spendoo.designsystem.generated.resources.month_nov
import spendoo.designsystem.generated.resources.month_oct
import spendoo.designsystem.generated.resources.month_sep
import spendoo.designsystem.generated.resources.monthly
import spendoo.designsystem.generated.resources.number_format
import spendoo.designsystem.generated.resources.transaction_date_am_format
import spendoo.designsystem.generated.resources.transaction_date_pm_format
import spendoo.designsystem.generated.resources.transactions
import spendoo.designsystem.generated.resources.week_label_format
import spendoo.designsystem.generated.resources.weekly
import spendoo.designsystem.generated.resources.yearly
import kotlin.math.absoluteValue

data class LineChartUiState(
    val budgetData: List<Double>,
    val spentData: List<Double>,
    val incomeData: List<Double>,
    val xAxisLabels: List<UiText>,
    val highestSpendingBucketIndex: Int,
    val dashedRanges: List<IntRange>
)

data class BarChartBucketUiState(
    val spending: Double,
    val status: BudgetStatus
)

data class BarChartUiState(
    val buckets: List<BarChartBucketUiState> = emptyList(),
    val xAxisLabels: List<UiText> = emptyList()
)

data class PieChartUiState(
    val spending: Double,
    val categoryName: String
)

data class StatisticsUiState(
    val isRefreshing: Boolean = false,
    val isLoading: Boolean = false,
    val selectedTab: StatisticsTab = StatisticsTab.CHARTS,
    val selectedGranularity: Granularity = Granularity.WEEK,
    val combinedStats: CombinedStats? = null,
    val lineChartUiState: LineChartUiState? = null,
    val barChartUiState: BarChartUiState? = null,
    val pieChartUiState: List<PieChartUiState> = emptyList(),
    val scheduledPayments: List<StatisticsScheduledPaymentUiState> = emptyList(),
    val isScheduledPaymentsError: Boolean = false,
    val userName: String = "",
    val userImageUrl: String? = null,
    val searchQuery: String = "",
    val transactions: List<StatisticsTransactionUiState> = emptyList(),
    val isTransactionsLoading: Boolean = false,
    val isTransactionsLoadingMore: Boolean = false,
    val isSortSheetVisible: Boolean = false,
    val isActionsSheetVisible: Boolean = false,
    val selectedSortOption: TransactionSortOption = TransactionSortOption.DATE_DESCENDING,
    val selectedTransaction: StatisticsTransactionUiState? = null
)

fun formatTransactionDate(date: LocalDateTime): UiText {
    val year = date.year.toString()
    val month = date.month.number.toString().padStart(2, '0')
    val day = date.day.toString().padStart(2, '0')
    
    val hour24 = date.hour
    val minute = date.minute.toString().padStart(2, '0')
    val isPm = hour24 >= 12
    val hour12 = when {
        hour24 == 0 -> 12
        hour24 > 12 -> hour24 - 12
        else -> hour24
    }.toString()
    val resId = if (isPm) Res.string.transaction_date_pm_format else Res.string.transaction_date_am_format
    return UiText.StringRes(resId, year, month, day, hour12, minute)
}

fun CategoryIcon.toDrawableResource(): DrawableResource {
    return when (this) {
        CategoryIcon.DEFAULT -> Res.drawable.ic_categories
        CategoryIcon.FOOD -> Res.drawable.ic_food
        CategoryIcon.TRANSPORT -> Res.drawable.ic_travel
        CategoryIcon.ENTERTAINMENT -> Res.drawable.ic_cinema
        CategoryIcon.HEALTHCARE -> Res.drawable.ic_health
        CategoryIcon.EDUCATION -> Res.drawable.ic_stats
        CategoryIcon.SHOPPING -> Res.drawable.ic_shopping
        CategoryIcon.TRAVEL -> Res.drawable.ic_transportation
        CategoryIcon.CAR -> Res.drawable.ic_car
        CategoryIcon.MOBILE -> Res.drawable.ic_mobile
        CategoryIcon.FINANCE -> Res.drawable.ic_loan
        CategoryIcon.COFFEE -> Res.drawable.ic_drink
        CategoryIcon.GIFTS -> Res.drawable.ic_gift
        CategoryIcon.PETS -> Res.drawable.ic_pet
        CategoryIcon.FITNESS -> Res.drawable.ic_gym
        CategoryIcon.UTILITIES -> Res.drawable.ic_thunder
        CategoryIcon.WIFI -> Res.drawable.ic_wifi
    }
}

enum class StatisticsTab {
    CHARTS,
    TRANSACTIONS
}

fun StatisticsTab.toName(): StringResource {
    return when (this) {
        StatisticsTab.CHARTS -> Res.string.charts
        StatisticsTab.TRANSACTIONS -> Res.string.transactions
    }
}

enum class TransactionSortOption(
    val title: StringResource,
    val sortParams: List<String>
) {
    DATE_DESCENDING(Res.string.date_descending, listOf("transactionDate,desc")),
    DATE_ASCENDING(Res.string.date_ascending, listOf("transactionDate,asc")),
    AMOUNT_DESCENDING(Res.string.amount_descending, listOf("amount,desc")),
    AMOUNT_ASCENDING(Res.string.amount_ascending, listOf("amount,asc"))
}

data class StatisticsTransactionUiState(
    val id: String,
    val title: String,
    val amount: String,
    val amountColorRed: Boolean,
    val date: UiText,
    val categoryName: String,
    val categoryIcon: CategoryIcon,
    val isExpense: Boolean,
    val isDeletable: Boolean = true,
    val type: TransactionType = TransactionType.EXPENSE,
)

fun getMonthNameRes(month: kotlinx.datetime.Month): StringResource {
    return when (month) {
        kotlinx.datetime.Month.JANUARY -> Res.string.month_jan
        kotlinx.datetime.Month.FEBRUARY -> Res.string.month_feb
        kotlinx.datetime.Month.MARCH -> Res.string.month_mar
        kotlinx.datetime.Month.APRIL -> Res.string.month_apr
        kotlinx.datetime.Month.MAY -> Res.string.month_may
        kotlinx.datetime.Month.JUNE -> Res.string.month_jun
        kotlinx.datetime.Month.JULY -> Res.string.month_jul
        kotlinx.datetime.Month.AUGUST -> Res.string.month_aug
        kotlinx.datetime.Month.SEPTEMBER -> Res.string.month_sep
        kotlinx.datetime.Month.OCTOBER -> Res.string.month_oct
        kotlinx.datetime.Month.NOVEMBER -> Res.string.month_nov
        kotlinx.datetime.Month.DECEMBER -> Res.string.month_dec
    }
}

fun formatXAxisLabel(dateTime: LocalDateTime, granularity: Granularity): UiText {
    return when (granularity) {
        Granularity.DAY -> UiText.StringRes(Res.string.number_format, dateTime.day.toString())
        Granularity.WEEK -> UiText.StringRes(Res.string.week_label_format, ((dateTime.date.dayOfYear - 1) / 7 + 1).toString())
        Granularity.MONTH -> UiText.StringRes(getMonthNameRes(dateTime.month))
        Granularity.YEAR -> UiText.StringRes(Res.string.number_format, dateTime.year.toString())
    }
}

fun getMonthDayFormatRes(month: kotlinx.datetime.Month): StringResource {
    return when (month) {
        kotlinx.datetime.Month.JANUARY -> Res.string.month_day_jan
        kotlinx.datetime.Month.FEBRUARY -> Res.string.month_day_feb
        kotlinx.datetime.Month.MARCH -> Res.string.month_day_mar
        kotlinx.datetime.Month.APRIL -> Res.string.month_day_apr
        kotlinx.datetime.Month.MAY -> Res.string.month_day_may
        kotlinx.datetime.Month.JUNE -> Res.string.month_day_jun
        kotlinx.datetime.Month.JULY -> Res.string.month_day_jul
        kotlinx.datetime.Month.AUGUST -> Res.string.month_day_aug
        kotlinx.datetime.Month.SEPTEMBER -> Res.string.month_day_sep
        kotlinx.datetime.Month.OCTOBER -> Res.string.month_day_oct
        kotlinx.datetime.Month.NOVEMBER -> Res.string.month_day_nov
        kotlinx.datetime.Month.DECEMBER -> Res.string.month_day_dec
    }
}

fun formatMonthDay(dateTime: LocalDateTime): UiText {
    val day = dateTime.day
    val formatRes = getMonthDayFormatRes(dateTime.month)
    return UiText.StringRes(formatRes, day.toString())
}

fun Granularity.toName(): StringResource {
    return when (this) {
        Granularity.DAY -> Res.string.daily
        Granularity.WEEK -> Res.string.weekly
        Granularity.MONTH -> Res.string.monthly
        Granularity.YEAR -> Res.string.yearly
    }
}

fun Granularity.toExpensesTitleRes(): StringResource {
    return when (this) {
        Granularity.DAY -> Res.string.expenses_last_day
        Granularity.WEEK -> Res.string.expenses_last_week
        Granularity.MONTH -> Res.string.expenses_last_month
        Granularity.YEAR -> Res.string.expenses_last_year
    }
}

fun getXAxisLabels(
    buckets: List<StatsBucket>,
    selectedGranularity: Granularity
): List<UiText> = buckets.map { formatXAxisLabel(it.startDate, selectedGranularity) }

fun getBarXLabels(
    barBuckets: List<BudgetStatusBucket>,
    selectedGranularity: Granularity
): List<UiText> = barBuckets.map { formatXAxisLabel(it.startDate, selectedGranularity) }

fun getDashedRanges(buckets: List<StatsBucket>): List<IntRange> {
    val dashedRanges = mutableListOf<IntRange>()
    var startPredicted: Int? = null
    buckets.forEachIndexed { idx, bucket ->
        if (bucket.isPredicted) {
            if (startPredicted == null) {
                startPredicted = idx
            }
        } else {
            if (startPredicted != null) {
                dashedRanges.add(startPredicted..idx)
                startPredicted = null
            }
        }
    }
    if (startPredicted != null) {
        dashedRanges.add(startPredicted..buckets.lastIndex)
    }
    return dashedRanges
}

fun Transaction.toUiState(): StatisticsTransactionUiState {
    val categoryName = category?.categoryName ?: ""
    val categoryIcon = category?.categoryIcon ?: CategoryIcon.DEFAULT
    val isExp = type == TransactionType.EXPENSE
    val amtInt = amount.toInt().absoluteValue
    val amtStr = if (amount % 1.0 == 0.0) amtInt.toString() else amount.toString()
    
    return StatisticsTransactionUiState(
        id = id,
        title = title,
        amount = amtStr,
        amountColorRed = isExp,
        date = formatTransactionDate(date),
        categoryName = categoryName,
        categoryIcon = categoryIcon,
        isExpense = isExp,
        isDeletable = type != TransactionType.BUDGET,
        type = type
    )
}

fun ScheduledPayment.toUiState(now: LocalDateTime): StatisticsScheduledPaymentUiState {
    val amtInt = amount.toInt()
    val amtStr = if (amount % 1.0 == 0.0) amtInt.toString() else amount.toString()
    
    val dueInstant = nextDueDate.toInstant(TimeZone.currentSystemDefault())
    val nowInstant = now.toInstant(TimeZone.currentSystemDefault())
    val daysDiff = (dueInstant - nowInstant).inWholeDays
    
    return StatisticsScheduledPaymentUiState(
        id = id,
        name = title,
        categoryIcon = categoryIcon,
        amount = amtStr,
        date = formatMonthDay(nextDueDate),
        dueDateText = nextDueDate.toTimeLeftText(now),
        isDueSoon = daysDiff <= 1
    )
}
