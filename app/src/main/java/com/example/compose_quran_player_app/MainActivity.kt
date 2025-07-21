package com.example.compose_quran_player_app

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.Colors
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.compose_quran_player_app.data.pojo.reciters.Reciter
import com.example.compose_quran_player_app.ui.reciters.components.RecitersScreen
import com.example.compose_quran_player_app.ui.screen.Screen
import com.example.compose_quran_player_app.ui.suwar.components.SuwarScreen
import com.example.compose_quran_player_app.ui.suwar_details.SuwarDetailsScreen
import com.example.compose_quran_player_app.ui.theme.Compose_quran_player_appTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var suwarList:List<String>

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()
        setContent {
            Compose_quran_player_appTheme {
                val systemUiController = rememberSystemUiController()
                val navController = rememberNavController()
                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = Color(0xFF012331),
                        darkIcons = false
                    )
                }
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    "Reciters List",
                                    style = TextStyle(
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 18.sp
                                    ),
                                    color = Color.White,
                                    textAlign = TextAlign.Center
                                )
                            },
                            backgroundColor = Color(0xFF012331),

                            actions = {
                                IconButton(onClick = {

                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = "Search",
                                        tint = Color.White
                                    )
                                }
                            })
                    }
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Screen.RecitersScreen.route
                    ) {
                        composable(Screen.RecitersScreen.route) {
                            RecitersScreen(navController = navController)
                        }

                        composable(
                            route = Screen.SuwarScreen.route,
                            arguments = listOf(navArgument("suwarIds") {
                                type = NavType.StringType
                            })
                        ) { backStackEntry ->
                            val suwarIds = backStackEntry.arguments?.getString("suwarIds") ?: ""
                             suwarList = suwarIds.split(",").map { it.trim() }
                            SuwarScreen(
                                navController = navController,
                                availableSuwarIds = suwarList,
                            )
                        }
                        composable(
                            Screen.PlayerScreen.route){

                            SuwarDetailsScreen(navController = navController,suwarList)
                        }
                    }
                }
            }
        }
    }
}
