package com.spendoo.identity.presentation.screen.login

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.components.textField.OutlinedTextField
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.identity.presentation.screen.components.ScreenTemplate
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
                style = Theme.typography.label.medium.medium
            )
        },
        onClick = {},
        buttonText = "Send Code",

        )
    {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("Enter Your Email", color = Color(0xFFB0B0B0), style = Theme.typography.body.small) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .height(50.dp),
            shape = RoundedCornerShape(16.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
    }
}

