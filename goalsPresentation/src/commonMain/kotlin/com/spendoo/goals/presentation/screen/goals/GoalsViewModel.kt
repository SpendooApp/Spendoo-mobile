package com.spendoo.goals.presentation.screen.goals

import com.spendoo.designsystem.navigation.BaseViewModel

class GoalsViewModel : BaseViewModel<GoalsUiState>(GoalsUiState()), GoalsInteractionListener {

    override fun onReload() {

    }

    override fun onBackClicked() {
        popBackStack()
    }
}
