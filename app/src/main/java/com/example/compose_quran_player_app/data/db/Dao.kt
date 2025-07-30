package com.example.compose_quran_player_app.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.compose_quran_player_app.data.pojo.reciters.Reciter
import com.example.compose_quran_player_app.data.pojo.suwar.Suwar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveReciters(reciter: List<Reciter>)

    @Query("SELECT * FROM reciters_table")
       suspend fun getAllReciters(): List<Reciter>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSuwar(suwar: List<Suwar>)

    @Query("SELECT * FROM suwar_table WHERE id IN (:surahList)")
    suspend fun getAllSuwarForReciter(surahList: List<String>): List<Suwar>
}