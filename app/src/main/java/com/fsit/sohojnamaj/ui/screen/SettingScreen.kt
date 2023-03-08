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
import com.fsit.sohojnamaj.ui.viewModel.SettingViewModel

@Composable
fun SettingScreenRoute(
    onBackClick: () -> Unit,
    viewModel: SettingViewModel = hiltViewModel(),
) {
    val offsets by viewModel.offset.collectAsStateWithLifecycle()
    Log.i("123321", "SettingScreen: flow offset =$offsets")

    SettingScreen(
        onBackClick=onBackClick,
        offset=offsets,
        updateOffset = viewModel::updateOffset
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(onBackClick: () -> Unit, offset: OffsetModel,updateOffset:(Int,Int)->Unit) {
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

                OutlinedCard(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .fillMaxWidth(),
                    border = BorderStroke(1.dp, color = Color(54, 168, 160))
                ) {
                    var fajrOffset by remember { mutableStateOf(offset.fajr) }




                    OffsetItem("ফজর",offset.fajr) {
                        fajrOffset=it
                        updateOffset.invoke(0,it)
                    }
                    OffsetItem("সূর্যোদয়",offset.sunrise) {
                        updateOffset.invoke(1,it)
                    }
                    OffsetItem("যোহর",offset.dhur) {
                        updateOffset.invoke(2,it)

                    }
                    OffsetItem("আসর",offset.asr) {
                        updateOffset.invoke(3,it)

                    }
                    OffsetItem("সূর্যাস্ত",offset.sunset) {
                        updateOffset.invoke(4,it)

                    }
                    OffsetItem("মাগরিব",offset.magrib) {
                        updateOffset.invoke(5,it)

                    }
                    OffsetItem("এশা",offset.isha) {
                        updateOffset.invoke(6,it)

                    }
                }
            }
        }

    }
}

@Composable
fun OffsetItem(name: String, minute: Int, onAdjusted: (Int) -> Unit) {
    Log.i("123321", "OffsetItem: input minute $minute")
    var currentOffset by remember {
        mutableStateOf(minute)
    }

    Row(
        modifier = Modifier
            .padding(16.dp)
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
                        currentOffset -= 1
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
                        currentOffset += 1
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

@Preview
@Composable
fun PreviewSettingScreen() {
    
    SettingScreen(onBackClick={}, offset = OffsetModel(), updateOffset = {
        _,_->
    } )
}
