package com.fsit.sohojnamaj.ui.screen

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fsit.sohojnamaj.R
import com.fsit.sohojnamaj.model.dua.TypeTwoItem
import com.fsit.sohojnamaj.model.dua.sampleTypeTwo
import com.fsit.sohojnamaj.ui.theme.kalPurush
import com.fsit.sohojnamaj.ui.viewModel.TypeTwoViewModel
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.ui.StyledPlayerView
import java.io.File

@Composable
fun TypeTwoScreenRoute(
    onBackClick: () -> Unit,
    viewModel: TypeTwoViewModel = hiltViewModel(),
) {
    val typeTwoItem by viewModel.typeTwoList.collectAsStateWithLifecycle()
    val title = viewModel.title
    val id = viewModel.id
    if (id != -1) {
        TypeTwoScreen(id = id, title = title, typeTwoItem = typeTwoItem, onBackClick = onBackClick)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TypeTwoScreen(id:Int,typeTwoItem: List<TypeTwoItem>, title: String, onBackClick: () -> Unit) {
    val context= LocalView.current.context

    val file= if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        File(context.dataDir,"$id.mp3")
    } else {
        File(context.cacheDir,"$id.mp3")

    }
    Log.i("123321", "TypeTwoScreen: ${file.absolutePath}")

    var isReady by remember{ mutableStateOf(false) }
    var isShowFAB by remember{ mutableStateOf(file.exists()) }



    val  player = ExoPlayer.Builder(context).build()
    val audioAttributes = AudioAttributes.Builder()
        .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
        .setUsage(C.USAGE_MEDIA)
        .build()
    player.setAudioAttributes(audioAttributes,true)
    //   playerView.setPlayer(player)


    val mediaItem: MediaItem = MediaItem.fromUri(file.absolutePath)
    player.setMediaItem(mediaItem)
    player.prepare()
    player.playWhenReady = false
    Scaffold(
        floatingActionButton = {
            if( isShowFAB) {
                FloatingActionButton(onClick = {
                    isShowFAB=false
                   isReady=true

                }) {
                    Icon(imageVector = Icons.Default.PlayArrow, contentDescription =null )
                }
            }
        },
        topBar = {
            TopAppBar(


                navigationIcon = {
                    IconButton(onClick = {onBackClick.invoke() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                title = { Text(title, maxLines = 1, overflow = TextOverflow.Ellipsis) }
            ) }
    ) {

        Column(modifier = Modifier.padding(it)) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(typeTwoItem) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp, bottom = 5.dp)
                    ) {
                        if (it.other?.isNotEmpty() == true) {
                            Text(
                                text = it.other,
                                fontFamily = kalPurush,
                                style = MaterialTheme.typography.titleMedium,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth(),
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }

                        Text(
                            text = "আরবি উচ্চারণঃ",
                            fontFamily = kalPurush,
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = "بِسْمِ اللّهِ الرَّحْمـَنِ الرَّحِيمِ ",
                            fontFamily = kalPurush,
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedCard(
                            onClick = { /*TODO*/ },
                            modifier = Modifier
                                .padding(top = 12.dp)
                                .background(Color.White)
                                .fillMaxWidth(),
                            border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary),
                            colors = CardDefaults.outlinedCardColors(containerColor = Color.White)

                        ) {
                            it.arabic?.let { it1 ->
                                Text(
                                    text = it1,
                                    textAlign = TextAlign.End,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    style = MaterialTheme.typography.titleMedium

                                )
                            }

                        }

                        Text(
                            text = "বাংলা উচ্চারণঃ",
                            fontFamily = kalPurush,
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                        Text(
                            text = "বিসমিল্লাহির রাহমানির রাহিম ।",
                            fontFamily = kalPurush,
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth(),
                            color = MaterialTheme.colorScheme.secondary
                        )
                        OutlinedCard(
                            onClick = { /*TODO*/ },
                            modifier = Modifier
                                .padding(top = 12.dp)
                                .background(Color.White)
                                .fillMaxWidth(),
                            border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary),
                            colors = CardDefaults.outlinedCardColors(containerColor = Color.White)

                        ) {
                            it.bengali?.let { it1 ->
                                Text(
                                    text = it1,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    style = MaterialTheme.typography.titleMedium

                                )
                            }

                        }

                        Text(
                            text = "বাংলা অর্থঃ",
                            fontFamily = kalPurush,
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(top = 8.dp)

                        )
                        Text(
                            text = "শুরু করছি আল্লাহর নামে যিনি পরম করুণাময়,অতি দয়ালু।",
                            fontFamily = kalPurush,
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth(),
                            color = MaterialTheme.colorScheme.secondary
                        )
                        OutlinedCard(
                            onClick = { /*TODO*/ },
                            modifier = Modifier
                                .padding(top = 12.dp)
                                .background(Color.White)
                                .fillMaxWidth(),
                            border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary),
                            colors = CardDefaults.outlinedCardColors(containerColor = Color.White)
                        ) {
                            it.meaning?.let { it1 ->
                                Text(
                                    text = it1,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    style = MaterialTheme.typography.titleMedium

                                )
                            }

                        }


                        it.english?.let { it1 ->
                            Text(
                                text = "English Meaning:",
                                fontFamily = kalPurush,
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.padding(top = 8.dp)

                            )
                            Text(
                                text = "In the name of Allah, Most Gracious, Most Merciful.",
                                fontFamily = kalPurush,
                                style = MaterialTheme.typography.titleMedium,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth(),
                                color = MaterialTheme.colorScheme.secondary
                            )
                            OutlinedCard(
                                onClick = { /*TODO*/ },
                                modifier = Modifier
                                    .padding(top = 12.dp)
                                    .background(Color.White)
                                    .fillMaxWidth(),
                                border = BorderStroke(
                                    1.dp,
                                    color = MaterialTheme.colorScheme.primary
                                ),
                                colors = CardDefaults.outlinedCardColors(containerColor = Color.White)

                            ) {

                                Text(
                                    text = it1,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    style = MaterialTheme.typography.titleMedium

                                )
                            }

                        }


                    }

                }
            }
            AnimatedVisibility(visible = isReady) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),

                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Spacer(modifier = Modifier)
                            Text(text = title ?: "")
                            IconButton(onClick = {
                                player.pause()
                                isReady = false
                                isShowFAB = true
                            }) {
                                Icon(imageVector = Icons.Default.Close, contentDescription = null)
                            }
                        }


                        val backgroundColor=MaterialTheme.colorScheme.surfaceVariant.toArgb()

                        AndroidView(

                            factory = { context ->

                                val view = LayoutInflater.from(context)
                                    .inflate(R.layout.custom_exo, null, false)

                                val playerView = view.findViewById<StyledPlayerView>(R.id.video_view)
                                val controller=playerView.findViewById<LinearLayout>(R.id.root)
                                controller.setBackgroundColor(backgroundColor)


                                val imageViewArtwork =
                                    playerView.findViewById<ImageView>(com.google.android.exoplayer2.ui.R.id.exo_artwork)
                                imageViewArtwork.setImageResource(R.drawable.transparent_bg)

                                playerView.player = player
                                player.playWhenReady = isReady
                              //  playerView.layoutParams=LinearLayout.LayoutParams(controller.width,controller.height)

                                // do whatever you want...
                                view // return the view
                            },
                            update = { view ->
                                val playerView = view.findViewById<StyledPlayerView>(R.id.video_view)
                                val controller=playerView.findViewById<LinearLayout>(R.id.root)
                                controller.post {
                                    Log.i("123321", "TypeTwoScreen: controller height: " + controller.height)

                                    playerView.layoutParams=LinearLayout.LayoutParams(controller.width, controller.height)
                                  //  height=controller.height
                                    //width=controller.width
                                }

                                // Update the view
                            }
                        )
                    }
                    DisposableEffect(key1 = isReady) {

                        onDispose {
                            player.pause()
                        }
                    }

                }
            }  }

    }


}


@Preview
@Composable
fun PreviewTypeTwoScreen() {
    TypeTwoScreen(0,sampleTypeTwo, "title") {}
}