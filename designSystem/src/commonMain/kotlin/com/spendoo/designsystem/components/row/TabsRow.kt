package com.spendoo.designsystem.components.row

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.modifier.clickableNoRipple
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.asString
import org.jetbrains.compose.resources.StringResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.daily
import spendoo.designsystem.generated.resources.monthly
import spendoo.designsystem.generated.resources.weekly
import spendoo.designsystem.generated.resources.yearly
import kotlin.enums.EnumEntries

@Composable
fun <T: Enum<T>>TabsRow(
    entries: EnumEntries<T>,
    selectedGranularity: T,
    toName: T.() -> StringResource,
    onGranularitySelected: (T) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        entries.forEach { granularity ->
            val isSelected = granularity == selectedGranularity
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickableNoRipple { onGranularitySelected(granularity) }
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {

                Text(
                    text = granularity.toName().asString(),
                    style = Theme.typography.body.medium,
                    color = if (isSelected) Theme.colorScheme.button.primary else Theme.colorScheme.text.body
                )
                if (isSelected) {
                    Box(
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .height(2.dp)
                            .width(24.dp)
                            .background(Theme.colorScheme.button.primary)
                    )
                } else {
                    Spacer(modifier = Modifier.height(6.dp))
                }
            }
        }
    }
}

@Preview
@Composable
private fun TabsRowPreview() {
    SpendooTheme {
        TabsRow(
            entries = TestEnum.entries,
            selectedGranularity = TestEnum.SECOND,
            toName = { this.toName() },
            onGranularitySelected = {}
        )
    }
}

private enum class TestEnum {
    FIRST, SECOND, THIRD, FOURTH
}

private fun TestEnum.toName(): StringResource {
    return when (this) {
        TestEnum.FIRST -> Res.string.daily
        TestEnum.SECOND -> Res.string.weekly
        TestEnum.THIRD -> Res.string.monthly
        TestEnum.FOURTH -> Res.string.yearly
    }
}

