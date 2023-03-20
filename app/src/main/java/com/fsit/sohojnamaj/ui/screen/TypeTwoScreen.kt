package com.fsit.sohojnamaj.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fsit.sohojnamaj.model.dua.TypeTwoItem
import com.fsit.sohojnamaj.model.dua.sampleTypeTwo
import com.fsit.sohojnamaj.ui.theme.kalPurush
import com.fsit.sohojnamaj.ui.viewModel.TypeTwoViewModel

@Composable
fun TypeTwoScreenRoute(
    onBackClick: () -> Unit,
    viewModel: TypeTwoViewModel = hiltViewModel(),
)
{
    val typeTwoItem by viewModel.typeTwoList.collectAsStateWithLifecycle()
    val title = viewModel.title
    TypeTwoScreen(title=title,typeTwoItem =typeTwoItem,onBackClick=onBackClick)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TypeTwoScreen(typeTwoItem: List<TypeTwoItem>, title: String, onBackClick: () -> Unit) {
    Scaffold(
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

        LazyColumn(modifier = Modifier.padding(it)) {
            items(typeTwoItem) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp, bottom = 5.dp)
                ) {
                    if(it.other?.isNotEmpty()==true){
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
                        modifier = Modifier.padding(top=8.dp)
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
                        modifier = Modifier.padding(top=8.dp)

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
                        modifier = Modifier.padding(top=8.dp)

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
                        border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary),
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

    }
}


@Preview
@Composable
fun PreviewTypeTwoScreen() {
    TypeTwoScreen(sampleTypeTwo, "title") {}
}