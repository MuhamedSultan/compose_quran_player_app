package com.example.compose_quran_player_app.ui.screen

sealed class Screen(val route:String) {
    object RecitersScreen:Screen("reciters_screen")
    object SuwarScreen : Screen("suwar_screen/{suwarIds}") {
        fun passArgs(suwarIds: String): String = "suwar_screen/$suwarIds"
    }
    object PlayerScreen:Screen("player_screen")
}