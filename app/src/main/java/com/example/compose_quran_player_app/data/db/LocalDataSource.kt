package com.example.compose_quran_player_app.data.db

import com.example.compose_quran_player_app.data.pojo.reciters.Reciter
import com.example.compose_quran_player_app.data.pojo.suwar.Suwar

interface LocalDataSource {
    suspend fun saveReciters(reciters: List<Reciter>)
    suspend fun saveSuwar(suwar: List<Suwar>)
}