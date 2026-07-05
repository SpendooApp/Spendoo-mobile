package com.spendoo.statistics.presentation.screen.export

import com.spendoo.designsystem.navigation.BaseViewModel
import com.spendoo.designsystem.utils.UiText
import com.spendoo.statistics.domain.entity.ExportChoices
import com.spendoo.statistics.domain.entity.TimePeriod
import com.spendoo.statistics.domain.entity.ReportType
import com.spendoo.statistics.domain.entity.DataToInclude
import com.spendoo.statistics.domain.repository.StatisticsRepository
import com.spendoo.statistics.api.DownloadRoute
import kotlinx.datetime.LocalDate
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.an_error_occurred
import spendoo.designsystem.generated.resources.please_select_dates
import spendoo.designsystem.generated.resources.start_date_cannot_be_after_end_date

class ExportViewModel(
    private val targetUserId: String? = null,
    private val statisticsRepository: StatisticsRepository
) : BaseViewModel<ExportUiState>(ExportUiState()), ExportInteractionListener {

    override fun onBackClicked() {
        popBackStack()
    }

    override fun onTimePeriodSelected(timePeriod: TimePeriod) {
        updateState { copy(timePeriod = timePeriod) }
    }

    override fun onStartDateClicked() {
        updateState { copy(showDatePickerFor = DatePickerField.START_DATE) }
    }

    override fun onEndDateClicked() {
        updateState { copy(showDatePickerFor = DatePickerField.END_DATE) }
    }

    override fun onStartDateSelected(date: LocalDate) {
        updateState { copy(startDate = date, showDatePickerFor = null) }
    }

    override fun onEndDateSelected(date: LocalDate) {
        updateState { copy(endDate = date, showDatePickerFor = null) }
    }

    override fun onDismissDatePicker() {
        updateState { copy(showDatePickerFor = null) }
    }

    override fun onReportTypeSelected(reportType: ReportType) {
        updateState { copy(reportType = reportType) }
    }

    override fun onDataToIncludeSelected(dataToInclude: DataToInclude) {
        updateState { copy(dataToInclude = dataToInclude) }
    }

    override fun onGenerateClicked() {
        val currentState = state.value
        if (currentState.timePeriod == TimePeriod.CUSTOM_RANGE) {
            if (currentState.startDate == null || currentState.endDate == null) {
                showSnackBar(
                    title = UiText.StringRes(Res.string.an_error_occurred),
                    message = UiText.StringRes(Res.string.please_select_dates),
                    isSuccess = false
                )
                return
            }
            if (currentState.startDate > currentState.endDate) {
                showSnackBar(
                    title = UiText.StringRes(Res.string.an_error_occurred),
                    message = UiText.StringRes(Res.string.start_date_cannot_be_after_end_date),
                    isSuccess = false
                )
                return
            }
        }

        val choices = ExportChoices(
            timePeriod = currentState.timePeriod,
            startDate = currentState.startDate,
            endDate = currentState.endDate,
            reportType = currentState.reportType,
            dataToInclude = currentState.dataToInclude.takeIf { currentState.reportType == ReportType.DETAILED_REPORT },
            targetUserId = targetUserId
        )

        statisticsRepository.saveExportChoices(choices)
        navigate(DownloadRoute)
    }

    override fun onCleared() {
        super.onCleared()
        statisticsRepository.clearExportChoices()
    }
}
