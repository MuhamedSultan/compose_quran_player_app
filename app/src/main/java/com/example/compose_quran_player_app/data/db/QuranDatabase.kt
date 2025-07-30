package com.example.compose_quran_player_app.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.compose_quran_player_app.data.pojo.reciters.Reciter
import com.example.compose_quran_player_app.data.pojo.suwar.Suwar

@Database(entities = [Reciter::class, Suwar::class], version = 1)
@TypeConverters(Converters::class)
abstract class QuranDatabase : RoomDatabase() {
    abstract fun dao(): Dao

    companion object {
        @Volatile
        private var instance: QuranDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext, QuranDatabase::class.java,
                "reciters_database",
            ).fallbackToDestructiveMigration().build()
    }

}