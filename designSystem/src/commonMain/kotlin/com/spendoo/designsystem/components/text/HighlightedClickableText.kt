package com.spendoo.designsystem.components.text

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import com.spendoo.designsystem.theme.theme.Theme

@Composable
fun HighlightedClickableText(
    normalText: String,
    highlightedText: String,
    onHighlightClick: () -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = Theme.typography.label.medium.small,
    normalColor: Color = Theme.colorScheme.text.title,
    highlightColor: Color = Theme.colorScheme.button.primary,
    underlineHighlight: Boolean = false,
    textAlign: TextAlign = TextAlign.Center,
) {
    val annotatedText = buildAnnotatedString {
        withStyle(SpanStyle(color = normalColor)) {
            append(normalText)
            append(" ")
        }

        // Define clickable link inside the text
        withLink(
            LinkAnnotation.Clickable(
                tag = "highlight",
                linkInteractionListener = { onHighlightClick() }
            )
        ) {
            withStyle(
                style = SpanStyle(
                    color = highlightColor,
                    textDecoration = if (underlineHighlight) TextDecoration.Underline else TextDecoration.None,
                    fontSize = textStyle.fontSize,
                    fontFamily = textStyle.fontFamily,
                    fontWeight = textStyle.fontWeight
                )
            ) {
                append(highlightedText)
            }
        }
    }

    BasicText(
        text = annotatedText,
        style = textStyle.copy(textAlign = textAlign),
        modifier = modifier
    )
}
