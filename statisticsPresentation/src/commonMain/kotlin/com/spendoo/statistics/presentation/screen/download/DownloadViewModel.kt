package com.spendoo.statistics.presentation.screen.download

import com.spendoo.designsystem.utils.UiText
import spendoo.designsystem.generated.resources.Res

import com.spendoo.designsystem.navigation.BaseViewModel
import com.spendoo.identity.domain.repository.SettingsRepository
import com.spendoo.identity.domain.util.AppTheme
import com.spendoo.statistics.domain.entity.DataToInclude
import com.spendoo.statistics.domain.entity.ExportChoices
import com.spendoo.statistics.domain.entity.Granularity
import com.spendoo.statistics.domain.entity.ReportDataType
import com.spendoo.statistics.domain.entity.ReportType
import com.spendoo.statistics.domain.entity.TimePeriod
import com.spendoo.statistics.domain.repository.StatisticsRepository
import com.spendoo.statistics.presentation.screen.statistics.BarChartBucketUiState
import com.spendoo.statistics.presentation.screen.statistics.BarChartUiState
import com.spendoo.statistics.presentation.screen.statistics.LineChartUiState
import com.spendoo.statistics.presentation.screen.statistics.PieChartUiState
import com.spendoo.statistics.presentation.screen.statistics.getBarXLabels
import com.spendoo.statistics.presentation.screen.statistics.getDashedRanges
import com.spendoo.statistics.presentation.screen.statistics.getXAxisLabels
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.daysUntil
import kotlinx.datetime.minus
import kotlinx.datetime.number
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import spendoo.designsystem.generated.resources.an_error_occurred
import kotlin.time.Clock

class DownloadViewModel(
    private val statisticsRepository: StatisticsRepository,
    private val settingsRepository: SettingsRepository,
) : BaseViewModel<DownloadUiState>(DownloadUiState()), DownloadInteractionListener {

    private var isSystemDarkTheme: Boolean = false

    fun initialize(isSystemDarkTheme: Boolean) {
        this.isSystemDarkTheme = isSystemDarkTheme
        val choices = statisticsRepository.getExportChoices()
        updateState { copy(exportChoices = choices) }

        if (choices != null) {
            if (choices.reportType == ReportType.DETAILED_REPORT) {
                fetchDetailedPdf(choices, isSystemDarkTheme)
            } else if (choices.reportType == ReportType.SUMMARY_CHARTS) {
                fetchChartsData(choices)
            }
        }
    }

    override fun onRetryClicked() {
        val choices = state.value.exportChoices
        if (choices != null) {
            if (choices.reportType == ReportType.DETAILED_REPORT) {
                fetchDetailedPdf(choices, isSystemDarkTheme)
            } else if (choices.reportType == ReportType.SUMMARY_CHARTS) {
                fetchChartsData(choices)
            }
        }
    }

    override fun onBackClicked() {
        popBackStack()
    }
    
    private fun fetchDetailedPdf(choices: ExportChoices, isSystemDarkTheme: Boolean) {
        updateState { copy(isLoading = true, errorMessage = null) }

        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        val (start, end) = when (choices.timePeriod) {
            TimePeriod.LAST_MONTH -> {
                val lastMonthYear = if (today.month.number == 1) today.year - 1 else today.year
                val lastMonthNumber = if (today.month.number == 1) 12 else today.month.number - 1
                val startDate = LocalDate(lastMonthYear, lastMonthNumber, 1)
                val endDate = startDate.plus(1, DateTimeUnit.MONTH).minus(1, DateTimeUnit.DAY)
                Pair(startDate, endDate)
            }

            TimePeriod.CUSTOM_RANGE -> {
                Pair(choices.startDate ?: today, choices.endDate ?: today)
            }
        }

        val startDateTime = start.atTime(0, 0, 0)
        val endDateTime = end.atTime(23, 59, 59)

        val reportDataType = when (choices.dataToInclude) {
            DataToInclude.FULL_REPORT -> ReportDataType.FULL
            DataToInclude.ONLY_EXPENSES -> ReportDataType.EXPENSES
            DataToInclude.BUDGET_ANALYSIS -> ReportDataType.INCOME
            else -> ReportDataType.FULL
        }

        val theme = settingsRepository.getCurrentTheme().let {
            if (it == AppTheme.SYSTEM) {
                if (isSystemDarkTheme) AppTheme.DARK else AppTheme.LIGHT
            } else {
                it
            }
        }

        tryToCall(
            block = {
                statisticsRepository.getStatisticsPdf(
                    startDate = startDateTime,
                    endDate = endDateTime,
                    reportDataType = reportDataType,
                    theme = theme
                )
            },
            onSuccess = { pdfBytes ->
                updateState {
                    copy(
                        isLoading = false,
                        pdfBytes = pdfBytes
                    )
                }
            },
            onError = { _ ->
                updateState {
                    copy(
                        isLoading = false,
                        errorMessage = UiText.StringRes(Res.string.an_error_occurred)
                    )
                }
            }
        )
    }

    private fun fetchChartsData(choices: ExportChoices) {
        updateState { copy(isLoading = true, errorMessage = null) }

        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        val (start, end) = when (choices.timePeriod) {
            TimePeriod.LAST_MONTH -> {
                val lastMonthYear = if (today.month.number == 1) today.year - 1 else today.year
                val lastMonthNumber = if (today.month.number == 1) 12 else today.month.number - 1
                val startDate = LocalDate(lastMonthYear, lastMonthNumber, 1)
                val endDate = startDate.plus(1, DateTimeUnit.MONTH).minus(1, DateTimeUnit.DAY)
                Pair(startDate, endDate)
            }

            TimePeriod.CUSTOM_RANGE -> {
                Pair(choices.startDate ?: today, choices.endDate ?: today)
            }
        }

        val startDateTime = start.atTime(0, 0, 0)
        val endDateTime = end.atTime(23, 59, 59)

        val days = start.daysUntil(end) + 1
        val granularity = when {
            days <= 8 -> Granularity.DAY
            days in 9..42 -> Granularity.WEEK
            days in 43..365 -> Granularity.MONTH
            else -> Granularity.YEAR
        }

        tryToCall(
            block = {
                statisticsRepository.getStatistics(granularity, startDateTime, endDateTime)
            },
            onSuccess = { stats ->
                val lineChartUiState = LineChartUiState(
                    budgetData = stats.financialStats.buckets.map { it.budget },
                    spentData = stats.financialStats.buckets.map { it.spending },
                    incomeData = stats.financialStats.buckets.map { it.income },
                    xAxisLabels = getXAxisLabels(stats.financialStats.buckets, granularity),
                    highestSpendingBucketIndex = stats.financialStats.highestSpendingBucketIndex,
                    dashedRanges = getDashedRanges(stats.financialStats.buckets)
                )

                val barChartUiState = BarChartUiState(
                    buckets = stats.budgetStatus.buckets.map { bucket ->
                        BarChartBucketUiState(
                            spending = bucket.spending,
                            status = bucket.status
                        )
                    },
                    xAxisLabels = getBarXLabels(stats.budgetStatus.buckets, granularity)
                )

                val pieChartUiState = stats.topCategories.topCategories.map { category ->
                    PieChartUiState(
                        spending = category.spending,
                        categoryName = category.categoryName
                    )
                }

                updateState {
                    copy(
                        isLoading = false,
                        combinedStats = stats,
                        lineChartUiState = lineChartUiState,
                        barChartUiState = barChartUiState,
                        pieChartUiState = pieChartUiState
                    )
                }
            },
            onError = { _ ->
                updateState {
                    copy(
                        isLoading = false,
                        errorMessage = UiText.StringRes(Res.string.an_error_occurred)
                    )
                }
            }
        )
    }

    fun getPdfFilename(): String {

        val choices = state.value.exportChoices
        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        val startDate = choices?.startDate ?: today
        val endDate = choices?.endDate ?: today

        val reportDataType = when (state.value.exportChoices?.dataToInclude) {
            DataToInclude.FULL_REPORT -> ReportDataType.FULL
            DataToInclude.ONLY_EXPENSES -> ReportDataType.EXPENSES
            DataToInclude.BUDGET_ANALYSIS -> ReportDataType.INCOME
            else -> ReportDataType.FULL
        }

        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val timestamp = formatLocalDateTime(now)
        val startStr = formatLocalDate(startDate)
        val endStr = formatLocalDate(endDate)
        return "statistics_report_${reportDataType.name}_${startStr}_to_${endStr}_$timestamp.pdf"
    }

    private fun formatLocalDate(date: LocalDate): String {
        val day = date.day.toString().padStart(2, '0')
        val month = date.month.number.toString().padStart(2, '0')
        val year = date.year.toString()
        return "$year-$month-$day"
    }

    private fun formatLocalDateTime(dateTime: LocalDateTime): String {
        val day = dateTime.day.toString().padStart(2, '0')
        val month = dateTime.month.number.toString().padStart(2, '0')
        val year = dateTime.year.toString()
        val hour = dateTime.hour.toString().padStart(2, '0')
        val minute = dateTime.minute.toString().padStart(2, '0')
        return "$year-$month-$day" + "_" + "$hour-$minute"
    }
}
