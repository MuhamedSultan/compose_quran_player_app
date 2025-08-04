package com.example.compose_quran_player_app.ui.suwar.components

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.compose_quran_player_app.common.Result
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.compose_quran_player_app.common.ConnectivityHelper
import com.example.compose_quran_player_app.ui.screen.Screen
import com.example.compose_quran_player_app.ui.suwar.viewModel.SuwarViewModel
import com.example.compose_quran_player_app.ui.suwar_details.viewModel.SharedSelectionViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SuwarScreen(
    navController: NavController,
    searchQuery: String,
    availableSuwarIds: List<String>,
    snackBarHostState: SnackbarHostState = SnackbarHostState(),
    context: Context,
    suwarViewModel: SuwarViewModel = hiltViewModel(),
    sharedViewModel: SharedSelectionViewModel = hiltViewModel(
        navController.getBackStackEntry(Screen.RecitersScreen.route)
    )
) {
    val state = suwarViewModel.suwarList.collectAsState().value

    LaunchedEffect(Unit) {
        suwarViewModel.getAvailableSuwar(availableSuwarIds)
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
                val filteredSuwar = remember(searchQuery){
                    if (searchQuery.isBlank()){
                        state.data
                    }else{
                        state.data.filter { it.name.contains(searchQuery, ignoreCase = true) }
                    }
                }
                LazyColumn {
                    items(filteredSuwar.size) { suwar ->
                        SuwarItem(suwar = filteredSuwar[suwar], onItemClick = {
                            sharedViewModel.setSelectedSuwar(state.data[suwar])
                            sharedViewModel.setAllSuwar(state.data)
                            if (ConnectivityHelper.checkRealInternetAvailability(context = context)) {
                                navController.navigate(Screen.PlayerScreen.route)
                            }else {
                                CoroutineScope(Dispatchers.Main).launch {
                                    snackBarHostState.showSnackbar("No internet connection")
                                }
                            }
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
