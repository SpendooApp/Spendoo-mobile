package com.spendoo.identitypresentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spendoo.designsystem.utils.shadow
import org.jetbrains.compose.resources.painterResource
import com.spendoo.identitypresentation.generated.resources.Res
import com.spendoo.identitypresentation.generated.resources.Logo
import com.spendoo.identitypresentation.generated.resources.four_arrow
import org.jetbrains.compose.ui.tooling.preview.Preview


@Preview
@Composable
fun LoginScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1768AA)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Circular Logo Container
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(top = 200.dp)
                .size(220.dp)
                .dropShadow(
                    CircleShape,
                    shadow = shadow,
                )
                .background(Color.White, CircleShape)
        ) {
            Image(
                painter = painterResource(Res.drawable.Logo),
                contentDescription = "Logo",
                modifier = Modifier.size(230.dp)
            )
        }

        Spacer(modifier = Modifier.height(110.dp))

        // Swipe Text
        Column(
            modifier = Modifier
                .padding(start = 10.dp, end = 20.dp)
                .align(Alignment.Start)
        ) {
            Row {
                Text(
                    text = "Swipe up",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,

                    )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "to",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
            Text(
                text = "get started.",
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Arrow Indicator (KMP Safe)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(bottom = 56.dp)
        ) {
            Image(
                painter = painterResource(Res.drawable.four_arrow),
                contentDescription = null,
                modifier = Modifier.size(24.dp, 36.dp)
            )

        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 40.dp
                    )
                )
                .background(Color.White)
        )

    }
}
