package com.example.compose_quran_player_app.ui.reciters.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose_quran_player_app.common.Result
import com.example.compose_quran_player_app.data.pojo.reciters.Reciter
import com.example.compose_quran_player_app.data.pojo.reciters.ReciterWithMoshaf
import com.example.compose_quran_player_app.data.repository.QuranRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class RecitersViewModel @Inject constructor(private val repository: QuranRepository) : ViewModel() {
    private val _reciters = MutableStateFlow<Result<List<ReciterWithMoshaf>>>(Result.Loading)
    val reciters: StateFlow<Result<List<ReciterWithMoshaf>>> = _reciters

    fun getReciters() {
        viewModelScope.launch {
            _reciters.value = Result.Loading
            try {
                val data = repository.getReciters()
                _reciters.value = Result.Success(data)
            } catch (ex: Exception) {
                _reciters.value = Result.Error(ex.localizedMessage ?: "Unknown error")
                Log.e("RecitersViewModel", "getReciters error: ", ex)
            }
        }
    }

}