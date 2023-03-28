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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.unit.sp
import com.fsit.sohojnamaj.model.Name
import com.fsit.sohojnamaj.model.dua.AlifBaa
import com.fsit.sohojnamaj.model.dua.AlifBaaList
import com.fsit.sohojnamaj.model.sampleName
import com.fsit.sohojnamaj.ui.theme.kalPurush

@Composable
fun AlifBaScreenRoute(onBackClick: () -> Unit) {
AlifBaScreen(onBackClick=onBackClick)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlifBaScreen(onBackClick: () -> Unit) {


    var selectedItem by remember { mutableStateOf(AlifBaaList[0]) }

    var showDialog by remember {
        mutableStateOf(false)
    }
    if (showDialog){
        AlertDialog(onDismissRequest = {},

            title = {
            },
            text = {
                DialogLayoutAlif(selectedItem)
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

      Scaffold (
                  topBar = { TopAppBar(
                      navigationIcon = {
                                       IconButton(onClick = {onBackClick.invoke() }) {
                                           Icon(
                                               imageVector = Icons.Default.ArrowBack,
                                               contentDescription = null
                                           )
                                       }
                      },
                      title = { Text("আরবি হরফ") },

                  ) }
                      ){
          LazyVerticalGrid(
              modifier=Modifier.padding(it),
              columns = GridCells.Fixed(4)   ){
              items(AlifBaaList){
                  Card(onClick = {
                      selectedItem=it
                      showDialog=true
                  },
                      modifier=Modifier.padding(5.dp)
                  ) {
                   Column(modifier = Modifier.fillMaxWidth(),
                   horizontalAlignment = Alignment.CenterHorizontally
                       ){
                       Text(
                           it.alphabet,
                           fontSize = 50.sp
                       )
                       Text(it.bangla, fontSize = 16.sp, fontFamily = kalPurush)
                   }


                  }
              }
          }
      }


}

@Composable
fun DialogLayoutAlif(item: AlifBaa
                     = AlifBaaList[
                             0
                     ]) {
    var logo by remember{ mutableStateOf(Icons.Default.PlayCircle) }
    val mediaPlayer= MediaPlayer.create(LocalView.current.context,item.audioId)
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

            Text(
                item.alphabet,
                fontSize = 50.sp,
                modifier = Modifier.padding(start=5.dp),
                color = Color.White
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
            val context= LocalView.current.context
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
                color= Color.White,
                fontFamily = kalPurush,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
            Text(
                modifier= Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                text = item.bangla,
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
                color= Color.White,
                fontFamily = kalPurush,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
            Text(
                modifier= Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                text = item.uccaron,
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
                color= Color.White,
                fontFamily = kalPurush,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
            Text(
                modifier= Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                text = item.source,
                fontFamily = kalPurush,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }

}

@Preview
@Composable
fun PreviewAlifBaScreen() {

    AlifBaScreen({})
}

@Preview
@Composable
fun PreviewAlifBaScreenDialog() {

   DialogLayoutAlif()
}