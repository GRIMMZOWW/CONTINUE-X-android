package com.bhaumik.continuex.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bhaumik.continuex.ui.theme.*
import com.bhaumik.continuex.ui.viewmodel.MainViewModel
import com.bhaumik.continuex.ui.viewmodel.UiState

@Composable
fun HomeScreen(
    viewModel: MainViewModel = viewModel(),
    onOpenDrawer: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val chatText by viewModel.chatText.collectAsStateWithLifecycle()
    val selectedStyle by viewModel.selectedStyle.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        
        // --- CLEAN TOP BAR ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onOpenDrawer) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = TextGray
                )
            }
            
            Text(
                text = "CONTINUE-X",
                color = AccentIndigo,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            
            Box(modifier = Modifier.size(48.dp)) // Balance
        }

        Text(
            text = "Compress your AI chat",
            color = TextGray,
            fontSize = 13.sp,
            modifier = Modifier.padding(top = 4.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // 2. Style Selector
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOf("brief", "detailed", "code").forEach { style ->
                StyleChip(
                    label = style.replaceFirstChar { it.uppercase() },
                    isSelected = selectedStyle == style,
                    onClick = { viewModel.updateStyle(style) },
                    modifier = Modifier.weight(1f).padding(horizontal = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 3. Input Area
        Text(
            text = "PASTE YOUR CONVERSATION",
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )
        
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = chatText,
                onValueChange = { viewModel.updateChatText(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 200.dp),
                placeholder = { Text("Paste your full AI conversation here...", color = TextGray) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = CardBackground,
                    unfocusedContainerColor = CardBackground,
                    focusedBorderColor = AccentIndigo,
                    unfocusedBorderColor = CardBorder,
                    cursorColor = AccentIndigo,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            )
            
            Text(
                text = "${chatText.length} chars",
                color = TextGray,
                fontSize = 10.sp,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(12.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 4. Generate Button
        Button(
            onClick = { viewModel.generateCapsule() },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AccentIndigo,
                disabledContainerColor = AccentIndigo.copy(alpha = 0.5f)
            ),
            shape = RoundedCornerShape(8.dp),
            enabled = chatText.isNotBlank() && uiState !is UiState.Loading
        ) {
            if (uiState is UiState.Loading) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 2.dp
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text("Compressing...", color = Color.White, fontWeight = FontWeight.Bold)
            } else {
                Text("Generate Capsule \u26A1", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }

        // 5. Output Area
        AnimatedVisibility(visible = uiState is UiState.Success || uiState is UiState.Error) {
            Column(modifier = Modifier.padding(top = 32.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(if (uiState is UiState.Success) SuccessGreen else Color.Red, RoundedCornerShape(4.dp))
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (uiState is UiState.Success) "GENERATED CAPSULE" else "ERROR",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = CardBackground),
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
                ) {
                    val content = when (val state = uiState) {
                        is UiState.Success -> state.capsule
                        is UiState.Error -> state.message
                        else -> ""
                    }
                    
                    Text(
                        text = content,
                        color = if (uiState is UiState.Success) Color.White else Color.Red,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 13.sp,
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .heightIn(max = 300.dp)
                            .verticalScroll(rememberScrollState())
                    )
                }

                if (uiState is UiState.Success) {
                    var copied by remember { mutableStateOf(false) }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // 6. Copy Button
                    Button(
                        onClick = {
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clip = ClipData.newPlainText("Capsule", (uiState as UiState.Success).capsule)
                            clipboard.setPrimaryClip(clip)
                            copied = true
                            Handler(Looper.getMainLooper()).postDelayed({ copied = false }, 2000)
                        },
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        shape = RoundedCornerShape(8.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, AccentIndigo)
                    ) {
                        Text(
                            text = if (copied) "\u2713 Copied!" else "Copy Capsule",
                            color = if (copied) SuccessGreen else AccentIndigo,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        // 7. Trust Badge
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "\uD83D\uDD12", fontSize = 12.sp)
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Your chat is never stored",
                color = SuccessGreen,
                fontSize = 12.sp
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun StyleChip(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(40.dp)
            .background(
                color = if (isSelected) AccentIndigo else Color.Transparent,
                shape = RoundedCornerShape(20.dp)
            )
            .border(
                width = 1.dp,
                color = if (isSelected) AccentIndigo else CardBorder,
                shape = RoundedCornerShape(20.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = if (isSelected) Color.White else TextGray,
            fontSize = 14.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}
