package com.fsit.sohojnamaj.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fsit.sohojnamaj.model.ZakatModel
import com.fsit.sohojnamaj.model.modelList
import com.fsit.sohojnamaj.ui.theme.kalPurush
import com.fsit.sohojnamaj.ui.viewModel.ZakatViewModel
import kotlinx.coroutines.flow.update
import java.util.*

@Composable
fun ZakatScreenRoute(
    onBackClick: () -> Unit,
    viewModel: ZakatViewModel = hiltViewModel(),

    ) {
    val list by viewModel.infoList.collectAsStateWithLifecycle()
    Log.i("123321", "ZakatScreenRoute: ")
    val goldPrice by viewModel.goldPrice.collectAsStateWithLifecycle()
   // val totalWealth by viewModel.totalWealth.collectAsStateWithLifecycle()

    ZakatScreen(
        onBackClick = onBackClick,
        list = modelList,
        goldPrice = goldPrice,
        totalWealth = 0.0,
        onUpdate = { viewModel.update(it) },
        onGoldPrice = {
            viewModel.updateGoldPrice(
                it
            )
        }

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ZakatScreen(
    onBackClick: () -> Unit,
    list: ArrayList<ZakatModel>,
    goldPrice: String,
    onUpdate: (ZakatModel) -> Unit,
    onGoldPrice: (String) -> Unit,
    totalWealth: Double,

    ) {

    var showDialog by remember {
        mutableStateOf(false)
    }
    var goldPrice by remember { mutableStateOf("1714.61") }
    if (showDialog) {
        AlertDialog(onDismissRequest = {},

            title = {
            },
            text = {
                Column {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.primary),
                        text = "দয়া করে ১ ভরি (তোলা) রুপার মুল্য লিখুন। নিসাব হিসাব এর জন্য এটি ব্যবহৃত হবে। নিইসাব হিসাব ব্যাতিত আপনার যাকাত হিসাব করা সম্ভব না",
                        color = Color.White,
                        fontFamily = kalPurush,
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )




                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        label = {
                            Text(
                                text = "রুপার মুল্য", fontFamily = kalPurush
                            )
                        },
                        value = goldPrice,
                        onValueChange = { text ->
                            goldPrice = if (text.isEmpty()) "" else {
                                text.toDouble()
                            }.toString()
                        },
                        suffix = { Text(text = "টাকা") }
                    )

                }
            },
            confirmButton = {
                Text(
                    modifier = Modifier
                        .clickable {
                            showDialog = false
                        },
                    text = "  সংরক্ষণ",
                    fontFamily = kalPurush
                )
            },

            dismissButton = {
                Text(
                    modifier = Modifier
                        .clickable {
                            showDialog = false
                        },
                    text = "বাতিল  ",
                    fontFamily = kalPurush
                )
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { onBackClick.invoke() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                title = { Text("যাকাত ক্যালকুলেটর") },

                )
        }
    ) {


        
        var total by remember{ mutableStateOf(0.0) }

        list.forEach {
            if(it.amount.isNotEmpty()){



                        if (it.isAdding)total+=it.amount.toDouble() else  total-=it.amount.toDouble()


            }
        }

        
        
        Column(
            modifier = Modifier.padding(it)
        ) {
            val imageList = remember{ mutableStateMapOf<Int, Double>()}
            Log.i("123321", "ZakatScreen: ${imageList.toMap()}")
            LazyColumn(modifier=Modifier.weight(1f)) {
                items(list) {
                    var value by remember { mutableStateOf(it.amount) }

                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        label = {
                            Text(
                                text = it.title, fontFamily = kalPurush
                            )
                        },
                        value = value,
                        onValueChange = { text ->
                            value = text
                            try {
                                imageList[it.id] = text.toDouble()*if(it.isAdding)1 else -1
                            } catch (e: Exception) {
                                imageList[it.id] = 0.0

                            }
                            // onUpdate.invoke(ZakatModel(it.id, it.title, value, it.isAdding))
                        },
                        suffix = { Text(text = "টাকা") }
                    )
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .clickable { showDialog = true }
                        .fillMaxWidth()
                        .padding(5.dp)
                        .background(MaterialTheme.colorScheme.primary),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = "যাকাতের নিসাব ",
                        color = Color.White,
                        fontFamily = kalPurush,
                        style = MaterialTheme.typography.titleLarge,
                    )
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = String.format("%.1f",goldPrice.toDouble()*52.50),
                        color = Color.White,
                        fontFamily = kalPurush,
                        style = MaterialTheme.typography.titleLarge,
                    )

                }
            }
            Row(
                modifier = Modifier
                    .clickable { showDialog = true }
                    .fillMaxWidth()
                    .padding(5.dp)
                    .background(MaterialTheme.colorScheme.primary),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "মোট সম্পদ",
                    color = Color.White,
                    fontFamily = kalPurush,
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = String.format("%.1f", imageList.values.sum()),
                    color = Color.White,
                    fontFamily = kalPurush,
                    style = MaterialTheme.typography.titleLarge,
                )


            }

            Row(
                modifier = Modifier
                    .clickable { showDialog = true }
                    .fillMaxWidth()
                    .padding(5.dp)
                    .background(MaterialTheme.colorScheme.primary),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "মোট যাকাত",
                    color = Color.White,
                    fontFamily = kalPurush,
                    style = MaterialTheme.typography.titleLarge,
                )
                val zakatAmount =(2.5/100.0) * imageList.values.sum()
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = String.format("%.1f",if(zakatAmount>goldPrice.toDouble())zakatAmount else 0.0),
                    color = Color.White,
                    fontFamily = kalPurush,
                    style = MaterialTheme.typography.titleLarge,
                )


            }

        }
    }

}

@Preview
@Composable
fun previewZakatScreen() {
    ZakatScreen(onBackClick = {}, list = modelList, "0", {}, {}, totalWealth = 0.0)

}
