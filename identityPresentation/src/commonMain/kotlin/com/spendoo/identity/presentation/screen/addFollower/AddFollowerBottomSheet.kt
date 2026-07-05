package com.spendoo.identity.presentation.screen.addFollower

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spendoo.designsystem.components.cards.ActionButton
import com.spendoo.designsystem.components.cards.ProfileFollowCard
import com.spendoo.designsystem.components.sheet.BottomSheet
import com.spendoo.designsystem.components.textField.CustomTextField
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.painter
import com.spendoo.identity.domain.model.UserSearch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.find_someone
import spendoo.designsystem.generated.resources.follow
import spendoo.designsystem.generated.resources.ic_find
import spendoo.designsystem.generated.resources.sent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFollowerBottomSheet(
    onDismiss: () -> Unit,
    viewModel: AddFollowerViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BottomSheet(
        isVisible = true,
        onDismiss = onDismiss,
        containerColor = Theme.colorScheme.background.primary
    ) {
        AddFollowerContent(
            state = state,
            listener = viewModel
        )
    }
}

@Composable
fun AddFollowerContent(
    state: AddFollowerUiState,
    listener: AddFollowerInteractionListener
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomTextField(
            value = state.searchCode,
            onValueChange = listener::onCodeChanged,
            hint = stringResource(Res.string.find_someone),
            leadingIcon = Res.drawable.ic_find.painter(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        state.searchedUser?.let { user ->
            ProfileFollowCard(
                imageUrl = user.imageUrl.orEmpty(),
                name = user.fullName,
                actions = listOf {
                    ActionButton(
                        onActionClick = { listener.onClickFollowUser(user.userId) },
                        text = if (state.isFollowSent) stringResource(Res.string.sent) else stringResource(Res.string.follow),
                        textColor = Theme.colorScheme.button.primary,
                        backgroundColor = Theme.colorScheme.button.secondary
                    )
                }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Preview
@Composable
private fun AddFollowerContentPreview() {
    SpendooTheme {
        AddFollowerContent(
            state = AddFollowerUiState(
                searchCode = "123456",
                searchedUser = UserSearch("1", "Habiba Yasser", null)
            ),
            listener = object : AddFollowerInteractionListener {
                override fun onCodeChanged(code: String) {}
                override fun onSearchUser() {}
                override fun onClickFollowUser(userId: String) {}
            }
        )
    }
}
