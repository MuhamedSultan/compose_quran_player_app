package com.example.compose_quran_player_app.ui.suwar_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.compose_quran_player_app.R
import com.example.compose_quran_player_app.ui.screen.Screen
import com.example.compose_quran_player_app.ui.suwar_details.viewModel.SharedSelectionViewModel

@Composable
fun SuwarDetailsScreen(
    navController: NavController,
    sharedViewModel: SharedSelectionViewModel = hiltViewModel(
        navController.getBackStackEntry(Screen.RecitersScreen.route)
    )
) {
    val selectedReciter = sharedViewModel.selectedReciter.collectAsState().value
    val selectedSuwar = sharedViewModel.selectedSuwar.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.suwar_details_image),
            contentDescription = "suwar image",
            modifier = Modifier
                .size(350.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,

            )
        Spacer(Modifier.height(24.dp))
        Text(
            selectedSuwar?.name.toString(), style = TextStyle(
                fontWeight = FontWeight.SemiBold
            ), fontSize = 24.sp
        )
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                selectedReciter?.moshaf?.name.toString(), style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    color = Color.DarkGray
                ), fontSize = 24.sp
            )
            Text(
                text = " ــــ ",
                fontSize = 20.sp,
            )

            Text(
                selectedReciter?.reciter?.name.toString(), style = TextStyle(
                    fontWeight = FontWeight.SemiBold, color = Color.DarkGray
                ), fontSize = 24.sp
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        var sliderPosition by remember { mutableFloatStateOf(0f) }

        Slider(
            value = sliderPosition,
            onValueChange = { sliderPosition = it },
            valueRange = 0f..100f,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        )


    }
}
