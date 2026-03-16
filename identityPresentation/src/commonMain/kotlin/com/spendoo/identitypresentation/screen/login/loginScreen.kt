package com.spendoo.identitypresentation.screen.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spendoo.designsystem.components.button.Button
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.components.textField.TextField
import com.spendoo.designsystem.components.textField.customTextFieldColors
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.identitypresentation.screen.components.PasswordField
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun LoginScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color(0xFF179FDD)
            )
    ) {
        // Blue header with rounded bottom corners
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(
                    Color.White
                )
                .background(
                    color = Color(0xFF179FDD),
                    shape = RoundedCornerShape(bottomEnd = 56.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(24.dp)
            ) {
                Text(
                    text = "Hello",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    style = Theme.typography.heading.large
                )
                Text(
                    text = "Welcome back!",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    style = Theme.typography.heading.large
                )
            }
        }


        // Body / form area
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
        ) {
            TextField(
                value = email,
                onValueChange = { email = it },
                placeholder = {
                    Text(
                        "Enter Your Email",
                        color = Color(0xFFB0B0B0),
                        style = Theme.typography.body.medium
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = customTextFieldColors(
                    unfocusedContainerColor = Color(0xFFF5F5F5),
                    focusedContainerColor = Color(0xFFF5F5F5),
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Spacer(modifier = Modifier.height(12.dp))

            PasswordField()

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Forget the password?",
                fontSize = 14.sp,
                color = Color(0xFF179FDD),
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 4.dp)
                    .wrapContentWidth(Alignment.End),
                style = Theme.typography.label.medium.medium
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Full-width rounded Login button
            Button(
                onClick = { /* TODO: handle login */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(26.dp),
            ) {
                Text(
                    text = "Login",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    style = Theme.typography.title.large
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Centered "Don't have an account? SignUp" with clickable SignUp
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Don't have an account? ",
                    fontSize = 16.sp,
                    color = Color(0xFFB0B0B0),
                    style = Theme.typography.label.medium.medium
                )
                Text(
                    text = "SignUp",
                    fontSize = 16.sp,
                    color = Color(0xFF179FDD),
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable { /* TODO: navigate to sign up */ },
                    style = Theme.typography.label.semiBold.medium
                )
            }
        }
    }
}