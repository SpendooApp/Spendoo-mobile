package com.spendoo.identity.presentation.screen.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.identity.domain.util.AppLanguage
import org.jetbrains.compose.resources.stringResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.arabic
import spendoo.designsystem.generated.resources.english
import spendoo.designsystem.generated.resources.select_language

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageSelectionBottomSheet(
    selectedLanguage: AppLanguage,
    onSelectLanguage: (AppLanguage) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Theme.colorScheme.background.primary
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(Res.string.select_language),
                style = Theme.typography.title.large,
                color = Theme.colorScheme.text.title
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        if (selectedLanguage == AppLanguage.ENGLISH) Theme.colorScheme.button.secondary
                        else Theme.colorScheme.background.secondary
                    )
                    .clickable { onSelectLanguage(AppLanguage.ENGLISH) }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(Res.string.english),
                    style = Theme.typography.body.large,
                    color = Theme.colorScheme.text.title
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        if (selectedLanguage == AppLanguage.ARABIC) Theme.colorScheme.button.secondary
                        else Theme.colorScheme.background.secondary
                    )
                    .clickable { onSelectLanguage(AppLanguage.ARABIC) }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(Res.string.arabic),
                    style = Theme.typography.body.large,
                    color = Theme.colorScheme.text.title
                )
            }
        }
    }
}
