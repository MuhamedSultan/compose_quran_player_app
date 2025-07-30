package com.example.compose_quran_player_app.data.repository

import android.content.Context
import com.example.compose_quran_player_app.common.ConnectivityHelper
import com.example.compose_quran_player_app.data.db.LocalDataSource
import com.example.compose_quran_player_app.data.pojo.reciters.ReciterWithMoshaf
import com.example.compose_quran_player_app.data.pojo.suwar.Suwar
import com.example.compose_quran_player_app.data.remote.RemoteDataSource
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class QuranRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val context: Context
) :
    QuranRepository {
    override suspend fun getReciters(): List<ReciterWithMoshaf> {
        if (ConnectivityHelper.checkRealInternetAvailability(context)) {
            val response = remoteDataSource.getAllReciters()
            val reciters = response.body()?.reciters ?: emptyList()
            localDataSource.saveReciters(reciters)
            return reciters.flatMap { reciter ->
                reciter.moshaf.map { moshaf ->
                    ReciterWithMoshaf(reciter, moshaf)
                }
            }
        } else {
            return localDataSource.getAllReciters().flatMap { reciter ->
                reciter.moshaf.map { moshaf ->
                    ReciterWithMoshaf(reciter, moshaf)
                }
            }
        }
    }

    override suspend fun getAvailableSuwar(availableSuwar: List<String>): List<Suwar> {
        if (ConnectivityHelper.checkRealInternetAvailability(context)) {
            val suwar = remoteDataSource.getAvailableSuwar()
                .body()?.suwar?.filter { availableSuwar.contains(it.id.toString()) } ?: emptyList()
            localDataSource.saveSuwar(suwar)
            return suwar
        } else {
            return localDataSource.getAllSuwarForReciter(availableSuwar)
        }
    }

}