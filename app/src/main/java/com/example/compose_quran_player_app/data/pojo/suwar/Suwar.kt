package com.example.compose_quran_player_app.data.pojo.suwar

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "suwar_table")
data class Suwar(
    val end_page: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val makkia: Int,
    val name: String,
    val start_page: Int,
    val type: Int
)