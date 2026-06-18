package com.spendoo.goals.presentation.screen.goals

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.spendoo.designsystem.components.appBar.TopBar
import com.spendoo.designsystem.theme.theme.Theme
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.my_goals


@Composable
fun GoalsScreen(
    viewModel: GoalsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    GoalsContent(
        state = state,
        listener = viewModel
    )
}

@Composable
private fun GoalsContent(
    state: GoalsUiState,
    listener: GoalsInteractionListener
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colorScheme.background.primary)
    ) {
        TopBar(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(Res.string.my_goals),
            onBackClicked = listener::onBackClicked
        )

        // TODO: Implement goals
    }
}
