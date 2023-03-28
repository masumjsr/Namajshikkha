package com.fsit.sohojnamaj.ui.screen

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fsit.sohojnamaj.model.Name
import com.fsit.sohojnamaj.model.sampleName
import com.fsit.sohojnamaj.ui.theme.kalPurush
import com.fsit.sohojnamaj.ui.viewModel.NameViewModel


@Composable
fun NameScreenRoute(
    onBackClick: () -> Unit,
    viewModel: NameViewModel = hiltViewModel(),
) {
    val names by viewModel.nameList.collectAsStateWithLifecycle()
    NameScreen(onBackClick=onBackClick,names=names)

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameScreen(names: List<Name>, onBackClick: () -> Unit) {

    var selectedItem by remember { mutableStateOf(sampleName[0]) }

    var showDialog by remember {
        mutableStateOf(false)
    }
    if (showDialog){
        AlertDialog(onDismissRequest = {},

            title = {
            },
            text = {
                DialogLayout(selectedItem)
            },
            confirmButton = {
                Text(
                    modifier = Modifier
                        .clickable {
                            showDialog=false
                        },
                    text = "Ok",
                    fontFamily = kalPurush)
            },

            dismissButton = {

            }
        )
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "আল্লাহ্‌র নাম সমূহ") }
            )
        }
    ) {
        LazyColumn(modifier = Modifier.padding(it) ){
            items(names){

                ElevatedCard(
                    modifier = Modifier.padding(5.dp),
                    onClick = {
                        selectedItem=it
                        showDialog=true
                    }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp, bottom = 5.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column() {
                            Text(text = it.arabic, fontFamily = kalPurush, style = MaterialTheme.typography.titleMedium)
                            Text(text = it.meaning, fontFamily = kalPurush,style = MaterialTheme.typography.bodyMedium)
                        }
                        Row() {
                            Icon(
                                modifier = Modifier.size(30.dp),
                                painter = painterResource(id = it.logo)
                                , contentDescription = null)
                        }
                    }
                }
            }
        }

    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewNameScreen() {
    // NameScreen(sampleName, {})
      DialogLayout()
}

@Composable
fun DialogLayout(item:Name= sampleName[0]) {
    var logo by remember{ mutableStateOf(Icons.Default.PlayCircle) }
    val mediaPlayer= MediaPlayer.create(LocalView.current.context,item.audio)
    mediaPlayer.setOnCompletionListener {
    logo= Icons.Default.PlayCircle
    }

        //mediaPlayer.start()



    Column (
        modifier = Modifier
            ){
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.secondary)
                .fillMaxWidth()
            ,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                modifier = Modifier.size(80.dp),
                painter = painterResource(id = item.logo), contentDescription = null,
                tint = Color.White
            )

           IconButton(onClick = {
               if(!mediaPlayer.isPlaying) mediaPlayer.start()
               logo=Icons.Default.PauseCircle
           }) {
               Icon(
                   modifier =
                   Modifier
                       .size(50.dp),
                   imageVector = logo, contentDescription = null,
                   tint = Color.White
               )
           }
            val text= "আল্লাহ্‌র ৯৯ নাম ও বাংলা অর্থ-\nবাংলা অর্থ-\n উচ্চারণঃ ${item.arabic}\n বাংলা অর্থঃ ${item.meaning}\n ফজিলতঃ ${item.fajoliot}"
            val context= LocalView.current.context
            Column() {
                IconButton(onClick = {
                    val intent2: Intent = Intent()
                    intent2.action=Intent.ACTION_SEND
                    intent2.type = "text/plain"
                    intent2.putExtra(Intent.EXTRA_TEXT, text)
                    context.startActivity(Intent.createChooser(intent2, "Share via"))
                }) {
                    Icon(imageVector = Icons.Default.Share, contentDescription = null, tint = Color.White)
                }
                IconButton(onClick = {
                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText("label",text)
                    clipboard.setPrimaryClip(clip)

                    Toast.makeText(context, "Text Copied Successfully", Toast.LENGTH_SHORT).show()
                }) {
                    Icon(imageVector = Icons.Default.CopyAll, contentDescription = null, tint = Color.White)

                }
            }
        }
       ElevatedCard(
           modifier = Modifier.padding(top=10.dp)
       ) {
           Text(
               modifier= Modifier
                   .fillMaxWidth()
                   .background(MaterialTheme.colorScheme.primary)
                   .padding(5.dp),
               text = "উচ্চারণ",
               color=Color.White,
               fontFamily = kalPurush,
               style = MaterialTheme.typography.titleLarge,
               textAlign = TextAlign.Center
           )
           Text(
               modifier= Modifier
                   .fillMaxWidth()
                   .padding(5.dp),
               text = item.arabic,
               fontFamily = kalPurush,
               style = MaterialTheme.typography.titleLarge,
               textAlign = TextAlign.Center
           )
       }

        ElevatedCard(
            modifier = Modifier.padding(5.dp)
        ) {
            Text(
                modifier= Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(5.dp),
                text = "বাংলা অর্থ",
                color=Color.White,
                fontFamily = kalPurush,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
            Text(
                modifier= Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                text = item.meaning,
                fontFamily = kalPurush,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
        }

        ElevatedCard(
            modifier = Modifier.padding(5.dp)
        ) {
            Text(
                modifier= Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(5.dp),
                text = "ফজিলত",
                color=Color.White,
                fontFamily = kalPurush,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
            Text(
                modifier= Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                text = item.fajoliot,
                fontFamily = kalPurush,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }

    }
