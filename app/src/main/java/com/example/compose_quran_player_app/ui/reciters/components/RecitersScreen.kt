package com.example.compose_quran_player_app.ui.reciters.components

import android.annotation.SuppressLint
import android.provider.CalendarContract.Colors
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.compose_quran_player_app.common.Result
import com.example.compose_quran_player_app.data.pojo.reciters.ReciterWithMoshaf
import com.example.compose_quran_player_app.ui.reciters.viewModel.RecitersViewModel
import com.example.compose_quran_player_app.ui.screen.Screen
import com.example.compose_quran_player_app.ui.suwar_details.viewModel.SharedSelectionViewModel

@Composable
fun RecitersScreen(
     navController: NavController,
    viewModel: RecitersViewModel = hiltViewModel(),
      sharedViewModel: SharedSelectionViewModel = hiltViewModel()

) {
    val state = viewModel.reciters.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.getReciters()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.LightGray)
    ) {
        when (state) {
            is Result.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is Result.Success -> {
                val reciters = state.data
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(reciters) { item: ReciterWithMoshaf ->
                        val surahIds = item.moshaf.surah_list
                        ReciterItem(reciterWithMoshaf = item, onItemClick = {
                            sharedViewModel.setSelectedReciter(item)
                            navController.navigate(Screen.SuwarScreen.passArgs(surahIds))
                        })
                    }
                }
            }

            is Result.Error -> {
                Text(
                    text = state.message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}
