package com.spendoo.statistics.presentation.screen.download

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spendoo.designsystem.components.appBar.TopBar
import com.spendoo.designsystem.components.button.AppButton
import com.spendoo.designsystem.components.button.AppButtonType
import com.spendoo.designsystem.components.divider.HorizontalDivider
import com.spendoo.designsystem.components.indicator.CircularProgressIndicator
import com.spendoo.designsystem.components.pdf.PdfLayoutCompiler
import com.spendoo.designsystem.components.pdf.PdfPageInput
import com.spendoo.designsystem.components.pdf.PdfViewer
import com.spendoo.designsystem.components.pdf.rememberPlatformContext
import com.spendoo.designsystem.components.placeholder.ErrorState
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.statistics.domain.entity.Granularity
import com.spendoo.statistics.domain.entity.ReportType
import com.spendoo.statistics.presentation.screen.download.components.VerticalTopCategoryRowItem
import com.spendoo.statistics.presentation.screen.statistics.components.BarChartSection
import com.spendoo.statistics.presentation.screen.statistics.components.DonutChartSection
import com.spendoo.statistics.presentation.screen.statistics.components.LineChartSection
import io.github.vinceglb.filekit.dialogs.FileKitDialogSettings
import io.github.vinceglb.filekit.dialogs.compose.SaverResultLauncher
import io.github.vinceglb.filekit.dialogs.compose.rememberFileSaverLauncher
import io.github.vinceglb.filekit.write
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.budget_status
import spendoo.designsystem.generated.resources.cashflow_history
import spendoo.designsystem.generated.resources.download_report
import spendoo.designsystem.generated.resources.retry
import spendoo.designsystem.generated.resources.statistics_summary_report
import spendoo.designsystem.generated.resources.top_categories

@Composable
fun DownloadScreen(
    viewModel: DownloadViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val isDarkTheme = Theme.isDarkTheme

    LaunchedEffect(viewModel, isDarkTheme) {
        viewModel.initialize(isDarkTheme)
    }

    DownloadContent(
        state = state,
        listener = viewModel,
        viewModel = viewModel
    )
}

@Composable
private fun DownloadContent(
    state: DownloadUiState,
    listener: DownloadInteractionListener,
    viewModel: DownloadViewModel
) {
    val context = rememberPlatformContext()
    val scope = rememberCoroutineScope()
    val compiler = remember(context) { PdfLayoutCompiler(context) }
    val parentCompositionContext = rememberCompositionContext()
    val bytesToWriteState = remember { mutableStateOf<ByteArray?>(null) }

    val fileSaverLauncher = rememberFileSaverLauncher(
        dialogSettings = FileKitDialogSettings.createDefault()
    ) { file ->
        file?.let {
            bytesToWriteState.value?.let { bytes ->
                scope.launch {
                    it.write(bytes)
                }
            }
        }
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colorScheme.background.primary)
            .statusBarsPadding()
    ) {
        val screenWidth = maxWidth.value.toInt()

        var compiledChartsPdf by remember { mutableStateOf<ByteArray?>(null) }
        var isCompiling by remember { mutableStateOf(false) }

        LaunchedEffect(state.lineChartUiState, state.barChartUiState, state.pieChartUiState) {
            if (state.exportChoices?.reportType == ReportType.SUMMARY_CHARTS &&
                (state.lineChartUiState != null || state.barChartUiState != null || state.pieChartUiState.isNotEmpty())
            ) {
                isCompiling = true
                try {
                    val pages = getChartsPdfPages(state = state, screenWidth = screenWidth)
                    compiledChartsPdf = compiler.compileMultiplePagesToPdf(
                        pages = pages,
                        scale = state.scaleFactor,
                        parentContext = parentCompositionContext
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    isCompiling = false
                }
            }
        }

        val activePdfBytes = if (state.exportChoices?.reportType == ReportType.DETAILED_REPORT) {
            state.pdfBytes
        } else {
            compiledChartsPdf
        }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopBar(
                title = stringResource(Res.string.download_report),
                onBackClicked = listener::onBackClicked
            )

            if (state.isLoading || isCompiling) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (state.errorMessage != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    ErrorState(
                        text = state.errorMessage,
                        onActionText = Res.string.retry,
                        onRetry = listener::onRetryClicked,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    if (activePdfBytes != null) {
                        PdfViewer(
                            pdf = activePdfBytes,
                            aspectRatio = null,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                DownloadActionSection(
                    viewModel = viewModel,
                    pdfBytes = activePdfBytes,
                    fileSaverLauncher = fileSaverLauncher,
                    bytesToWriteState = bytesToWriteState,
                )
            }
        }
    }
}

@Composable
private fun DownloadActionSection(
    viewModel: DownloadViewModel,
    pdfBytes: ByteArray?,
    fileSaverLauncher: SaverResultLauncher,
    bytesToWriteState: MutableState<ByteArray?>,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .navigationBarsPadding()
    ) {
        val filename = viewModel.getPdfFilename()

        AppButton(
            type = AppButtonType.Primary,
            onClick = {
                pdfBytes?.let { bytes ->
                    bytesToWriteState.value = bytes
                    fileSaverLauncher.launch(
                        suggestedName = filename.removeSuffix(".pdf"),
                        defaultExtension = "pdf"
                    )
                }
            },
            text = stringResource(Res.string.download_report),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

private fun getChartsPdfPages(state: DownloadUiState, screenWidth: Int): List<PdfPageInput> {
    val pointsCount = state.lineChartUiState?.xAxisLabels?.size ?: 0
    val page1Width = maxOf(screenWidth, pointsCount * 30 + 80)
    val page1Height = 260 + 80 + 32
    val page1 = PdfPageInput(
        widthDp = page1Width,
        heightDp = page1Height,
        content = { Page1Content(state = state) }
    )

    val barCount = state.barChartUiState?.xAxisLabels?.size ?: 0
    val page2Width = maxOf(screenWidth, barCount * 47 + 80)
    val page2Height = 200 + 40 + 32 + 40
    val page2 = PdfPageInput(
        widthDp = page2Width,
        heightDp = page2Height,
        content = { Page2Content(state = state) }
    )

    val categoriesCount = state.combinedStats?.topCategories?.topCategories?.size ?: 0
    val page3Height = 260 + (categoriesCount * 50) + 32
    val page3 = PdfPageInput(
        widthDp = screenWidth,
        heightDp = page3Height,
        content = { Page3Content(state = state) }
    )

    return listOf(page1, page2, page3)
}

@Composable
private fun Page1Content(state: DownloadUiState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Theme.colorScheme.background.primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column {
            Text(
                text = stringResource(Res.string.statistics_summary_report),
                style = Theme.typography.heading.medium,
                color = Theme.colorScheme.text.title
            )
        }

        HorizontalDivider(modifier = Modifier.fillMaxWidth())

        state.lineChartUiState?.let { lineState ->
            Text(
                text = stringResource(Res.string.cashflow_history),
                style = Theme.typography.heading.tiny,
                color = Theme.colorScheme.text.titleSmall
            )
            LineChartSection(
                lineChartUiState = lineState,
                animateChart = false,
                pointsGap = 30.dp
            )
        }
    }
}

@Composable
private fun Page2Content(state: DownloadUiState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Theme.colorScheme.background.primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        state.barChartUiState?.let { barState ->
            Text(
                text = stringResource(Res.string.budget_status),
                style = Theme.typography.heading.tiny,
                color = Theme.colorScheme.text.titleSmall
            )
            BarChartSection(
                barChartUiState = barState,
                animateChart = false
            )
        }
    }
}

@Composable
private fun Page3Content(state: DownloadUiState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Theme.colorScheme.background.primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (state.pieChartUiState.isNotEmpty() && state.combinedStats != null) {
            DonutChartSection(
                pieChartUiStates = state.pieChartUiState,
                animateChart = false,
                topCategories = state.combinedStats.topCategories,
                selectedGranularity = Granularity.MONTH
            )
            HorizontalDivider(modifier = Modifier.fillMaxWidth())
        }

        state.combinedStats?.topCategories?.topCategories?.let { categories ->
            Text(
                text = stringResource(Res.string.top_categories),
                style = Theme.typography.heading.tiny,
                color = Theme.colorScheme.text.titleSmall
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                categories.forEach { category ->
                    VerticalTopCategoryRowItem(category = category)
                }
            }
        }
    }
}
