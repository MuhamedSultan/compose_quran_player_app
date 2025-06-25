package com.example.compose_quran_player_app.ui.reciters.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.compose_quran_player_app.R
import com.example.compose_quran_player_app.data.pojo.reciters.ReciterWithMoshaf

@Composable
fun ReciterItem(
    reciterWithMoshaf: ReciterWithMoshaf,
    onItemClick: (ReciterWithMoshaf) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(8.dp, color = Color.White)
            .fillMaxWidth()
            .background(color = Color.White)
            .clickable { onItemClick(reciterWithMoshaf) }
            .padding(8.dp)) {


        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .padding(20.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = reciterWithMoshaf.reciter.name,
                style = MaterialTheme.typography.h5.copy(
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.End
                )
            )

            Spacer(modifier = Modifier.height(8.dp))
            Divider(
                color = Color.Gray,
                thickness = 1.dp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = reciterWithMoshaf.moshaf.name,
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.End,
                color = Color.DarkGray
            )

        }
        Image(
            painter = painterResource(id = R.drawable.app_logo),
            contentDescription = "Reciter Image",
            modifier = Modifier
                .padding(end = 8.dp)
                .size(100.dp),
            contentScale = ContentScale.Crop
        )

    }
}