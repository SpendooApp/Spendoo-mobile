package com.spendoo.statistics.presentation.screen.export

import com.spendoo.statistics.domain.entity.TimePeriod
import com.spendoo.statistics.domain.entity.ReportType
import com.spendoo.statistics.domain.entity.DataToInclude
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.budget_analysis
import spendoo.designsystem.generated.resources.detailed_report
import spendoo.designsystem.generated.resources.full_report
import spendoo.designsystem.generated.resources.ic_shopping
import spendoo.designsystem.generated.resources.ic_stats
import spendoo.designsystem.generated.resources.ic_wallet
import spendoo.designsystem.generated.resources.only_expenses
import spendoo.designsystem.generated.resources.summary_charts

enum class DatePickerField {
    START_DATE,
    END_DATE
}

data class ExportUiState(
    val timePeriod: TimePeriod = TimePeriod.LAST_MONTH,
    val startDate: LocalDate? = null,
    val endDate: LocalDate? = null,
    val reportType: ReportType = ReportType.SUMMARY_CHARTS,
    val dataToInclude: DataToInclude = DataToInclude.FULL_REPORT,
    val showDatePickerFor: DatePickerField? = null
)

fun DataToInclude.toStringResource(): StringResource {
    return when (this) {
        DataToInclude.FULL_REPORT -> Res.string.full_report
        DataToInclude.ONLY_EXPENSES -> Res.string.only_expenses
        DataToInclude.BUDGET_ANALYSIS -> Res.string.budget_analysis
    }
}

fun DataToInclude.toIconResource(): DrawableResource {
    return when (this) {
        DataToInclude.FULL_REPORT -> Res.drawable.ic_stats
        DataToInclude.ONLY_EXPENSES -> Res.drawable.ic_shopping
        DataToInclude.BUDGET_ANALYSIS -> Res.drawable.ic_wallet
    }
}

fun ReportType.toStringResource(): StringResource {
    return when (this) {
        ReportType.SUMMARY_CHARTS -> Res.string.summary_charts
        ReportType.DETAILED_REPORT -> Res.string.detailed_report
    }
}

fun ReportType.toIconResource(): DrawableResource {
    return when (this) {
        ReportType.SUMMARY_CHARTS -> Res.drawable.ic_stats
        ReportType.DETAILED_REPORT -> Res.drawable.ic_shopping
    }
}