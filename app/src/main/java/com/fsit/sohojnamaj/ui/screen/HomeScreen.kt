package com.fsit.sohojnamaj.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fsit.sohojnamaj.ui.viewModel.HomeViewModel
import com.fsit.sohojnamaj.util.calender.bangla.Bongabdo
import com.fsit.sohojnamaj.util.calender.primecalendar.civil.CivilCalendar
import com.fsit.sohojnamaj.util.calender.primecalendar.hijri.HijriCalendar
import java.util.*

@Composable
fun HomeScreenRoute() {
HomeScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
     viewModel: HomeViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar (
                title = { Text(text = "নামাজ শিক্ষা - বাংলাদেশ") },
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

            DateSection()
            ElevatedCard(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth(),
                colors = CardDefaults.elevatedCardColors(
                    containerColor =  Color(54, 168, 160),
                    contentColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text(text = "বর্তমান ওয়াক্ত",
                            style = MaterialTheme.typography.titleSmall)
                    Row(
                        modifier = Modifier
                            .padding(top =10.dp, bottom =10.dp),
                        verticalAlignment = Alignment.CenterVertically

                    ) {
                        Canvas(modifier = Modifier.size(10.dp), onDraw = {
                            drawCircle(color = Color.White)
                        })
                        Text(
                            modifier = Modifier
                                .padding(start = 10.dp),
                            text = "মাগরিব",
                            style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "6:03 pm - 7:17 pm",
                            style = MaterialTheme.typography.titleMedium)
                    }

                    Text(text = "সময় বাকিঃ ৪৪ মিনিট",
                        style = MaterialTheme.typography.titleSmall)

                    LinearProgressIndicator(
                        modifier = Modifier
                            .padding(top = 10.dp, bottom = 10.dp)
                            .fillMaxWidth(),
                        progress = 0.4f,
                        color = Color.White,
                        trackColor = Color.White.copy(0.3f)
                    )
                }
            }

            OutlinedCard(onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                border = BorderStroke(1.dp,color  = Color(54, 168, 160))
                ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text(text = "পরবর্তী নামাজ",
                        style = MaterialTheme.typography.titleSmall)
                    Row(){
                        Text(
                            modifier = Modifier,
                            text = "এশা",
                            style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "7:18 pm - 11:18pm",
                            style = MaterialTheme.typography.titleMedium)
                    }

                }
            }
            ElevatedCard(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth(),
                colors = CardDefaults.elevatedCardColors(
                    containerColor =  Color(99, 115, 128),
                    contentColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        modifier = Modifier.padding(bottom = 8.dp),
                        text = "আগামীকালের সময়সুচী",
                        style = MaterialTheme.typography.titleSmall)
                    LazyVerticalGrid(columns = GridCells.Fixed(3) ){
                        item { Text(text = "সাহারী শেষ" , style =  MaterialTheme.typography.bodySmall) }
                        item { Text(text = "5:15 AM" ,style =  MaterialTheme.typography.bodyMedium) }
                        item { Text(text = "পরবর্তী সাহরী",style= MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center) }
                        item { Text(text = "ইফতার", style =  MaterialTheme.typography.bodySmall) }
                        item { Text(text = "6:03 PM" ,style = MaterialTheme.typography.bodyMedium) }
                        item { Text(text = "১০ ঘন্টা ৪২ মিনিট", style =  MaterialTheme.typography.bodySmall) }
                    }
                }
            }
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
        val calendar = HijriCalendar(locale = Locale("bn"))
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
    HomeScreen()

}

/*
@Preview(locale = "en")
@Composable

fun PreviewHomeScreenEn() {
    HomeScreen()

}*/
