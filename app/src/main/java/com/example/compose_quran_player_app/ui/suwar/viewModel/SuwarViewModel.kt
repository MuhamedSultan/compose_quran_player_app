package com.example.compose_quran_player_app.ui.suwar.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose_quran_player_app.common.Result
import com.example.compose_quran_player_app.data.pojo.suwar.Suwar
import com.example.compose_quran_player_app.data.repository.QuranRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SuwarViewModel @Inject constructor(private val quranRepository: QuranRepository) :
    ViewModel() {
    private val _suwarList: MutableStateFlow<Result<List<Suwar>>> = MutableStateFlow(Result.Loading)
    val suwarList: StateFlow<Result<List<Suwar>>> = _suwarList

    fun getAvailableSuwar(availableSuwar: List<String>) {

        viewModelScope.launch {
            _suwarList.value = Result.Loading
            try {
                val suwar = quranRepository.getAvailableSuwar(availableSuwar)
                _suwarList.value = Result.Success(suwar)
            } catch (ex: Exception) {
                _suwarList.value = Result.Error(ex.localizedMessage ?: "Unknown error")
            }

        }
    }
}