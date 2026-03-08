package com.bhaumik.continuex.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bhaumik.continuex.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(onOpenDrawer: () -> Unit) {
    Scaffold(
        containerColor = BackgroundDark,
        topBar = {
            TopAppBar(
                title = { Text("About", color = White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onOpenDrawer) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu", tint = White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundDark)
            )
        }
    ) { innerPadding ->
        AboutContent(modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun AboutContent(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // Top Section: Monogram
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(CardBackground, CircleShape)
                .border(
                    width = 2.dp,
                    brush = Brush.linearGradient(listOf(AccentIndigo, Color.Cyan)),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "B",
                color = White,
                fontSize = 48.sp,
                fontWeight = FontWeight.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Bhaumik",
            color = White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Founder, CONTINUE-X",
            color = TextGray,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Middle Section: About Text
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Built by Bhaumik",
                color = White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "CONTINUE-X was born from a real problem I faced myself. Long AI sessions losing context, my work getting interrupted, momentum breaking every time I had to start a new chat. So I built this to solve my own problem — and now it solves yours too.",
                color = TextGray,
                fontSize = 15.sp,
                lineHeight = 24.sp
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row {
                AboutTagBadge(text = "Vibecoder")
                Spacer(modifier = Modifier.width(8.dp))
                AboutTagBadge(text = "AI Builder")
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        // FOR TEAMS Section
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, CardBorder, RoundedCornerShape(12.dp)),
            colors = CardDefaults.cardColors(containerColor = CardBackground),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "FOR TEAMS",
                    color = AccentIndigo,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Built for serious AI work",
                    color = White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Whether you are a solo developer, a team using AI daily, or an enterprise — CONTINUE-X keeps your work moving without losing context.",
                    color = TextGray,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                TeamCheckItem("No account needed — works instantly")
                TeamCheckItem("Share capsules with teammates")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Stats Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatItem(number = "31,000+", label = "characters\ncompressed")
            StatItem(number = "3", label = "capsule\nstyles")
            StatItem(number = "0", label = "bytes\nstored")
        }

        Spacer(modifier = Modifier.height(48.dp))

        // GitHub Button
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clickable {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/GRIMMZOWW"))
                    context.startActivity(intent)
                }
                .border(1.dp, CardBorder, RoundedCornerShape(12.dp)),
            colors = CardDefaults.cardColors(containerColor = CardBackground),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "github.com/GRIMMZOWW",
                    color = White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Co-built with Claude (Anthropic)",
            color = Color(0xFF334155),
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))
        
        // --- ADVANCED SETTINGS ---
        var showSettings by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf(false) }
        val sharedPrefs = context.getSharedPreferences("continuex_prefs", android.content.Context.MODE_PRIVATE)
        
        var provider by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf(sharedPrefs.getString("continuex_provider", "groq") ?: "groq") }
        var apiKey by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf(sharedPrefs.getString("continuex_api_key", "") ?: "") }
        var selectedModel by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf(sharedPrefs.getString("continuex_model", "llama-3.3-70b-versatile") ?: "llama-3.3-70b-versatile") }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .clickable { showSettings = !showSettings }
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "\u2699 Advanced Settings",
                    color = Color(0xFF334155),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            androidx.compose.animation.AnimatedVisibility(
                visible = showSettings,
                enter = androidx.compose.animation.expandVertically() + androidx.compose.animation.fadeIn(),
                exit = androidx.compose.animation.shrinkVertically() + androidx.compose.animation.fadeOut()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .background(CardBackground, RoundedCornerShape(12.dp))
                        .border(1.dp, CardBorder, RoundedCornerShape(12.dp))
                        .padding(20.dp)
                ) {
                    Text(
                        text = "Use Your Own API Key",
                        color = White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "By default CONTINUE-X uses built-in AI. Add your own key to use your preferred model.",
                        color = Color(0xFF64748B),
                        fontSize = 12.sp
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Provider Tabs
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(BackgroundDark, RoundedCornerShape(8.dp))
                            .padding(4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .background(if (provider == "groq") CardBorder else Color.Transparent, RoundedCornerShape(6.dp))
                                .clickable { provider = "groq"; selectedModel = "llama-3.3-70b-versatile" }
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Groq", color = if (provider == "groq") White else TextGray, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                        }
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .background(if (provider == "openai") CardBorder else Color.Transparent, RoundedCornerShape(6.dp))
                                .clickable { provider = "openai"; selectedModel = "gpt-4o-mini" }
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("OpenAI", color = if (provider == "openai") White else TextGray, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // API Key Input
                    Text(
                        text = "${if (provider == "groq") "Groq" else "OpenAI"} API Key",
                        color = TextGray,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    OutlinedTextField(
                        value = apiKey,
                        onValueChange = { apiKey = it },
                        placeholder = { Text(if (provider == "groq") "gsk_..." else "sk-...", color = Color(0xFF334155)) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = BackgroundDark,
                            unfocusedContainerColor = BackgroundDark,
                            focusedBorderColor = AccentIndigo,
                            unfocusedBorderColor = CardBorder,
                            focusedTextColor = White,
                            unfocusedTextColor = White
                        ),
                        shape = RoundedCornerShape(8.dp),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Model Dropdown
                    Text(
                        text = "Model",
                        color = TextGray,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    
                    var expanded by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf(false) }
                    val models = if (provider == "groq") {
                        listOf("llama-3.3-70b-versatile", "llama-3.1-8b-instant", "mixtral-8x7b-32768")
                    } else {
                        listOf("gpt-4o-mini", "gpt-4o", "gpt-3.5-turbo")
                    }

                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = selectedModel,
                            onValueChange = {},
                            readOnly = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { expanded = true },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = BackgroundDark,
                                unfocusedContainerColor = BackgroundDark,
                                disabledContainerColor = BackgroundDark,
                                disabledBorderColor = CardBorder,
                                disabledTextColor = White
                            ),
                            shape = RoundedCornerShape(8.dp),
                            enabled = false
                        )
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier.background(CardBackground)
                        ) {
                            models.forEach { m ->
                                DropdownMenuItem(
                                    text = { Text(m, color = White) },
                                    onClick = {
                                        selectedModel = m
                                        expanded = false
                                    }
                                )
                            }
                        }
                        Box(modifier = Modifier.matchParentSize().clickable { expanded = true })
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Get free ${if (provider == "groq") "Groq" else "OpenAI"} API key \u2192",
                        color = AccentIndigo,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            val url = if (provider == "groq") "https://console.groq.com" else "https://platform.openai.com"
                            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                        }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Action Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            onClick = {
                                sharedPrefs.edit().clear().apply()
                                apiKey = ""
                                provider = "groq"
                                selectedModel = "llama-3.3-70b-versatile"
                                android.widget.Toast.makeText(context, "Reset to default", android.widget.Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.weight(1f).height(40.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                            border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Clear & Default", color = TextGray, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = {
                                sharedPrefs.edit()
                                    .putString("continuex_provider", provider)
                                    .putString("continuex_api_key", apiKey)
                                    .putString("continuex_model", selectedModel)
                                    .apply()
                                android.widget.Toast.makeText(context, "Settings saved", android.widget.Toast.LENGTH_SHORT).show()
                            },
                            modifier = Modifier.weight(1f).height(40.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = AccentIndigo),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Save Settings", color = White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        // Privacy Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Privacy Policy",
                color = Color(0xFF475569),
                fontSize = 13.sp,
                modifier = Modifier.clickable {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://continue-x.vercel.app/privacy"))
                    context.startActivity(intent)
                }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Terms of Use",
                color = Color(0xFF475569),
                fontSize = 13.sp,
                modifier = Modifier.clickable {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://continue-x.vercel.app/terms"))
                    context.startActivity(intent)
                }
            )
        }
        
        Spacer(modifier = Modifier.height(48.dp))
    }
}

@Composable
fun TeamCheckItem(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(bottom = 8.dp)
    ) {
        Text(text = "✓", color = SuccessGreen, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, color = TextGray, fontSize = 13.sp)
    }
}

@Composable
fun StatItem(number: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = number, color = White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text(
            text = label,
            color = TextGray,
            fontSize = 10.sp,
            textAlign = TextAlign.Center,
            lineHeight = 14.sp
        )
    }
}

@Composable
fun AboutTagBadge(text: String) {
    Surface(
        color = AccentIndigo.copy(alpha = 0.1f),
        shape = RoundedCornerShape(20.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, AccentIndigo.copy(alpha = 0.3f))
    ) {
        Text(
            text = text,
            color = AccentIndigo,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}
