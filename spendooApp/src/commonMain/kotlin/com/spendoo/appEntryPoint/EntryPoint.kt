package com.spendoo.appEntryPoint

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spendoo.identity.api.IdentityFeatureApi
import com.spendoo.identity.domain.service.AuthorizationService
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun EntryPoint(
    identityApi: IdentityFeatureApi = koinInject(),
    viewModel: MainEntryViewModel = koinViewModel(),
    authorizationService: AuthorizationService = koinInject()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val accessToken by authorizationService.observeAccessToken().collectAsStateWithLifecycle()

    // if first time open onboarding
    if (state.isFirstTimeOpen) {
        identityApi.OnBoardingFlow(updateBottomNavigationVisibility = viewModel::onBottomNavigationChanged)
        return
    }

    if (accessToken.isBlank()) {
        identityApi.LoginFlow(updateBottomNavigationVisibility = viewModel::onBottomNavigationChanged)
        return
    }

    LoggedInContainer(
        state = state,
        listener = viewModel,
    )
}