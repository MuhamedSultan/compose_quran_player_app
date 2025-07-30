package com.example.compose_quran_player_app.data.db

import com.example.compose_quran_player_app.data.pojo.reciters.Reciter
import com.example.compose_quran_player_app.data.pojo.suwar.Suwar
import kotlinx.coroutines.flow.Flow

class LocalDataSourceImpl(private val dao: Dao) : LocalDataSource {
    override suspend fun saveReciters(reciters: List<Reciter>) {
        dao.saveReciters(reciters)
    }

    override suspend fun saveSuwar(suwar: List<Suwar>) {
        dao.saveSuwar(suwar)
    }

    override suspend fun getAllReciters(): List<Reciter> {
        return dao.getAllReciters()
    }

    override suspend fun getAllSuwarForReciter(surahList: List<String>): List<Suwar> {
        return dao.getAllSuwarForReciter(surahList)
    }
}