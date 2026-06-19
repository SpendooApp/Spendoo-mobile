package com.spendoo.statistics.presentation.screen.statistics

import com.spendoo.categories.api.ScheduledPaymentsRoute
import com.spendoo.designsystem.navigation.BaseViewModel

class StatisticsViewModel : BaseViewModel<StatisticsUiState>(StatisticsUiState()), StatisticsInteractionListener {

    init {
        listenToResetSignal()
        onReload()
    }

    private fun listenToResetSignal() {
        tryToCollect(
            block = {
                getResult<Boolean?>("reset", consume = true)
            },
            onEach = { shouldReset ->
                if (shouldReset == true) {
                    onReload()
                }
            },
            onError = {}
        )
    }

    override fun onReload() {

    }

    override fun onOpenScheduledPayments() {
        navigate(ScheduledPaymentsRoute)
    }
}
