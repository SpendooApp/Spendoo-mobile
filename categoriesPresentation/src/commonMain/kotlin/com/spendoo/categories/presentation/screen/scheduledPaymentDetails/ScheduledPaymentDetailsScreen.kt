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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.spendoo.designsystem.components.appBar.SpendooIconButton
import com.spendoo.designsystem.components.appBar.TopBar
import com.spendoo.designsystem.components.button.AppButton
import com.spendoo.designsystem.components.button.AppButtonType
import com.spendoo.designsystem.components.icon.Icon
import com.spendoo.designsystem.components.indicator.CircularProgressIndicator
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.UiText
import com.spendoo.designsystem.utils.asString
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.delete
import spendoo.designsystem.generated.resources.due_date
import spendoo.designsystem.generated.resources.edit
import spendoo.designsystem.generated.resources.frequency
import spendoo.designsystem.generated.resources.ic_delete
import spendoo.designsystem.generated.resources.ic_drink
import spendoo.designsystem.generated.resources.ic_edit
import spendoo.designsystem.generated.resources.money_amount
import spendoo.designsystem.generated.resources.pay
import spendoo.designsystem.generated.resources.scheduled_payment_details
import spendoo.designsystem.generated.resources.skip
import spendoo.designsystem.generated.resources.time_left

@Composable
fun ScheduledPaymentDetailsScreen(
    viewModel: ScheduledPaymentDetailsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ScheduledPaymentDetailsContent(
        state = state,
        interactionListener = viewModel
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
            title = stringResource(Res.string.scheduled_payment_details),
            onBackClicked = interactionListener::onBackClicked,
            actions = listOf(
                {
                    SpendooIconButton(
                        iconRes = Res.drawable.ic_edit, // TODO Placeholder, update later if requested
                        contentDescription = stringResource(Res.string.edit),
                        onClick = interactionListener::onEditClicked
                    )
                },
                {
                    SpendooIconButton(
                        iconRes = Res.drawable.ic_delete, // TODO Placeholder, update later if requested
                        contentDescription = stringResource(Res.string.delete),
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

                // Header Icon
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(Theme.colorScheme.button.secondary, RoundedCornerShape(24.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_drink),
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = Theme.colorScheme.icon.primary
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = state.title,
                    style = Theme.typography.heading.large,
                    color = Theme.colorScheme.text.title
                )

                Spacer(modifier = Modifier.height(32.dp))

                ScheduledPaymentDetailGrid(
                    amount = state.amount,
                    timeLeft = state.timeLeft.asString(),
                    frequency = state.frequency.name,
                    dueDate = state.dueDate,
                    isDueSoon = state.isDueSoon,
                    modifier = Modifier.fillMaxWidth()
                )

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
    frequency: String,
    dueDate: String,
    isDueSoon: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(Theme.colorScheme.background.secondary, RoundedCornerShape(24.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            DetailItem(stringResource(Res.string.money_amount), amount, Modifier.weight(1f))
            DetailItem(
                stringResource(Res.string.time_left),
                timeLeft,
                Modifier.weight(1f),
                if (isDueSoon) Theme.colorScheme.additional.error else Theme.colorScheme.text.title
            )
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            DetailItem(stringResource(Res.string.frequency), frequency, Modifier.weight(1f))
            DetailItem(stringResource(Res.string.due_date), dueDate, Modifier.weight(1f))
        }
    }
}

@Composable
private fun DetailItem(
    title: String,
    value: String,
    modifier: Modifier = Modifier,
    valueColor: Color = Theme.colorScheme.text.title
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = title,
            style = Theme.typography.label.medium.medium,
            color = Theme.colorScheme.text.body
        )
        Text(text = value, style = Theme.typography.heading.small, color = valueColor)
    }
}

@Composable
@PreviewLightDark
fun ScheduledPaymentDetailsScreenPreview() = SpendooTheme {
    ScheduledPaymentDetailsContent(
        state = ScheduledPaymentDetailsUiState(
            isLoading = false,
            title = "Daily Coffee",
            amount = "$5.00",
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
        }
    )
}
