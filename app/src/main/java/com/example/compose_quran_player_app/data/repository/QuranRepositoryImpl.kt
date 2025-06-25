package com.example.compose_quran_player_app.data.repository

import com.example.compose_quran_player_app.data.pojo.reciters.ReciterWithMoshaf
import com.example.compose_quran_player_app.data.pojo.suwar.Suwar
import com.example.compose_quran_player_app.data.remote.RemoteDataSource
import javax.inject.Inject

class QuranRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) :
    QuranRepository {
    override suspend fun getReciters(): List<ReciterWithMoshaf> {
        val response = remoteDataSource.getAllReciters()
        val reciters = response.body()?.reciters ?: emptyList()
        return reciters.flatMap { reciter ->
            reciter.moshaf.map { moshaf ->
                ReciterWithMoshaf(reciter, moshaf)
            }
        }
    }

    override suspend fun getAvailableSuwar(availableSuwar: List<String>): List<Suwar> {
        return remoteDataSource.getAvailableSuwar()
            .body()?.suwar?.filter { availableSuwar.contains(it.id.toString()) } ?: emptyList()
    }
}