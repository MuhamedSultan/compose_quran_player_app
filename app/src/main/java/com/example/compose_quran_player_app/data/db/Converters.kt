package com.example.compose_quran_player_app.data.db

import androidx.room.TypeConverter
import com.example.compose_quran_player_app.data.pojo.reciters.Moshaf
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromMoshafList(moshafList: List<Moshaf>?): String {
        return gson.toJson(moshafList)
    }

    @TypeConverter
    fun toMoshafList(data: String): List<Moshaf>? {
        val listType = object : TypeToken<List<Moshaf>>() {}.type
        return gson.fromJson(data, listType)
    }


}