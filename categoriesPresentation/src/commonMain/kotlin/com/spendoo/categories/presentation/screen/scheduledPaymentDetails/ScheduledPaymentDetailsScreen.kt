package com.spendoo.categories.presentation.screen.scheduledPaymentDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spendoo.categories.domain.entity.scheduledPayment.PaymentFrequency
import com.spendoo.categories.presentation.screen.addCategoryBottomSheet.toDrawableResource
import com.spendoo.categories.presentation.screen.addScheduledPaymentBottomSheet.AddScheduledPaymentBottomSheet
import com.spendoo.categories.presentation.screen.addScheduledPaymentBottomSheet.toText
import com.spendoo.designsystem.components.appBar.SpendooIconButton
import com.spendoo.designsystem.components.appBar.TopBar
import com.spendoo.designsystem.components.button.AppButton
import com.spendoo.designsystem.components.button.AppButtonType
import com.spendoo.designsystem.components.cards.DetailItem
import com.spendoo.designsystem.components.icon.CategoryIcon
import com.spendoo.designsystem.components.indicator.CircularProgressIndicator
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.UiText
import com.spendoo.designsystem.utils.asString
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.delete
import spendoo.designsystem.generated.resources.due_date
import spendoo.designsystem.generated.resources.edit
import spendoo.designsystem.generated.resources.frequency
import spendoo.designsystem.generated.resources.ic_clock_red
import spendoo.designsystem.generated.resources.ic_date
import spendoo.designsystem.generated.resources.ic_delete
import spendoo.designsystem.generated.resources.ic_edit
import spendoo.designsystem.generated.resources.ic_money
import spendoo.designsystem.generated.resources.ic_repeat
import spendoo.designsystem.generated.resources.money_amount
import spendoo.designsystem.generated.resources.pay
import spendoo.designsystem.generated.resources.skip
import spendoo.designsystem.generated.resources.time_left
import spendoo.designsystem.generated.resources.x_days

@Composable
fun ScheduledPaymentDetailsScreen(
    paymentId: String,
    viewModel: ScheduledPaymentDetailsViewModel = koinViewModel(parameters = { parametersOf(paymentId) })
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ScheduledPaymentDetailsContent(
        state = state,
        interactionListener = viewModel
    )

    AddScheduledPaymentBottomSheet(
        isVisible = state.isEditBottomSheetVisible,
        initialState = state.toAddScheduledPaymentUiState(),
        onDismiss = viewModel::onEditBottomSheetDismissed,
        onSuccess = {
            viewModel.onEditBottomSheetDismissed()
            viewModel.onReloadDetails()
        }
    )
}

@Composable
private fun ScheduledPaymentDetailsContent(
    state: ScheduledPaymentDetailsUiState,
    interactionListener: ScheduledPaymentDetailsInteractionListener
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colorScheme.background.primary)
            .statusBarsPadding()
    ) {
        TopBar(
            modifier = Modifier.fillMaxWidth(),
            title = "",
            onBackClicked = interactionListener::onBackClicked,
            actions = listOf(
                {
                    SpendooIconButton(
                        iconRes = Res.drawable.ic_edit,
                        contentDescription = stringResource(Res.string.edit),
                        backgroundColor = Theme.colorScheme.brand.secondary,
                        iconSize = 20.dp,
                        tint = Theme.colorScheme.icon.primary,
                        onClick = interactionListener::onEditClicked
                    )
                },
                {
                    SpendooIconButton(
                        iconRes = Res.drawable.ic_delete,
                        contentDescription = stringResource(Res.string.delete),
                        backgroundColor = Theme.colorScheme.brand.secondary,
                        iconSize = 20.dp,
                        tint = Theme.colorScheme.icon.primary,
                        onClick = interactionListener::onDeleteClicked
                    )
                }
            )
        )

        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Theme.colorScheme.background.secondary, RoundedCornerShape(16.dp))
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        CategoryIcon(
                            icon = state.categoryIcon.toDrawableResource(),
                            size = 48.dp
                        )
                        Text(
                            text = state.title,
                            style = Theme.typography.title.small,
                            color = Theme.colorScheme.text.title,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    val frequencyText = if (state.frequency == PaymentFrequency.CUSTOM) {
                        stringResource(Res.string.x_days, state.customFrequencyDays?.toString().orEmpty())
                    } else {
                        stringResource(state.frequency.toText())
                    }

                    ScheduledPaymentDetailGrid(
                        amount = state.amount,
                        timeLeft = state.timeLeft.asString(),
                        frequencyText = frequencyText,
                        dueDate = state.dueDate,
                        isDueSoon = state.isDueSoon,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    AppButton(
                        modifier = Modifier.weight(1f),
                        text = stringResource(Res.string.skip),
                        type = AppButtonType.Secondary,
                        onClick = interactionListener::onSkipClicked
                    )
                    AppButton(
                        modifier = Modifier.weight(1f),
                        text = stringResource(Res.string.pay),
                        type = AppButtonType.Primary,
                        onClick = interactionListener::onPayClicked
                    )
                }
            }
        }
    }
}

@Composable
private fun ScheduledPaymentDetailGrid(
    amount: String,
    timeLeft: String,
    frequencyText: String,
    dueDate: String,
    isDueSoon: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DetailItem(
                icon = Res.drawable.ic_money,
                value = amount,
                label = stringResource(Res.string.money_amount),
                modifier = Modifier.weight(1f)
            )
            DetailItem(
                icon = Res.drawable.ic_clock_red,
                value = timeLeft,
                label = stringResource(Res.string.time_left),
                valueColor = if (isDueSoon) Theme.colorScheme.additional.onError else Theme.colorScheme.text.title,
                modifier = Modifier.weight(1f)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DetailItem(
                icon = Res.drawable.ic_repeat,
                value = frequencyText,
                label = stringResource(Res.string.frequency),
                modifier = Modifier.weight(1f)
            )
            DetailItem(
                icon = Res.drawable.ic_date,
                value = dueDate,
                label = stringResource(Res.string.due_date),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
@PreviewLightDark
fun ScheduledPaymentDetailsScreenPreview() = SpendooTheme {
    ScheduledPaymentDetailsContent(
        state = ScheduledPaymentDetailsUiState(
            isLoading = false,
            title = "Daily Coffee",
            amount = "5.00",
            timeLeft = UiText.DynamicString("2 days left"),
            dueDate = "2024-10-10",
            isDueSoon = true
        ),
        interactionListener = object : ScheduledPaymentDetailsInteractionListener {
            override fun onBackClicked() {}
            override fun onEditClicked() {}
            override fun onDeleteClicked() {}
            override fun onSkipClicked() {}
            override fun onPayClicked() {}
            override fun onEditBottomSheetDismissed() {}
            override fun onReloadDetails() {}
        }
    )
}
