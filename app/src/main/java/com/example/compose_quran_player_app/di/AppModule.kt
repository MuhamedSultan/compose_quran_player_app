package com.example.compose_quran_player_app.di

import com.example.compose_quran_player_app.common.Constants.BASE_URL
import com.example.compose_quran_player_app.data.remote.ApiService
import com.example.compose_quran_player_app.data.remote.RemoteDataSource
import com.example.compose_quran_player_app.data.remote.RemoteDataSourceImpl
import com.example.compose_quran_player_app.data.repository.QuranRepository
import com.example.compose_quran_player_app.data.repository.QuranRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(apiService: ApiService): RemoteDataSource {
        return RemoteDataSourceImpl(apiService)
    }
    @Provides
    @Singleton
    fun provideReciterRepository(remoteDataSource: RemoteDataSource): QuranRepository {
        return QuranRepositoryImpl(remoteDataSource)
    }

}