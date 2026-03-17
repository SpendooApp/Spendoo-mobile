package com.spendoo.identity.presentation.screen.signup.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.button.AppButton
import com.spendoo.designsystem.components.button.AppButtonType
import com.spendoo.designsystem.components.sheet.BottomSheet
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.theme.theme.Theme

@Composable
fun PrivacyPolicyBottomSheet(isVisible: Boolean, onDismissRequest: () -> Unit) {
    BottomSheet(
        isVisible = isVisible,
        onDismiss = onDismissRequest,
        content = {
            val scrollState = rememberScrollState()
            Text(
                text = "Privacy Policy for Spendoo\n" +
                        "\n" +
                        "1. Information We Collect\n" +
                        "We collect information to help you visualize your spending, including:\n" +
                        "Transaction Data: Amounts and categories (e.g., Transport, Food, Shopping).\n" +
                        "Subscription Details: Names of recurring services and payment dates.\n" +
                        "Social Data: Connections made between users and unique User IDs.\n" +
                        "\n" +
                        "2. How We Use Your Data\n" +
                        "Notifications: To trigger real-time alerts when a budget limit is reached.\n" +
                        "Subscription Monitoring: To send proactive alerts before a payment is processed.\n" +
                        "\n" +
                        "3. AI Training & Model Improvement\n" +
                        "To provide you with the most accurate \"Smart Tips\" and brand comparisons, Spendoo uses your anonymized transaction and subscription data to train and improve our AI models.\n" +
                        "This data is aggregated so that your personal identity is not linked to the training sets.\n" +
                        "Training helps the system better identify cost-saving patterns and improve the accuracy of our Smart Brand Alternatives algorithms.\n" +
                        "\n" +
                        "4. Data Sharing\n" +
                        "Your specific financial data is never sold to third parties.\n" +
                        "Social data (your name/profile) is only visible to users you have explicitly approved via a Follow Request.\n" +
                        "\n" +
                        "5. Your Rights\n" +
                        "You may request to delete your account data at any time through the Settings menu.",
                style = Theme.typography.body.small,
                modifier = Modifier.weight(1f).padding(bottom = 16.dp).verticalScroll(scrollState)
            )
            AppButton(
                text = "OK",
                type = AppButtonType.Primary,
                onClick = onDismissRequest,
                modifier = Modifier.fillMaxWidth()
            )
        }
    )
}
