package com.spendoo.statistics.presentation.screen.download

import com.spendoo.statistics.domain.entity.CombinedStats
import com.spendoo.statistics.domain.entity.ExportChoices
import com.spendoo.statistics.presentation.screen.statistics.BarChartUiState
import com.spendoo.statistics.presentation.screen.statistics.LineChartUiState
import com.spendoo.statistics.presentation.screen.statistics.PieChartUiState

import com.spendoo.designsystem.utils.UiText

data class DownloadUiState(
    val exportChoices: ExportChoices? = null,
    val isLoading: Boolean = false,
    val errorMessage: UiText? = null,
    val scaleFactor: Float = 1.0f,
    val combinedStats: CombinedStats? = null,
    val lineChartUiState: LineChartUiState? = null,
    val barChartUiState: BarChartUiState? = null,
    val pieChartUiState: List<PieChartUiState> = emptyList(),
    val pdfBytes: ByteArray? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as DownloadUiState

        if (isLoading != other.isLoading) return false
        if (scaleFactor != other.scaleFactor) return false
        if (exportChoices != other.exportChoices) return false
        if (errorMessage != other.errorMessage) return false
        if (combinedStats != other.combinedStats) return false
        if (lineChartUiState != other.lineChartUiState) return false
        if (barChartUiState != other.barChartUiState) return false
        if (pieChartUiState != other.pieChartUiState) return false
        if (!pdfBytes.contentEquals(other.pdfBytes)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = isLoading.hashCode()
        result = 31 * result + scaleFactor.hashCode()
        result = 31 * result + (exportChoices?.hashCode() ?: 0)
        result = 31 * result + (errorMessage?.hashCode() ?: 0)
        result = 31 * result + (combinedStats?.hashCode() ?: 0)
        result = 31 * result + (lineChartUiState?.hashCode() ?: 0)
        result = 31 * result + (barChartUiState?.hashCode() ?: 0)
        result = 31 * result + pieChartUiState.hashCode()
        result = 31 * result + (pdfBytes?.contentHashCode() ?: 0)
        return result
    }

}
