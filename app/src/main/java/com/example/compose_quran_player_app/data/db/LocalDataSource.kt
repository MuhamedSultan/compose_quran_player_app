package com.example.compose_quran_player_app.data.db

import com.example.compose_quran_player_app.data.pojo.reciters.Reciter
import com.example.compose_quran_player_app.data.pojo.suwar.Suwar
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun saveReciters(reciters: List<Reciter>)
    suspend fun saveSuwar(suwar: List<Suwar>)
    suspend fun getAllReciters(): List<Reciter>
    suspend fun getAllSuwarForReciter(surahList: List<String>): List<Suwar>

}