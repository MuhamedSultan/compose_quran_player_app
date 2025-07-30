package com.example.compose_quran_player_app.di

import android.content.Context
import androidx.room.Room
import com.example.compose_quran_player_app.common.Constants.BASE_URL
import com.example.compose_quran_player_app.data.db.Dao
import com.example.compose_quran_player_app.data.db.LocalDataSource
import com.example.compose_quran_player_app.data.db.LocalDataSourceImpl
import com.example.compose_quran_player_app.data.db.QuranDatabase
import com.example.compose_quran_player_app.data.remote.ApiService
import com.example.compose_quran_player_app.data.remote.RemoteDataSource
import com.example.compose_quran_player_app.data.remote.RemoteDataSourceImpl
import com.example.compose_quran_player_app.data.repository.QuranRepository
import com.example.compose_quran_player_app.data.repository.QuranRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideLocalDataSource(dao: Dao): LocalDataSource {
        return LocalDataSourceImpl(dao)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): QuranDatabase {
        return Room.databaseBuilder(
            context,
            QuranDatabase::class.java,
            "quran_database"
        ).build()
    }

    @Provides
    fun provideDao(database: QuranDatabase): Dao {
        return database.dao()
    }

    @Provides
    @Singleton
    fun provideReciterRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource,
        @ApplicationContext context: Context
    ): QuranRepository {
        return QuranRepositoryImpl(remoteDataSource, localDataSource, context)
    }

}