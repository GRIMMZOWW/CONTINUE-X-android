package com.bhaumik.continuex.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bhaumik.continuex.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HowItWorksScreen(onOpenDrawer: () -> Unit) {
    Scaffold(
        containerColor = BackgroundDark,
        topBar = {
            TopAppBar(
                title = { Text("How It Works", color = White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onOpenDrawer) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu", tint = White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundDark)
            )
        }
    ) { innerPadding ->
        HowItWorksContent(modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun HowItWorksContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StepCard(
            number = "01",
            title = "Paste Your Chat",
            description = "Copy your entire AI conversation from any tool and paste it into CONTINUE-X"
        )

        Spacer(modifier = Modifier.height(16.dp))

        StepCard(
            number = "02",
            title = "Choose Your Style",
            description = "Brief for quick context. Detailed for full history. Code for dev sessions."
        )

        Spacer(modifier = Modifier.height(16.dp))

        StepCard(
            number = "03",
            title = "Resume Instantly",
            description = "Paste the Capsule into any new AI chat and continue exactly where you left off."
        )

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "Why Not Just Copy-Paste?",
            color = White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
            textAlign = TextAlign.Start
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Raw Copy-Paste
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color(0xFFEF4444), RoundedCornerShape(12.dp)),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1a0a0a)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "❌ Raw Copy-Paste",
                        color = Color(0xFFEF4444),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    ComparisonItem("Dumps thousands of lines into new chat")
                    ComparisonItem("AI gets confused by conversation format")
                    ComparisonItem("Wastes your entire context window immediately")
                    ComparisonItem("AI focuses on old messages not your current goal")
                    ComparisonItem("Takes 10+ minutes to re-orient the AI")
                    ComparisonItem("New AI has no idea what decisions were made")
                }
            }

            // CONTINUE-X Capsule
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color(0xFF10B981), RoundedCornerShape(12.dp)),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0a1a0f)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "✅ CONTINUE-X Capsule",
                        color = Color(0xFF10B981),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    ComparisonItem("Sends only what matters — goal, state, next step")
                    ComparisonItem("AI reads it instantly and understands everything")
                    ComparisonItem("Context window saved for actual new work")
                    ComparisonItem("AI starts exactly where you left off")
                    ComparisonItem("Resume in under 30 seconds")
                    ComparisonItem("All key decisions preserved and structured")
                }
            }
        }

        Spacer(modifier = Modifier.height(64.dp))

        Text(
            text = "Works With Every AI Tool",
            color = TextGray,
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )

        // Simple Column instead of FlowRow for crash avoidance
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val tools = listOf("Claude", "ChatGPT", "Cursor", "Gemini", "Copilot")
            tools.chunked(3).forEach { rowTools ->
                Row(horizontalArrangement = Arrangement.Center) {
                    rowTools.forEach { tool ->
                        ToolBadge(name = tool)
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun StepCard(number: String, title: String, description: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, CardBorder, RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = number,
                color = AccentIndigo,
                fontSize = 32.sp,
                fontWeight = FontWeight.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                color = White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                color = TextGray,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
        }
    }
}

@Composable
fun ComparisonItem(text: String) {
    Text(
        text = "• $text",
        color = Color(0xFF94A3B8),
        fontSize = 12.sp,
        lineHeight = 18.sp,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
fun ToolBadge(name: String) {
    Surface(
        color = CardBackground,
        shape = RoundedCornerShape(20.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
    ) {
        Text(
            text = name,
            color = TextGray,
            fontSize = 12.sp,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}
