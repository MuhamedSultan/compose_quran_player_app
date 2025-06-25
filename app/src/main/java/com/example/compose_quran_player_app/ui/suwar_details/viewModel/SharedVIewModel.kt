package com.example.compose_quran_player_app.ui.suwar_details.viewModel

import androidx.lifecycle.ViewModel
import com.example.compose_quran_player_app.data.pojo.reciters.ReciterWithMoshaf
import com.example.compose_quran_player_app.data.pojo.suwar.Suwar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SharedSelectionViewModel @Inject constructor() : ViewModel() {

    private val _selectedReciter = MutableStateFlow<ReciterWithMoshaf?>(null)
    val selectedReciter: StateFlow<ReciterWithMoshaf?> = _selectedReciter

    private val _selectedSuwar = MutableStateFlow<Suwar?>(null)
    val selectedSuwar: StateFlow<Suwar?> = _selectedSuwar

    fun setSelectedReciter(reciter: ReciterWithMoshaf) {
        _selectedReciter.value = reciter
    }

    fun setSelectedSuwar(suwar: Suwar) {
        _selectedSuwar.value = suwar
    }
}