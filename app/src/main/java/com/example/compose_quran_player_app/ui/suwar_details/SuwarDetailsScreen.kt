package com.example.compose_quran_player_app.ui.suwar_details

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.NavController
import com.example.compose_quran_player_app.R
import com.example.compose_quran_player_app.ui.screen.Screen
import com.example.compose_quran_player_app.ui.suwar_details.viewModel.SharedSelectionViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Composable
fun SuwarDetailsScreen(
    navController: NavController,
    availableSuwar:List<String>,
    sharedViewModel: SharedSelectionViewModel = hiltViewModel(
        navController.getBackStackEntry(Screen.RecitersScreen.route)
    )
) {
    val selectedReciter = sharedViewModel.selectedReciter.collectAsState().value
    val selectedSuwar = sharedViewModel.selectedSuwar.collectAsState().value
    val allSuwar = sharedViewModel.allSuwar.collectAsState().value
    val context = LocalContext.current
    var isLooping by remember { mutableStateOf(false) }

    // val suwarList = allSuwar[0].id.split(",").map { it.trim() }

    val surahIdFormatted = selectedSuwar?.id?.toString()?.padStart(3, '0') ?: ""
    val baseUrl = selectedReciter?.moshaf?.server?.let {
        if (it.endsWith("/")) it else "$it/"
    } ?: ""

//    val audioUrl = baseUrl + surahIdFormatted + ".mp3"
//    val exoPlayer = rememberExoPlayer(context, audioUrl)

    var position by remember { mutableStateOf(0L) }
    var duration by remember { mutableStateOf(1L) }

    val audioUrl = baseUrl + surahIdFormatted + ".mp3"
    val exoPlayer = rememberExoPlayer(context, audioUrl)

    LaunchedEffect(audioUrl) {
        exoPlayer.stop()
        exoPlayer.clearMediaItems()
        val mediaItem = MediaItem.fromUri(audioUrl)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true
    }
    LaunchedEffect(exoPlayer) {
        while (true) {
            position = exoPlayer.currentPosition
            duration = exoPlayer.duration.takeIf { it > 0 } ?: 1L
            delay(500)
        }
    }




    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.suwar_details_image),
                contentDescription = "suwar image",
                modifier = Modifier
                    .size(300.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.height(24.dp))

            Text(
                selectedSuwar?.name.orEmpty(),
                style = TextStyle(fontWeight = FontWeight.SemiBold),
                fontSize = 24.sp
            )

            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    selectedReciter?.moshaf?.name?.trim().orEmpty(),
                    fontSize = 18.sp,
                    color = Color.DarkGray
                )

                Text(" ــ ", fontSize = 20.sp)

                Text(
                    selectedReciter?.reciter?.name?.trim().orEmpty(),
                    fontSize = 18.sp,
                    color = Color.DarkGray
                )
            }

            Spacer(modifier = Modifier.height(16.dp))



            Slider(
                value = position / duration.toFloat(),
                onValueChange = {
                    val newPosition = (it * duration).toLong()
                    exoPlayer.seekTo(newPosition)
                    position = newPosition
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(formatTime(position))
                Text(formatTime(duration))
            }
            Spacer(Modifier.height(24.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                var isPlaying by remember { mutableStateOf(true) }
                val pauseIcon = painterResource(R.drawable.ic_pause)
                val playIcon = painterResource(R.drawable.ic_play)

                Icon(
                    painter = painterResource(R.drawable.ic_random_playing),
                    contentDescription = "Random Surah",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable {

                        }
                )

                Icon(
                    painter = painterResource(R.drawable.ic_previous),
                    contentDescription = "Previous Surah",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable {
                            sharedViewModel.selectPreviousOrNextSuwar(
                                direction = SharedSelectionViewModel.Direction.PREVIOUS,
                                availableSuwar = availableSuwar
                            )
                        }
                )



                Icon(
                    painter = if (isPlaying) pauseIcon else playIcon,
                    contentDescription = if (isPlaying) "Pause" else "Play",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(48.dp)
                        .clickable {
                            if (isPlaying) exoPlayer.pause() else exoPlayer.play()
                            isPlaying = !isPlaying
                        }
                )



                Icon(
                    painter = painterResource(R.drawable.ic_next),
                    contentDescription = "Previous Surah",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable {
                            sharedViewModel.selectPreviousOrNextSuwar(
                                direction = SharedSelectionViewModel.Direction.NEXT,
                                availableSuwar = availableSuwar
                            )
                        }
                )

                Icon(
                    painter = painterResource(R.drawable.ic_loop),
                    contentDescription = "Previous Surah",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable {

                        }
                )
            }
        }
    }
}

@Composable
fun rememberExoPlayer(context: Context, url: String): ExoPlayer {
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri(url)
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
            Log.d("ExoPlayer", "Released on dispose")
        }
    }

    return exoPlayer
}

fun formatTime(ms: Long): String {
    val totalSeconds = ms / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format("%02d:%02d", minutes, seconds)
}
