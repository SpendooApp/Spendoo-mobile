package com.spendoo.statistics.presentation.screen.export

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spendoo.designsystem.components.appBar.TopBar
import com.spendoo.designsystem.components.button.AppButton
import com.spendoo.designsystem.components.button.AppButtonType
import com.spendoo.designsystem.components.button.IconPosition
import com.spendoo.designsystem.components.column.SelectOptionSection
import com.spendoo.designsystem.components.dialog.DatePicker
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.format
import com.spendoo.statistics.domain.entity.DataToInclude
import com.spendoo.statistics.domain.entity.ReportType
import com.spendoo.statistics.domain.entity.TimePeriod
import com.spendoo.statistics.presentation.screen.export.components.RangeSection
import com.spendoo.statistics.presentation.screen.export.components.TimePeriodSection
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.data_to_include
import spendoo.designsystem.generated.resources.export_report
import spendoo.designsystem.generated.resources.generate
import spendoo.designsystem.generated.resources.ic_arrow_right
import spendoo.designsystem.generated.resources.report_type

@Composable
fun ExportScreen(
    viewModel: ExportViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ExportContent(
        state = state,
        listener = viewModel
    )

    DatePicker(
        showDialog = state.showDatePickerFor != null,
        selectedDate = if (state.showDatePickerFor == DatePickerField.START_DATE) state.startDate else state.endDate,
        onDateSelected = { date ->
            if (state.showDatePickerFor == DatePickerField.START_DATE) {
                viewModel.onStartDateSelected(date)
            } else {
                viewModel.onEndDateSelected(date)
            }
        },
        onDismiss = viewModel::onDismissDatePicker
    )
}

@Composable
private fun ExportContent(
    state: ExportUiState,
    listener: ExportInteractionListener
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colorScheme.background.primary)
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 100.dp)
        ) {
            TopBar(
                title = stringResource(Res.string.export_report),
                onBackClicked = listener::onBackClicked
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Spacer(modifier = Modifier.height(4.dp))

                TimePeriodSection(
                    selectedTimePeriod = state.timePeriod,
                    onTimePeriodSelected = listener::onTimePeriodSelected
                )

                AnimatedVisibility(visible = state.timePeriod == TimePeriod.CUSTOM_RANGE) {
                    RangeSection(
                        startDateText = state.startDate?.format(),
                        endDateText = state.endDate?.format(),
                        onStartDateClicked = listener::onStartDateClicked,
                        onEndDateClicked = listener::onEndDateClicked
                    )
                }

                SelectOptionSection(
                    title = Res.string.report_type,
                    selected = state.reportType,
                    onSelected = listener::onReportTypeSelected,
                    entries = ReportType.entries,
                    getName = { it.toStringResource() },
                    getIcon = { it.toIconResource() },
                )

                AnimatedVisibility(visible = state.reportType == ReportType.DETAILED_REPORT) {
                    SelectOptionSection(
                        title = Res.string.data_to_include,
                        selected = state.dataToInclude,
                        onSelected = listener::onDataToIncludeSelected,
                        getName = { it.toStringResource() },
                        getIcon = { it.toIconResource() },
                        entries = DataToInclude.entries,
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .shadow(
                    elevation = 16.dp,
                    shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                )
                .background(
                    Theme.colorScheme.background.tertiary,
                    RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                )
                .border(
                    1.dp,
                    Theme.colorScheme.border.secondary,
                    RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                )
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ) {
            AppButton(
                text = stringResource(Res.string.generate),
                onClick = listener::onGenerateClicked,
                modifier = Modifier.fillMaxWidth(),
                type = AppButtonType.Primary,
                icon = vectorResource(Res.drawable.ic_arrow_right),
                iconPosition = IconPosition.End
            )
        }
    }
}

@Composable
@Preview
private fun ExportContentPreview() = SpendooTheme {
    ExportContent(
        state = ExportUiState(),
        listener = object : ExportInteractionListener {
            override fun onBackClicked() {}
            override fun onTimePeriodSelected(timePeriod: TimePeriod) {}
            override fun onStartDateClicked() {}
            override fun onEndDateClicked() {}
            override fun onStartDateSelected(date: LocalDate) {}
            override fun onEndDateSelected(date: LocalDate) {}
            override fun onDismissDatePicker() {}
            override fun onReportTypeSelected(reportType: ReportType) {}
            override fun onDataToIncludeSelected(dataToInclude: DataToInclude) {}
            override fun onGenerateClicked() {}
        }
    )
}