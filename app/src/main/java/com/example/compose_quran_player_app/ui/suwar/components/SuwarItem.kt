package com.example.compose_quran_player_app.ui.suwar.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose_quran_player_app.data.pojo.suwar.Suwar

@Composable
fun SuwarItem(suwar:Suwar, onItemClick: () -> Unit) {
    Row(modifier = Modifier .padding(8.dp)
        .clip(RoundedCornerShape(12.dp))
        .border(24.dp, color = Color.White)
        .fillMaxWidth().padding(24.dp)
        .background(color = Color.White).clickable(onClick = onItemClick), horizontalArrangement = Arrangement.End) {
        Text(text = suwar.name, style = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.SemiBold))
        Spacer(modifier = Modifier.width(8.dp))
        Image(imageVector = Icons.Default.PlayArrow, contentDescription = "player")
    }

}