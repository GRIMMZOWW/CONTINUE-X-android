package com.bhaumik.continuex.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bhaumik.continuex.ui.theme.CardBorder

@Composable
fun ErrorCard(
    message: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF1a0a0a), RoundedCornerShape(12.dp))
            .border(1.dp, Color(0xFFef4444), RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.Top) {
            Text(
                text = "⚠",
                color = Color(0xFFF59E0B),
                fontSize = 20.sp,
                modifier = Modifier.padding(end = 12.dp)
            )
            Column {
                Text(
                    text = "ERROR",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = message,
                    color = Color(0xFF94A3B8),
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
            }
        }
    }
}
