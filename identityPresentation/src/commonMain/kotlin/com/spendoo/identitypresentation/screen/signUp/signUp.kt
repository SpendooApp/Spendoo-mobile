package com.spendoo.identitypresentation.screen.signUp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spendoo.designsystem.components.button.Button
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.components.textField.TextField
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.identitypresentation.screen.components.PasswordField
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.ic_female
import spendoo.designsystem.generated.resources.ic_male

@Preview
@Composable
fun SignUpScreen() {
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var age: Int? by remember { mutableStateOf(null) }

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
            Text(
                text = "Create Account",
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
                .padding(top = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Gender Selection
            Text(
                text = "What is your gender?",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = Theme.typography.label.medium.medium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Male Button
                StaticGenderButton(
                    gender = Gender.Male,
                    isSelected = true
                )

                // Female Button
                StaticGenderButton(
                    gender = Gender.Female,
                    isSelected = false
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Username Field
            TextField(
                value = username,
                onValueChange = { username = it },
                placeholder = { Text("Enter Your Username", color = Color(0xFFB0B0B0), style = Theme.typography.body.small) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Age Field
            TextField(
                value = age?.toString() ?: "",
                onValueChange = { age = it.toIntOrNull() ?: 0 },
                placeholder = { Text("Enter Your Age", color = Color(0xFFB0B0B0), style = Theme.typography.body.small) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Email Field
            TextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("Enter Your Email", color = Color(0xFFB0B0B0), style = Theme.typography.body.small) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password Field
            PasswordField()

            Spacer(modifier = Modifier.height(18.dp))

            // Terms and Conditions Text
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "By continuing, you agree on our ",
                    color = Color(0xFF8B8B8B),
                    fontSize = 12.sp,
                    style = Theme.typography.label.medium.small
                )
                Row {
                    Text(
                        text = "Terms and Conditions",
                        color = Color(0xFF1E88E5),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        style = Theme.typography.label.medium.small
                    )
                    Text(
                        text = " and ",
                        color = Color(0xFF8B8B8B),
                        fontSize = 12.sp,
                        style = Theme.typography.label.medium.small
                    )
                    Text(
                        text = "Privacy",
                        color = Color(0xFF1E88E5),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        style = Theme.typography.label.medium.small
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Sign Up Button
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
            ) {
                Text(
                    text = "Sign Up",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    style = Theme.typography.title.large
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Login Link
            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Already have an account? ",
                    color = Color(0xFFB0B0B0),
                    fontSize = 14.sp,
                    style = Theme.typography.label.medium.medium
                )
                Text(
                    text = "Login",
                    color = Color(0xFF1E88E5),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    style = Theme.typography.label.semiBold.medium
                )
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}


enum class Gender {
    Male, Female
}

@Composable
private fun StaticGenderButton(
    gender: Gender,
    isSelected: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(120.dp)
            .height(100.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = 2.dp,
                color = if (isSelected) Color(0xFF1E88E5) else Color(0xFFE0E0E0),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(8.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(
                    if (gender == Gender.Male) Res.drawable.ic_male
                    else Res.drawable.ic_female
                ),
                contentDescription = gender.name,
                modifier = Modifier.size(48.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = gender.name,
            color = if (isSelected) Color(0xFF69C7FD) else Color(0xFF2D3748),
            fontSize = 14.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold,
            style = Theme.typography.label.medium.medium
        )
    }
}


