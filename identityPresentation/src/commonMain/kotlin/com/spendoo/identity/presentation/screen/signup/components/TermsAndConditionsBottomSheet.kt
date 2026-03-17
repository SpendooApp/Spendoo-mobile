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
fun TermsAndConditionsBottomSheet(isVisible: Boolean, onDismissRequest: () -> Unit) {
    BottomSheet(
        isVisible = isVisible,
        onDismiss = onDismissRequest,
        content = {
            val scrollState = rememberScrollState()
            Text(
                text = "Terms and Conditions for Spendoo\n" +
                        "Effective Date: February 2026\n" +
                        "\n" +
                        "1. Acceptance of Services\n" +
                        "By creating an account on Spendoo, you agree to these terms. Spendoo provides financial management tools, including subscription tracking, budget monitoring, and social connectivity features.\n" +
                        "\n" +
                        "2. Account Security & User IDs\n" +
                        "Users are responsible for maintaining the confidentiality of their account.\n" +
                        "The \"Regenerate ID\" feature is provided to enhance privacy; users are encouraged to reset their ID if they feel their current connection code has been compromised.\n" +
                        "Following another user requires mutual consent via the Follow Request and Approve process.\n" +
                        "\n" +
                        "3. Adaptive Budgeting Disclaimer\n" +
                        "The Adaptive Budgeting feature, which suggests moving funds from \"Donor Categories\" to \"Overspent Categories,\" is a tool for convenience.\n" +
                        "Spendoo is not responsible for any financial decisions made by the user or for the accuracy of bank-sync delays.\n" +
                        "\n" +
                        "4. No Financial Advice\n" +
                        "The insights provided, such as Brand Alternatives (e.g., switching mobile data plans), are for informational purposes only and do not constitute professional financial advice.",
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
