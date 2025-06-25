package com.example.compose_quran_player_app.data.pojo.reciters

data class Reciter(
    val id: Int,
    val letter: String,
    val moshaf: List<Moshaf>,
    val name: String
)