package com.example.compose_quran_player_app.data.remote

import com.example.compose_quran_player_app.data.pojo.reciters.RecitersResponse
import com.example.compose_quran_player_app.data.pojo.suwar.SuwarResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("api/v3/reciters")
    suspend fun getReciters(): Response<RecitersResponse>

    @GET("api/v3/suwar")
    suspend fun getAvailableSuwar(): Response<SuwarResponse>
}