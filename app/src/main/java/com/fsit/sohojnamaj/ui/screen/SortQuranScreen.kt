package com.fsit.sohojnamaj.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.fsit.sohojnamaj.model.Sura
import com.fsit.sohojnamaj.model.sampleSura
import com.fsit.sohojnamaj.ui.viewModel.SortSuraViewModel
import com.fsit.sohojnamaj.ui.viewModel.SuraViewModel
import com.fsit.sohojnamaj.util.calender.bangla.translateNumbersToBangla

@Composable
fun SortQuranScreenRoute(
    viewModel: SortSuraViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onSuraClick: (Int,String) -> Unit
) {
    val sura by viewModel.suraList.collectAsStateWithLifecycle()
SortQuranScreen(
    sura=sura,
    onSuraClick=onSuraClick,
    onBackClick=onBackClick)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortQuranScreen(onBackClick: () -> Unit, sura: List<Sura>, onSuraClick: (Int,String) -> Unit) {
    Scaffold (
        topBar = {
            TopAppBar (
                title = { Text(text = "প্রয়োজনীয় ছোট সূরা")},
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
                itemsIndexed(sura){index, sura ->
                    OutlinedCard(
                        onClick = {onSuraClick.invoke(sura.sura_no,sura.sura_name)},
                        modifier = Modifier
                            .padding(top = 8.dp, start = 12.dp, end = 12.dp)
                            .fillMaxWidth()
                            .padding(),
                        border = BorderStroke(1.dp, color = Color(54, 168, 160))

                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp).fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    modifier = Modifier

                                        .background(
                                            color = Color(54, 168, 160),
                                            shape = RoundedCornerShape(25)
                                        )
                                        .padding(
                                            start = 5.dp, end = 5.dp,top=1.dp, bottom = 1.dp
                                        ),
                                    text = "${index+1}",
                                    fontSize = 10.sp,
                                    textAlign = TextAlign.Center,
                                    color = Color.White
                                )
                                Column (

                                    modifier = Modifier.padding(start = 8.dp, end = 5.dp),
                                        ){
                                    Text(
                                        text = sura.sura_name,
                                        modifier = Modifier.padding(bottom = 5.dp)
                                    )
                                    Text(text = sura.sura_mean, style = MaterialTheme.typography.bodySmall)
                                }
                            }


                                Column (

                                    modifier = Modifier.padding(start = 8.dp, end = 5.dp),
                                        ){
                                    Text(
                                        text = sura.sura_discended,
                                        modifier = Modifier.padding(bottom = 5.dp).fillMaxWidth(),
                                         style = MaterialTheme.typography.bodySmall,
                                        textAlign = TextAlign.End
                                    )
                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = sura.total_ayat, style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.End)
                                }
                            }
                        }
                    }
                }
            }
        }
        


}

@Preview
@Composable
fun SortQuranScreenPreview() {
    SortQuranScreen(onBackClick={}, sura = sampleSura, onSuraClick = {_,_->})
}