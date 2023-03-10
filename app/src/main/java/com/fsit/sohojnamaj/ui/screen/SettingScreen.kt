package com.fsit.sohojnamaj.ui.screen

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.VolumeMute
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fsit.sohojnamaj.model.OffsetModel
import com.fsit.sohojnamaj.model.PrayerPreferenceModel
import com.fsit.sohojnamaj.model.SoundModel
import com.fsit.sohojnamaj.ui.util.LargeDropdownMenu
import com.fsit.sohojnamaj.ui.viewModel.SettingViewModel

@Composable
fun SettingScreenRoute(
    onBackClick: () -> Unit,
    viewModel: SettingViewModel = hiltViewModel(),
) {
    val settings by viewModel.settings.collectAsStateWithLifecycle()

    SettingScreen(
        onBackClick=onBackClick,
        setting=settings,
        updateOffset = viewModel::updateOffset,
        updateSound = viewModel::updateSound,
        updateMajhab = viewModel::updateMajhab,
        updateMethod = viewModel::updateMethod,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    onBackClick: () -> Unit, setting: PrayerPreferenceModel,
    updateOffset:(Int, Int)->Unit,
    updateSound:(Int, Int)->Unit,
    updateMethod:(Int)->Unit,
    updateMajhab:(Int)->Unit

) {
    Scaffold(
        topBar = {
            TopAppBar(
                title={ Text(text =  "Setting")},
                navigationIcon = {
                    IconButton(onClick = { onBackClick.invoke() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(top=paddingValues.calculateTopPadding(), start = 16.dp,end =16.dp)
        )    {
            item{


                Text("আযানের নটিফিকেশন সমন্বয়",style=MaterialTheme.typography.bodySmall)
                OutlinedCard(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .fillMaxWidth(),
                    border = BorderStroke(1.dp, color = Color(54, 168, 160))
                ) {
                    val soundModel= setting.soundModel

                    Log.i("123321", "SettingScreen: sound model is $soundModel")
                    
                    AzanItem("ফজর",soundModel.fajr) {
                        updateSound.invoke(0,it)
                    }
                    AzanItem("যোহর",soundModel.dhur) {
                        updateSound.invoke(1,it)
                    }
                    AzanItem("আসর",soundModel.asr) {
                        updateSound.invoke(2,it)
                    }
                    AzanItem("মাগরিব",soundModel.magrib) {
                        updateSound.invoke(3,it)
                    }
                    AzanItem("এশা",soundModel.isha) {
                        updateSound.invoke(4,it)
                    }
                }





                LargeDropdownMenu(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .fillMaxWidth(),
                    label = "সময় হিসাব পদ্ধতি",
                    items = listOf(
                        "ইউনিভার্সিটি অব ইসলামিক স্টাডিজ, করাচি",
                        "ইসলামিক সোসাইটি অফ নর্থ আমেরিকা",
                        "মুসলিম ওয়ার্ল্ড লীগ",
                        "উম্মুল কুরা বিশ্ববিদ্যালয় মক্কা",
                        "ইজিপ্সিয়ান জেনারেল অথরিটি অফ সার্ভে" ,
                        "ইনস্টিটিউট অফ জিওফিজিক্স, তেহরান ইউনিভার্সিটি"

                    ),
                    selectedIndex = setting.method,
                    onItemSelected = { index, _ -> updateMethod(index) },
                )
                LargeDropdownMenu(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .fillMaxWidth(),
                    label = "মাজহাব" ,
                    items = listOf(
                        "হানাফি",
                        "শাফেয়ী, হাম্বলী, মালিকী",

                    ),
                    selectedIndex = setting.majhab,
                    onItemSelected = { index, _ -> updateMajhab.invoke(index) },
                )
                TimeAdjustment(setting.offsetModel, updateOffset)

            }

        }


    }
}

@Composable
private fun TimeAdjustment(
    offset: OffsetModel,
    updateOffset: (Int, Int) -> Unit,
) {
    Text(
        modifier = Modifier
            .padding(top = 12.dp)
            .fillMaxWidth(),
        text="ওয়াক্তের সময় সমন্বয়",style=MaterialTheme.typography.bodySmall)
    OutlinedCard(
        modifier = Modifier
            .padding(top = 12.dp)
            .fillMaxWidth(),
        border = BorderStroke(1.dp, color = Color(54, 168, 160))
    ) {

        OffsetItem("ফজর", offset.fajr) {
            updateOffset.invoke(0, it)
        }
        OffsetItem("সূর্যোদয়", offset.sunrise) {
            updateOffset.invoke(1, it)
        }
        OffsetItem("যোহর", offset.dhur) {
            updateOffset.invoke(2, it)

        }
        OffsetItem("আসর", offset.asr) {
            updateOffset.invoke(3, it)

        }
        OffsetItem("সূর্যাস্ত", offset.sunset) {
            updateOffset.invoke(4, it)

        }
        OffsetItem("মাগরিব", offset.magrib) {
            updateOffset.invoke(5, it)

        }
        OffsetItem("এশা", offset.isha) {
            updateOffset.invoke(6, it)

        }
    }
}

@Composable
fun OffsetItem(name: String, minute: Int, onAdjusted: (Int) -> Unit) {


    Row(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(name)
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .clickable {
                        onAdjusted.invoke(minute - 1)
                    }
                    .size(25.dp)
                    .border(
                        BorderStroke(1.dp, color = Color(54, 168, 160)),
                        shape = RoundedCornerShape(25)
                    ),
                text = "-",
                style=MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
            Text(text = "   $minute মিনিট   ", textAlign = TextAlign.Center)
            Text(
                modifier = Modifier
                    .clickable {
                        onAdjusted.invoke(minute + 1)
                    }
                    .size(25.dp)
                    .border(
                        BorderStroke(1.dp, color = Color(54, 168, 160)),
                        shape = RoundedCornerShape(25)
                    ),
                text = "+",
                style=MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    }

}
@Composable
fun AzanItem(name: String, minute: Int, onAdjusted: (Int) -> Unit) {


    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(name)

            Icon(
                modifier = Modifier
                    .clickable {
                        onAdjusted.invoke(if (minute == 0) 1 else 0)
                    }

                    .size(32.dp)
                    .border(
                        BorderStroke(1.dp, color = Color(54, 168, 160)),
                        shape = RoundedCornerShape(25)
                    )
                    .padding(5.dp),
                imageVector = if(minute==0)Icons.Default.VolumeUp else Icons.Default.VolumeMute,
                contentDescription = "icon"
            )
        }


}
@Preview
@Composable
fun PreviewSettingScreen() {
    
    SettingScreen(onBackClick={}, setting = PrayerPreferenceModel(), updateOffset = {
        _,_->
    } ,
        { _, _ -> } ,{},{})
}