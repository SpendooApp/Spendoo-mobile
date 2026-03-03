package com.spendoo.identitypresentation.screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import com.spendoo.identitypresentation.generated.resources.Res
import com.spendoo.identitypresentation.generated.resources.ic_eye
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@Preview
@Composable
fun PasswordField() {
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    TextField(
        value = password,
        onValueChange = { password = it },
        placeholder = { Text("Enter Your Password", color = Color(0xFFB0B0B0)) },
        modifier = Modifier

            .height(50.dp)
            .fillMaxWidth(),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedIndicatorColor = Color(0xFFE0E4EB),
            focusedIndicatorColor = Color(0xFFE0E4EB)
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
