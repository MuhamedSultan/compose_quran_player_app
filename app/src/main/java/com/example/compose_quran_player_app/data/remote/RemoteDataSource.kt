package com.example.compose_quran_player_app.data.remote

import com.example.compose_quran_player_app.data.pojo.reciters.RecitersResponse
import com.example.compose_quran_player_app.data.pojo.suwar.SuwarResponse
import retrofit2.Response

interface RemoteDataSource {
    suspend fun getAllReciters(): Response<RecitersResponse>
    suspend fun getAvailableSuwar():Response<SuwarResponse>
}