package com.spendoo.identity.presentation.screen.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.theme.theme.Theme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SignUpScreen(viewModel: SignUpViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    SignUpScreenContent(
        interactionListener = viewModel,
        state = state
    )
}

@Composable
fun SignUpScreenContent(interactionListener: SignUpInteractionListener, state: SignUpUiState) {
    Box(
        Modifier.fillMaxSize().background(Color.Blue),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Column {
            Text("SignUp", Theme.typography.label.medium)
            Text("go to home", Theme.typography.label.medium, modifier = Modifier.clickable {
                interactionListener.onSignUpClicked()
            })
        }
    }
}

