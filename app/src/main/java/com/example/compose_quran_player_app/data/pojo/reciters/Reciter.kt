package com.example.compose_quran_player_app.data.pojo.reciters

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reciters_table")
data class Reciter(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val letter: String,
    val moshaf: List<Moshaf>,
    val name: String
)