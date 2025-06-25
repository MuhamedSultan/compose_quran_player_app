package com.example.compose_quran_player_app.data.repository

import com.example.compose_quran_player_app.data.pojo.reciters.ReciterWithMoshaf
import com.example.compose_quran_player_app.data.pojo.reciters.RecitersResponse
import com.example.compose_quran_player_app.data.pojo.suwar.Suwar
import retrofit2.Response

interface QuranRepository {
    suspend fun getReciters(): List<ReciterWithMoshaf>
    suspend fun getAvailableSuwar(availableSuwar: List<String>): List<Suwar>
}