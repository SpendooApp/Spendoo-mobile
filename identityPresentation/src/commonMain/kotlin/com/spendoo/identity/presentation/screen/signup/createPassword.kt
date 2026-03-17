package com.spendoo.identity.presentation.screen.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.spendoo.designsystem.components.icon.IconButton
import com.spendoo.designsystem.components.text.Text
import com.spendoo.designsystem.components.textField.OutlinedTextField
import com.spendoo.designsystem.components.textField.customTextFieldColors
import com.spendoo.designsystem.theme.theme.Theme
import com.spendoo.identity.presentation.screen.components.ScreenTemplate
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import spendoo.designsystem.generated.resources.Res
import spendoo.designsystem.generated.resources.ic_eye

@Preview
@Composable
fun ForgetScreen() {
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    ScreenTemplate(
        upperContent = {
            Text(
                text = "Create New Password",
                color = Color.White,
                style = Theme.typography.heading.large
            )
        },
        onClick = {},
        buttonText = "Login",

        )
    {
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("Enter Your New Password", color = Color(0xFFB0B0B0), style = Theme.typography.body.small) },
            modifier = Modifier
                .padding(bottom = 16.dp)
                .height(50.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = customTextFieldColors(
                unfocusedContainerColor = Color(0xFFF5F5F5),
                focusedContainerColor = Color(0xFFF5F5F5),
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            singleLine = true,
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(
                    onClick = { isPasswordVisible = !isPasswordVisible },
                    modifier = Modifier.size(36.dp)
                ) {
                    Image(
                        painter = painterResource(Res.drawable.ic_eye),
                        contentDescription = "Toggle password visibility",
                        modifier = Modifier
                            .size(24.dp)

                    )
                }
            }
        )


    }
}

