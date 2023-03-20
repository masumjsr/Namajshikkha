package com.fsit.sohojnamaj.ui.screen

import android.media.MediaPlayer
import android.os.Build
import android.util.Log
import android.widget.Toast
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fsit.sohojnamaj.model.SuraDetails
import com.fsit.sohojnamaj.model.sampleSuraDetails
import com.fsit.sohojnamaj.ui.theme.kalPurush
import com.fsit.sohojnamaj.ui.util.ComposableLifecycle
import com.fsit.sohojnamaj.ui.viewModel.SuraDetailsViewModel
import com.tanodxyz.gdownload.DownloadInfo
import com.tanodxyz.gdownload.DownloadProgressListener
import com.tanodxyz.gdownload.GDownload
import java.io.File
import java.text.DecimalFormat
import java.util.*

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

    var mediaPlayer:MediaPlayer? by remember{ mutableStateOf( null)}
    if(mediaPlayer==null){
        mediaPlayer= MediaPlayer()
        mediaPlayer?.setDataSource(file.absolutePath)
        mediaPlayer?.setOnCompletionListener {
            logo= Icons.Default.PlayArrow
        }
    }




    // Safely update the current lambdas when a new one is provided
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current



  //  val currentOnResume by rememberUpdatedState(onResume)
    //val currentOnPause by rememberUpdatedState(onPause)

    // If `lifecycleOwner` changes, dispose and reset the effect
    DisposableEffect(lifecycleOwner) {
        // Create an observer that triggers our remembered callbacks
        // for lifecycle events
        val observer = LifecycleEventObserver { _, event ->
            when (event) {




                Lifecycle.Event.ON_PAUSE -> {
                    Log.i("123321", "SuraDetailsScreen: pause")

                    if(mediaPlayer?.isPlaying==true){
                      try {
                          logo= Icons.Default.PlayArrow
                          mediaPlayer?.pause()
                      } catch (e: Exception) {
                          Log.i("123321", "SuraDetailsScreen: ${e.message}")
                      }
                  }

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

        GDownload.singleDownload(context){
            url="https://download.quranicaudio.com/quran/abdullaah_basfar/$formatted"
            name=file.absolutePath

            downloadProgressListener = object : DownloadProgressListener {

                override fun onConnectionEstablished(downloadInfo: DownloadInfo?) {}

                override fun onDownloadProgress(downloadInfo: DownloadInfo?) {
                   progress=DecimalFormat("##.##").format(downloadInfo?.progress)
                }

                override fun onDownloadFailed(downloadInfo: DownloadInfo, ex: String) {
                    Log.i("123321", "onDownloadFailed: $ex")
                }

                override fun onDownloadSuccess(downloadInfo: DownloadInfo) {
                    Log.i("123321", "onDownloadSuccess: ${downloadInfo.filePath}")
                    showDialog=false
                    logo=Icons.Default.Pause
                    mediaPlayer?.prepare()
                    mediaPlayer?.start()
                }

                override fun onDownloadIsMultiConnection(
                    downloadInfo: DownloadInfo,
                    multiConnection: Boolean
                ) {}

                override fun onPause(downloadInfo: DownloadInfo, paused: Boolean, reason: String) {}

                override fun onRestart(
                    downloadInfo: DownloadInfo,
                    restarted: Boolean,
                    reason: String
                ) {}

                override fun onResume(downloadInfo: DownloadInfo, resume: Boolean, reason: String) {}

                override fun onStop(downloadInfo: DownloadInfo, stopped: Boolean, reason: String) {}
            }
        }


        AlertDialog(onDismissRequest = {},

            title = {
                    Text(text = "Downloading $title")
            },
            text = {
               Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                   CircularProgressIndicator()
                   Text(text =progress,modifier = Modifier.padding(start = 12.dp), style = MaterialTheme.typography.titleLarge)
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
                           FloatingActionButton(onClick = {

                               if(file.exists())
                               {
                                   if(mediaPlayer?.isPlaying==true){
                                       logo=Icons.Default.PlayArrow
                                       mediaPlayer?.pause()
                                   }
                                   else {
                                       logo=Icons.Default.Pause
                                       try {
                                           mediaPlayer?.prepare()
                                           mediaPlayer?.start()
                                       } catch (e: Exception) {
                                           mediaPlayer?.start()

                                       }
                                   }
                               }
                               else showDialog=true
                           }) {
                               Icon(imageVector = logo, contentDescription =null )
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
        Column(modifier = Modifier.padding(it)) {
            LazyColumn(){
                items(suraDetails){ sura ->
                    Card(
                        onClick = {},
                        modifier = Modifier
                            .padding(top = 8.dp, start = 12.dp, end = 12.dp, bottom = 4.dp)
                            .fillMaxWidth()
                            .padding(),
                        border = BorderStroke(1.dp, color = Color(229, 229, 229)),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
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
                                Icon(imageVector = Icons.Default.MoreHoriz, contentDescription = null)
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

            }
        }

    }



@Preview
@Composable
fun AyatScreenPreview() {
    SuraDetailsScreen(
        onBackClick={}, sampleSuraDetails, "title", 0,

    )
}