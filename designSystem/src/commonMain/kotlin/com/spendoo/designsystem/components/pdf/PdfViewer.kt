package com.spendoo.designsystem.components.pdf

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.spendoo.designsystem.theme.theme.Theme

@Composable
fun PdfViewer(
    modifier: Modifier = Modifier,
    pdf: ByteArray,
    aspectRatio: Float? = 0.7071f,
    onPagesReady: (Boolean) -> Unit = {}
) {
    var pages by remember { mutableStateOf<List<ByteArray>?>(null) }
    
    LaunchedEffect(pdf) {
        pages = splitPdfToPngs(pdfData = pdf)
        onPagesReady(true)
    }
    
    if (pages == null) {
        onPagesReady(false)
        Box(modifier = modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Theme.colorScheme.button.primary
            )
        }
    } else {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(top = 16.dp, bottom = 88.dp)
        ) {
            items(pages.orEmpty()) { page ->
                AsyncImage(
                    model = page,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .let { mod ->
                            if (aspectRatio != null) {
                                mod.aspectRatio(aspectRatio)
                            } else {
                                mod
                            }
                        }
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White),
                )
            }
        }
    }
}
