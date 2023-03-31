package com.fsit.sohojnamaj.ui.screen

import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fsit.sohojnamaj.R
import com.fsit.sohojnamaj.model.SuraDetails
import com.fsit.sohojnamaj.model.sampleSuraDetails
import com.fsit.sohojnamaj.ui.theme.kalPurush
import com.fsit.sohojnamaj.ui.viewModel.SuraDetailsViewModel
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.tanodxyz.gdownload.DownloadInfo
import com.tanodxyz.gdownload.DownloadProgressListener
import com.tanodxyz.gdownload.GDownload
import kotlinx.coroutines.delay
import java.io.File
import java.util.*
import kotlin.time.Duration.Companion.seconds


@Composable
fun AyatScreenRoute(
    viewModel: SuraDetailsViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    val suraDetails by viewModel.suraDetails.collectAsStateWithLifecycle()
    val title:String? = viewModel.title
    val id= viewModel.id
    SuraDetailsScreen(
        id=id,
        title =title,
        suraDetails=suraDetails,
        onBackClick=onBackClick
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuraDetailsScreen(
    onBackClick: () -> Unit,
    suraDetails: List<SuraDetails>,
    title: String?,
    id: Int,
) {
    val context= LocalView.current.context

    val formatted = "${String.format(Locale.ENGLISH,"%03d", id)}.mp3"
    val file= if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        File(context.dataDir,formatted)
    } else {
        File(context.cacheDir,formatted)
    }
    var logo by remember{ mutableStateOf(Icons.Default.PlayArrow) }
    var isReady by remember{ mutableStateOf(false) }
    var isShowFAB by remember{ mutableStateOf(true) }


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





    // Safely update the current lambdas when a new one is provided
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        // Create an observer that triggers our remembered callbacks
        // for lifecycle events
        val observer = LifecycleEventObserver { _, event ->
            when (event) {




                Lifecycle.Event.ON_PAUSE -> {
                  player.pause()



                }

                Lifecycle.Event.ON_DESTROY-> {
                    Log.i("123321", "SuraDetailsScreen: destroy")
                }
                else -> {}
            }
        }

        // Add the observer to the lifecycle
        lifecycleOwner.lifecycle.addObserver(observer)

        // When the effect leaves the Composition, remove the observer
        onDispose {

            //mediaPlayer.pause()
            lifecycleOwner.lifecycle.removeObserver(observer)

        }
    }



    var showDialog by remember {
        mutableStateOf(false)
    }
    if (showDialog){

        var progress by remember { mutableStateOf("connecting...") }
        var percent by remember { mutableStateOf(0f)}

       val v= GDownload.singleDownload(context){
            url="https://download.quranicaudio.com/quran/abdullaah_basfar/$formatted"
            name=file.absolutePath

            downloadProgressListener = object : DownloadProgressListener {

                override fun onConnectionEstablished(downloadInfo: DownloadInfo?) {}

                override fun onDownloadProgress(downloadInfo: DownloadInfo?) {
                  downloadInfo?.let {
                    percent=it.progress.toFloat()
                      progress="${android.text.format.Formatter.formatShortFileSize(context,it.downloadedContentLengthBytes)}/${android.text.format.Formatter.formatShortFileSize(context,it.contentLengthBytes)}"
                  }
                }

                override fun onDownloadFailed(downloadInfo: DownloadInfo, ex: String) {
                    Log.i("123321", "onDownloadFailed: $ex")
                }

                override fun onDownloadSuccess(downloadInfo: DownloadInfo) {
                    Log.i("123321", "onDownloadSuccess: ${downloadInfo.filePath}")
                    showDialog=false
                   isReady=true


                }

                override fun onDownloadIsMultiConnection(
                    downloadInfo: DownloadInfo,
                    multiConnection: Boolean,
                ) {}

                override fun onPause(downloadInfo: DownloadInfo, paused: Boolean, reason: String) {}

                override fun onRestart(
                    downloadInfo: DownloadInfo,
                    restarted: Boolean,
                    reason: String,
                ) {}

                override fun onResume(downloadInfo: DownloadInfo, resume: Boolean, reason: String) {}

                override fun onStop(downloadInfo: DownloadInfo, stopped: Boolean, reason: String) {}
            }
        }


        AlertDialog(onDismissRequest = {},

            title = {
                    Text(text = "সূরা $title ডাউনলোড হচ্ছে")
            },
            text = {
               Column(modifier = Modifier.fillMaxWidth()) {
                   Log.i("123321", "SuraDetailsScreen: $progress")
                   LinearProgressIndicator(percent/100)
                   Text(text =progress,modifier = Modifier.padding(start = 12.dp), style = MaterialTheme.typography.bodyMedium)
               }
            },
            confirmButton = {
                Text(
                    modifier = Modifier
                        .clickable {
                            showDialog=false
                        },
                    text = "Cancel",
                    fontFamily = kalPurush,
                    style = MaterialTheme.typography.titleLarge
                )
            },

            dismissButton = {

            }
        )
    }


    Scaffold (
        floatingActionButton = {
                        if( isShowFAB) {
                            FloatingActionButton(onClick = {
                                    isShowFAB=false
                                if(file.exists())isReady=true


                                else showDialog=true
                            }) {
                                Icon(imageVector = logo, contentDescription =null )
                            }
                        }
        },
        topBar = {
            TopAppBar (
                title = { Text(text =title?: "আল-কুরআন") },
                navigationIcon = {
                    IconButton(onClick = { onBackClick()}) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ){

        var currentValue by remember { mutableStateOf(0L) }



        Column(modifier = Modifier.padding(it)) {
            LazyColumn(Modifier.weight(1f)){
                items(suraDetails){ sura ->
                    Card(
                        onClick = {},
                        modifier = Modifier
                            .padding(top = 8.dp, start = 12.dp, end = 12.dp, bottom = 4.dp)
                            .fillMaxWidth()
                            .padding(),
                        border = BorderStroke(1.dp, color = Color(229, 229, 229)),
                        colors = CardDefaults.cardColors(

                        )

                    ) {
                        Row(
                            modifier = Modifier
                                .padding(top = 8.dp, start = 12.dp, end = 12.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                modifier = Modifier

                                    .background(
                                        color = Color(54, 168, 160),
                                        shape = RoundedCornerShape(25)
                                    )
                                    .padding(
                                        start = 5.dp, end = 5.dp, top = 1.dp, bottom = 1.dp
                                    ),
                                text = "${sura.ayatNumber}",
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center,
                                color = Color.White
                            )
                            IconButton(onClick = { /*TODO*/ }) {
                               // Icon(imageVector = Icons.Default.MoreHoriz, contentDescription = null)
                            }
                        }


                        Text(
                            modifier= Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            text = sura.arabic,
                            textAlign = TextAlign.End,
                            fontSize = 24.sp
                        )

                        Divider( color = Color(229, 229, 229))


                        Text(
                            modifier=Modifier.padding(16.dp),
                            text = sura.ayat
                        )


                        Divider( color = Color(229, 229, 229))


                        Text(
                            modifier=Modifier.padding(16.dp),
                            text = sura.text
                        )


                    }
                }
            }
            AnimatedVisibility(visible = isReady) {
           Card(
               modifier = Modifier
                   .fillMaxWidth()
                   .padding(5.dp)
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




                       AndroidView(

                           factory = { context ->

                               val view = LayoutInflater.from(context)
                                   .inflate(R.layout.custom_exo, null, false)

                               val playerView = view.findViewById<StyledPlayerView>(R.id.video_view)
                               val imageViewArtwork =
                                   playerView.findViewById<ImageView>(com.google.android.exoplayer2.ui.R.id.exo_artwork)
                               imageViewArtwork.setImageResource(R.drawable.transparent_bg)

                               playerView.player = player
                               player.playWhenReady = isReady

                               // do whatever you want...
                               view // return the view
                           },
                           update = { view ->

                               val playerView = view.findViewById<StyledPlayerView>(R.id.video_view)
                               val controller=playerView.findViewById<LinearLayout>(R.id.root)
                               controller.post {
                                   Log.i("123321", "TypeTwoScreen: controller height: " + controller.height)

                                   playerView.layoutParams=
                                       LinearLayout.LayoutParams(controller.width, controller.height)
                                   //  height=controller.height
                                   //width=controller.width
                               }



                               // Update the view
                           }
                       )}
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
fun AyatScreenPreview() {
    SuraDetailsScreen(
        onBackClick={}, sampleSuraDetails, "title", 0,

    )
}