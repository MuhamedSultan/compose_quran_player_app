package com.example.compose_quran_player_app.data.remote

import com.example.compose_quran_player_app.data.pojo.reciters.RecitersResponse
import com.example.compose_quran_player_app.data.pojo.suwar.SuwarResponse
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val apiService: ApiService) : RemoteDataSource {
    override suspend fun getAllReciters(): Response<RecitersResponse> {
        return apiService.getReciters()
    }

    override suspend fun getAvailableSuwar(): Response<SuwarResponse> {
       return apiService.getAvailableSuwar()
    }
}