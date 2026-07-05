package com.spendoo.designsystem.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.icon.Icon
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.theme.theme.SpendooTheme
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.designsystem.utils.extentions.painter
import org.jetbrains.compose.resources.stringResource
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.ic_money_in_offer
import spendoo.designsystem.generated.resources.ic_money_small
import spendoo.designsystem.generated.resources.vs

data class OfferItemCardUiState(
    val title: String,
    val description: String,
    val save: Int,
    val previous: Int,
    val after: Int,
)


@Composable
fun OfferItemCard(
    offer: OfferItemCardUiState,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Theme.colorScheme.button.secondary,
    shape: Shape = RoundedCornerShape(12.dp)
) {
    Column(
        modifier = modifier
            .background(backgroundColor, shape)
            .border(1.dp, Theme.colorScheme.border.primary, shape)
            .padding( 12.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = offer.title,
                style = Theme.typography.heading.tiny,
                color = Theme.colorScheme.text.title,
                maxLines = 1,
                overflow = Ellipsis,
            )
            Box(
                modifier = Modifier
                    .background(Theme.colorScheme.additional.success, RoundedCornerShape(6.dp))
                    .padding(horizontal = 4.dp, vertical = 5.dp)
            ) {
                Text(
                    text = "${offer.save}",
                    style = Theme.typography.label.medium.extraSmall,
                    color = Theme.colorScheme.additional.onSuccess,
                )
            }
        }
        Text(
            text = offer.description,
            style = Theme.typography.label.medium.small,
            color = Theme.colorScheme.text.body,
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = Theme.colorScheme.border.secondary)

        Row(
            verticalAlignment = Alignment.CenterVertically)
        {
            Icon(
                modifier = Modifier.size(20.dp),
                painter = Res.drawable.ic_money_in_offer.painter(),
                contentDescription = null,
            )
            Text(
                modifier = Modifier.padding(start = 2.dp),
                text = "${offer.after}",
                color = Theme.colorScheme.icon.primary,
                style = Theme.typography.label.medium.medium
            )
            Text(
                modifier = Modifier
                    .padding(start = 4.dp),
                text = stringResource(Res.string.vs),
                color = Theme.colorScheme.text.body,
                style = Theme.typography.label.medium.small,
            )
            Icon(
                modifier = Modifier
                    .size(20.dp)
                    .padding(start = 4.dp),
                painter = Res.drawable.ic_money_small.painter(),
                contentDescription = null,
            )
            Text(
                modifier = Modifier.padding(start = 2.dp),
                text = "${offer.previous}",
                color = Theme.colorScheme.text.body,
                style = Theme.typography.label.medium.small,
                textDecoration = TextDecoration.LineThrough
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun OfferItemCard() = SpendooTheme {
    OfferItemCard(
        offer = OfferItemCardUiState(
            title = "Nescafe Gold",
            description = "Premium instant coffee - Make it at home",
            save = -4800,
            previous = 150,
            after = 350,
        ),
    )
}
