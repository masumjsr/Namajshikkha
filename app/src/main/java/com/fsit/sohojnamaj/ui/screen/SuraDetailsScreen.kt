package com.fsit.sohojnamaj.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fsit.sohojnamaj.model.SuraDetails
import com.fsit.sohojnamaj.model.sampleSuraDetails
import com.fsit.sohojnamaj.ui.viewModel.SuraDetailsViewModel

@Composable
fun AyatScreenRoute(
    viewModel: SuraDetailsViewModel= hiltViewModel(),
    onBackClick: () -> Unit) {
    val suraDetails by viewModel.suraDetails.collectAsStateWithLifecycle()
    val title:String? = viewModel.title
    SuraDetailsScreen(
        title =title,
        suraDetails=suraDetails,
        onBackClick=onBackClick
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuraDetailsScreen(onBackClick: () -> Unit, suraDetails: List<SuraDetails>, title: String?) {
    Scaffold (
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
                            modifier = Modifier.padding(top=8.dp, start = 12.dp,end=12.dp).fillMaxWidth(),
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
    SuraDetailsScreen(onBackClick={}, sampleSuraDetails, "title")
}