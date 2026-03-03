package com.spendoo.identitypresentation.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun ScreenTemplate(
    upperContent: @Composable () -> Unit,
    onClick: () -> Unit,
    buttonText: String,
    lowerContent: @Composable ColumnScope. () -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color(0xFF179FDD)
            )
    ) {
        // Blue Header Section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(
                    Color.White
                )
                .background(
                    color = Color(0xFF179FDD),
                    shape = RoundedCornerShape(bottomEnd = 56.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            upperContent()
        }

        // Content Section
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 46.dp)
                )
                .padding(horizontal = 24.dp)
                .padding(top = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            lowerContent()

            // Sign Up Button
            Button(
                onClick = onClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF179FDD)
                )
            ) {
                Text(
                    text = buttonText,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
        }
    }
}

@Preview(heightDp = 800, widthDp = 400)
@Composable
fun ScreenTemplatePreview() {
    ScreenTemplate(
        upperContent = {
            Text(
                text = "Upper Content",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        },
        onClick = {},
        buttonText = "Action Button"
    ){
        Column(modifier = Modifier.fillMaxSize().weight(1f)) {
            Text("Lower Content Item 1", color = Color.Black)
            Text("Lower Content Item 2", color = Color.Black)
            Text("Lower Content Item 3", color = Color.Black)
        }
    }
}
