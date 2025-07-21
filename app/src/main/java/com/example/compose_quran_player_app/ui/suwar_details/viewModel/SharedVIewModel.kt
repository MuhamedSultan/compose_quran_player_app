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

    private val _allSuwar = MutableStateFlow<List<Suwar>>(emptyList())
    val allSuwar: StateFlow<List<Suwar>> = _allSuwar

    private val _isRandom = MutableStateFlow(false)
    val isRandom: StateFlow<Boolean> = _isRandom


    fun setSelectedReciter(reciter: ReciterWithMoshaf) {
        _selectedReciter.value = reciter
    }

    fun setSelectedSuwar(suwar: Suwar) {
        _selectedSuwar.value = suwar
    }

    fun setAllSuwar(list: List<Suwar>) {
        _allSuwar.value = list
    }

fun selectPreviousOrNextSuwar(
    direction: Direction,
    availableSuwar: List<String>
) {
    val currentId = _selectedSuwar.value?.id ?: return

    val currentIndex = availableSuwar.indexOf(currentId.toString())

    val newIndex = when (direction) {
        Direction.PREVIOUS -> currentIndex - 1
        Direction.NEXT -> currentIndex + 1
    }

    if (newIndex in availableSuwar.indices) {
        val newId = availableSuwar[newIndex].toIntOrNull()
        val newSuwar = _allSuwar.value.find { it.id == newId }
        newSuwar?.let {
            _selectedSuwar.value = it
        }
    }
}
    fun playRandomSuwar(availableSuwar: List<String>){
        val randomIndex=availableSuwar.randomOrNull()?.toIntOrNull()
        val randomSuwar=_allSuwar.value.find {
            it.id==randomIndex
        }
        randomSuwar?.let {
            _selectedSuwar.value=it
        }
    }
    fun loopCurrentSurah(){
        _selectedSuwar.value=_selectedSuwar.value
    }

    fun toggleRandom() {
        _isRandom.value = !_isRandom.value
    }
    enum class Direction {
        PREVIOUS, NEXT
    }

}
