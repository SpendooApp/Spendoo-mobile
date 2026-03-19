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
import com.spendoo.designsystem.util.extentions.asString
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.ok
import spendoo.designsystem.generated.resources.privacy_policy_for_spendoo

@Composable
fun PrivacyPolicyBottomSheet(isVisible: Boolean, onDismissRequest: () -> Unit) {
    BottomSheet(
        isVisible = isVisible,
        onDismiss = onDismissRequest,
        content = {
            val scrollState = rememberScrollState()
            Text(
                text = Res.string.privacy_policy_for_spendoo.asString(),
                style = Theme.typography.body.small,
                modifier = Modifier.weight(1f).padding(bottom = 16.dp).verticalScroll(scrollState)
            )
            AppButton(
                text = Res.string.ok.asString(),
                type = AppButtonType.Primary,
                onClick = onDismissRequest,
                modifier = Modifier.fillMaxWidth()
            )
        }
    )
}
