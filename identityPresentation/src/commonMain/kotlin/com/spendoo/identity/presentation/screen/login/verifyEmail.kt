package com.spendoo.identity.presentation.screen.login

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spendoo.designsystem.components.button.Button
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.theme.theme.Theme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun VerifyEmail() {
    val otpValues = remember { mutableStateOf(listOf("", "", "", "")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF179FDD))
    ) {
        // Blue Header Section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(
                    color = Color(0xFF179FDD),
                    shape = RoundedCornerShape(bottomEnd = 56.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Verify your Email",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                style = Theme.typography.heading.large
            )
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
                .padding(top = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "Enter code sent on your email",
                color = Color.Black,
                style = Theme.typography.title.large
            )

            // OTP Input Fields
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp, 10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(5) { index ->
                    BasicTextField(
                        value = otpValues.value.getOrNull(index) ?: "",
                        onValueChange = { value ->
                            if (value.length <= 1 && value.all { it.isDigit() }) {
                                val newValues = otpValues.value.toMutableList()
                                newValues[index] = value
                                otpValues.value = newValues
                            }
                        },
                        modifier = Modifier
                            .size(50.dp)
                            .border(
                                width = 2.dp,
                                color = Color(0xFFE0E4EB),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        textStyle = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        ),
                        singleLine = true
                    )
                }
            }

            // Timer
            Text(
                text = "00:50",
                color = Color.Black,
                fontSize = 14.sp,
                modifier = Modifier.align(Alignment.End),
                style = Theme.typography.label.medium.medium
            )

            // Verify Button
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Text(
                    text = "Verify Code",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    style = Theme.typography.title.large
                )
            }

            // Resend Text
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Didn't receive code? ",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    style = Theme.typography.label.medium.medium
                )
                Text(
                    text = "Resend",
                    color = Color(0xFF179FDD),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    style = Theme.typography.label.semiBold.medium
                )
            }

            // Terms and Condition Text
        }
    }
}
