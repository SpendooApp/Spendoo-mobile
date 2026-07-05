package com.spendoo.identity.presentation.screen.addFollower

import com.spendoo.designsystem.navigation.BaseViewModel
import com.spendoo.designsystem.utils.UiText
import com.spendoo.identity.domain.repository.FollowRepository
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.an_error_occurred
import spendoo.designsystem.generated.resources.unknown_error

class AddFollowerViewModel(
    private val followRepository: FollowRepository
) : BaseViewModel<AddFollowerUiState>(AddFollowerUiState()), AddFollowerInteractionListener {

    override fun onCodeChanged(code: String) {
        updateState { copy(searchCode = code, searchedUser = null, isFollowSent = false) }
        if (code.length >= 10) {
            onSearchUser()
        }
    }

    override fun onSearchUser() {
        if (state.value.searchCode.isBlank()) return
        tryToCall(
            block = { followRepository.searchUserByCode(state.value.searchCode) },
            onStart = { updateState { copy(isLoading = true) } },
            onSuccess = { user ->
                updateState { copy(searchedUser = user) }
            },
            onError = { handleError(it) },
            onEnd = { updateState { copy(isLoading = false) } }
        )
    }

    override fun onClickFollowUser(userId: String) {
        tryToCall(
            block = { followRepository.sendFollowRequest(userId) },
            onSuccess = {
                updateState { copy(isFollowSent = true) }
            },
            onError = { handleError(it) }
        )
    }

    private fun handleError(throwable: Throwable) {
        showSnackBar(
            title = UiText.StringRes(Res.string.an_error_occurred),
            message = throwable.message?.let(UiText::DynamicString)
                ?: UiText.StringRes(Res.string.unknown_error),
            isSuccess = false
        )
    }
}
