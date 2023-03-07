package com.fsit.sohojnamaj.ui.screen

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fsit.sohojnamaj.R
import com.fsit.sohojnamaj.model.ForbiddenTime
import com.fsit.sohojnamaj.model.Prayer
import com.fsit.sohojnamaj.model.PrayerRange
import com.fsit.sohojnamaj.ui.util.PulsatingCircles
import com.fsit.sohojnamaj.ui.viewModel.HomeViewModel
import com.fsit.sohojnamaj.util.calender.bangla.Bongabdo
import com.fsit.sohojnamaj.util.calender.primecalendar.civil.CivilCalendar
import com.fsit.sohojnamaj.util.calender.primecalendar.hijri.HijriCalendar
import java.util.*
import java.util.logging.Handler

@Composable
fun HomeScreenRoute(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val current by viewModel.currentPrayer.collectAsStateWithLifecycle()
    val context= LocalView.current.context
    android.os.Handler().postDelayed(
        {
            viewModel.setupAlerm(context)
        },5000
    )
 HomeScreen(
     current =current
 )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(current: Prayer?) {
    Scaffold(
        topBar = {
            TopAppBar (
                title = {
                    Text(
                        text = "নামাজের সময়"
                    )
                        },
                actions = {
                    Icon(
                        modifier =Modifier
                            .padding(5.dp),
                        imageVector =Icons.Default.Settings,
                        contentDescription ="Settings")

                }
            )
        }
    ) {paddingValues->
        val context = LocalView.current.context




        Column(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding(), start = 16.dp, end = 16.dp)
                .fillMaxWidth()
        ){
            Log.i("123321", "HomeScreen: is forbidden= ${current?.forbiddenRange}")

            DateSection()
            CurrentWaqt(current)

            NextWaqt(current)
            Sahari(current)
            Forbidden(current)

            }
            }


        }

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun Forbidden(current: Prayer?) {
    OutlinedCard(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .padding(top = 12.dp)
            .fillMaxWidth(),
        border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.error)
    ) {
        Column {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                text = "আজকের সম্ভাব্য নিষিদ্ধ সময়সমূহ",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall
            )
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            LazyColumn(
                modifier = Modifier
                    .padding(10.dp)
            ) {
                items(current?.forbiddenTime ?: emptyList()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,

                        ) {
                        Text(
                            text = it.name,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = it.time,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                }
            }
        }
    }
}

@Composable
private fun Sahari(current: Prayer?) {
    ElevatedCard(
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color(99, 115, 128),
            contentColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (current?.isIfterOver == true) {
                Text(
                    modifier = Modifier.padding(bottom = 8.dp),
                    text = "আগামীকালের সময়সুচী",
                    style = MaterialTheme.typography.titleSmall
                )
            }

            LazyVerticalGrid(columns = GridCells.Fixed(3)) {
                item { Text(text = "সাহারী শেষ", style = MaterialTheme.typography.bodySmall) }
                item {
                    Text(
                        text = current?.nextSahari ?: "-",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                item {
                    Text(
                        text = "পরবর্তী ${if (current?.isIfterOver == true) "সাহারি" else "ইফতার"}",
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center
                    )
                }
                item { Text(text = "ইফতার", style = MaterialTheme.typography.bodySmall) }
                item { Text(text = "6:03 PM", style = MaterialTheme.typography.bodyMedium) }
                item {
                    Text(
                        text = current?.nextTimeLeft ?: "-",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun NextWaqt(current: Prayer?) {
    OutlinedCard(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .padding(top = 12.dp)
            .fillMaxWidth(),
        border = BorderStroke(1.dp, color = Color(54, 168, 160))
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
        ) {
            Text(
                text = "পরবর্তী নামাজ",
                style = MaterialTheme.typography.bodySmall
            )
            Row() {
                Text(
                    modifier = Modifier,
                    text = current?.next ?: "-",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = current?.nextText ?: "-",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

        }
    }
}

@Composable
private fun CurrentWaqt(current: Prayer?) {
    ElevatedCard(
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(
            containerColor = if (current?.forbiddenRange == true) MaterialTheme.colorScheme.error else Color(
                54,
                168,
                160
            ),
            contentColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = if (current?.forbiddenRange == true) "এখন চলছে" else "বর্তমান ওয়াক্ত",
                style = MaterialTheme.typography.titleSmall
            )
            Row(
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically

            ) {
                PulsatingCircles()
                Text(
                    modifier = Modifier
                        .padding(start = 10.dp),
                    text = current?.name ?: "-",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = current?.text ?: "-",
                    style = MaterialTheme.typography.titleMedium
                )
            }


            Text(
                text = "সময় বাকিঃ ${current?.timeLeft}",
                style = MaterialTheme.typography.titleSmall
            )


            LinearProgressIndicator(
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 10.dp)
                    .fillMaxWidth(),
                progress = current?.progress ?: 0f,
                color = Color.White,
                trackColor = Color.White.copy(0.3f)
            )
        }
    }
}


@Composable
private fun DateSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val calendar = HijriCalendar()
        val englishCalendar = CivilCalendar(locale = Locale("bn"))
        Column() {
            Text(
                text = calendar.longDateString,
                fontSize = 18.sp
            )
            Row() {


                Text(
                    text = englishCalendar.longDateString,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                    style = MaterialTheme.typography.bodySmall,
                            text = "|"
                )
                Text(text = Bongabdo().now(locale = Locale("bn")),
                        style = MaterialTheme.typography.bodySmall
                )

            }
        }
        OutlinedButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Default.LocationOn, contentDescription = "Location On")
            Text(text = "Dhaka")
        }
    }
}

@Preview(locale = "bn")
@Composable

fun PreviewHomeScreen() {
    HomeScreen(
        current = Prayer(name = "মাগরিব", PrayerRange(0,1234,1234),text="7:10 PM - 5:50 AM")
    )

}

/*
@Preview(locale = "en")
@Composable

fun PreviewHomeScreenEn() {
    HomeScreen()

}*/
