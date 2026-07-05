package com.spendoo.statistics.presentation.di

import com.spendoo.statistics.presentation.screen.download.DownloadViewModel
import com.spendoo.statistics.presentation.screen.export.ExportViewModel
import com.spendoo.statistics.presentation.screen.statistics.StatisticsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val statisticsPresentationModule = module {
    viewModel { params ->
        val (userId: String?, userName: String?, userImageUrl: String?) = params
        StatisticsViewModel(
            targetUserId = userId,
            targetUserName = userName,
            targetUserImageUrl = userImageUrl,
            statisticsRepository = get(),
            scheduledPaymentsRepository = get(),
            transactionsRepository = get(),
            profileRepository = get()
        )
    }
    viewModel { params ->
        val (targetUserId: String?) = params
        ExportViewModel(
            targetUserId = targetUserId,
            statisticsRepository = get()
        )
    }
    viewModelOf(::DownloadViewModel)
}
