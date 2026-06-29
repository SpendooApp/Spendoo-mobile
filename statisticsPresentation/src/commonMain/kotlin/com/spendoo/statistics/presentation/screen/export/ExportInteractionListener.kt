package com.spendoo.statistics.presentation.screen.export

import com.spendoo.statistics.domain.entity.TimePeriod
import com.spendoo.statistics.domain.entity.ReportType
import com.spendoo.statistics.domain.entity.DataToInclude
import kotlinx.datetime.LocalDate

interface ExportInteractionListener {
    fun onBackClicked()
    fun onTimePeriodSelected(timePeriod: TimePeriod)
    fun onStartDateClicked()
    fun onEndDateClicked()
    fun onStartDateSelected(date: LocalDate)
    fun onEndDateSelected(date: LocalDate)
    fun onDismissDatePicker()
    fun onReportTypeSelected(reportType: ReportType)
    fun onDataToIncludeSelected(dataToInclude: DataToInclude)
    fun onGenerateClicked()
}
