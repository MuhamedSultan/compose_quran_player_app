package com.example.compose_quran_player_app.data.db

import com.example.compose_quran_player_app.data.pojo.reciters.Reciter
import com.example.compose_quran_player_app.data.pojo.suwar.Suwar

class LocalDataSourceImpl(private val dao: Dao) :LocalDataSource {
    override suspend fun saveReciters(reciters: List<Reciter>) {
     dao.saveReciters(reciters)
    }

    override suspend fun saveSuwar(suwar: List<Suwar>) {
        dao.saveSuwar(suwar)
    }
}