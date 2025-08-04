package com.example.compose_quran_player_app

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.compose_quran_player_app.ui.reciters.components.RecitersScreen
import com.example.compose_quran_player_app.ui.screen.Screen
import com.example.compose_quran_player_app.ui.suwar.components.SuwarScreen
import com.example.compose_quran_player_app.ui.suwar_details.SuwarDetailsScreen
import com.example.compose_quran_player_app.ui.theme.Compose_quran_player_appTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var suwarList: List<String>

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val searchQuery = remember { mutableStateOf("") }
            val isSearching = remember { mutableStateOf(false) }
            val snackBarHostState = remember { SnackbarHostState() }

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
                        SearchTopBar(
                            searchQuery = searchQuery.value,
                            isSearching = isSearching.value,
                            onSearchChange = { searchQuery.value = it },
                            onToggleSearch = {
                                isSearching.value = !isSearching.value
                                if (!isSearching.value) {
                                    searchQuery.value = ""
                                }
                            }
                        )
                    },
                    snackbarHost = {
                        SnackbarHost(hostState = snackBarHostState) { data ->
                            Snackbar(
                                snackbarData = data,
                                containerColor = Color.Red,
                                contentColor = Color.White
                            )
                        }
                    }
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Screen.RecitersScreen.route
                    ) {
                        composable(Screen.RecitersScreen.route) {
                            RecitersScreen(
                                navController = navController,
                                searchQuery = searchQuery.value
                            )
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
                                searchQuery = searchQuery.value,
                                availableSuwarIds = suwarList,
                                context = applicationContext,
                                snackBarHostState = snackBarHostState
                            )
                        }

                        composable(Screen.PlayerScreen.route) {
                            SuwarDetailsScreen(navController = navController, suwarList)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun SearchTopBar(
        searchQuery: String,
        isSearching: Boolean,
        onSearchChange: (String) -> Unit,
        onToggleSearch: () -> Unit
    ) {
        val focusRequester = remember { FocusRequester() }
        val keyboardController = LocalSoftwareKeyboardController.current

        TopAppBar(
            title = {
                if (isSearching) {
                    TextField(
                        value = searchQuery,
                        onValueChange = onSearchChange,
                        placeholder = {
                            Text("Search...", color = Color.White, fontSize = 18.sp)
                        },
                        singleLine = true,
                        textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color(0xFF012331),
                            textColor = Color.White,
                            placeholderColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester)
                    )

                    LaunchedEffect(Unit) {
                        focusRequester.requestFocus()
                        keyboardController?.show()
                    }
                } else {
                    Text(
                        "Reciters List",
                        style = TextStyle(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp
                        ),
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            },
            backgroundColor = Color(0xFF012331),
            actions = {
                IconButton(onClick = onToggleSearch) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color.White
                    )
                }
            }
        )
    }
}