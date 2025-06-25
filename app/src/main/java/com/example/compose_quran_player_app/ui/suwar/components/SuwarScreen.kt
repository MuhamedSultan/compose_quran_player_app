package com.example.compose_quran_player_app.ui.suwar.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.compose_quran_player_app.common.Result
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.compose_quran_player_app.ui.screen.Screen
import com.example.compose_quran_player_app.ui.suwar.viewModel.SuwarViewModel
import com.example.compose_quran_player_app.ui.suwar_details.viewModel.SharedSelectionViewModel

@Composable
fun SuwarScreen(
    navController: NavController,
    availableSuwarIds: List<String>,
    suwarViewModel: SuwarViewModel = hiltViewModel(),
    sharedViewModel: SharedSelectionViewModel = hiltViewModel(
        navController.getBackStackEntry(Screen.RecitersScreen.route)
    )
) {
    val state = suwarViewModel.suwarList.collectAsState().value

    LaunchedEffect(Unit) {
        suwarViewModel.getAvailableSuwar(availableSuwarIds)
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.LightGray)) {
        when (state) {
            is Result.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is Result.Success -> {
                LazyColumn {
                    items(state.data.size) { suwar ->
                        SuwarItem(suwar = state.data[suwar], onItemClick = {
                            sharedViewModel.setSelectedSuwar(state.data[suwar])
                            navController.navigate(Screen.PlayerScreen.route)
                        })
                    }
                }
            }

            is Result.Error -> {
                Text("Error: ${state.message}", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}
