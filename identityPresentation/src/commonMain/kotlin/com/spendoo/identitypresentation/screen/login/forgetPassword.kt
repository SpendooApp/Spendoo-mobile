package com.spendoo.identitypresentation.screen.login

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spendoo.identitypresentation.screen.components.ScreenTemplate
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun ForgetScreen() {
    var email by remember { mutableStateOf("") }
    ScreenTemplate(
        upperContent = {
            Text(
                text = "Forget Password ?",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        },
        onClick = {},
        buttonText = "Send Code",

        )
    {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("Enter Your Email", color = Color(0xFFB0B0B0)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .height(50.dp),
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
               unfocusedIndicatorColor = Color(0xFFE0E4EB),
               focusedIndicatorColor = Color(0xFFE0E4EB)
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )


    }
}

