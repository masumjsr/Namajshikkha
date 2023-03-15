package com.fsit.sohojnamaj.ui.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fsit.sohojnamaj.model.dua.TypeOneItem
import com.fsit.sohojnamaj.model.dua.sampleSubCategory
import com.fsit.sohojnamaj.model.dua.sampleTypeOne
import com.fsit.sohojnamaj.ui.theme.kalPurush
import com.fsit.sohojnamaj.ui.viewModel.TypeOneViewModel

@Composable
fun TypeOneScreenRoute(
    onBackClick: () -> Unit,
    viewModel: TypeOneViewModel = hiltViewModel(),
)
{
    val typeOneItem by viewModel.typeOneList.collectAsStateWithLifecycle()
    Log.i("123321", "TypeOneScreenRoute:id=${viewModel.id} data is  $typeOneItem ")
    TypeOneScreen(typeOneItem =typeOneItem)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TypeOneScreen(typeOneItem: List<TypeOneItem>) {
        Scaffold (
            topBar = { TopAppBar(title = {Text("Title type one")}) }
                ){

            LazyColumn(modifier = Modifier.padding(it) ){
               items(typeOneItem){
                   Card(
                       onClick = { /*TODO*/ },
                       modifier = Modifier
                           .fillMaxWidth()
                           .padding(start = 10.dp, end = 10.dp, bottom = 5.dp)
                   ) {
                       if(it.title?.isNotEmpty()==true){
                           Text(
                               modifier=Modifier.fillMaxWidth()
                                   .background(MaterialTheme.colorScheme.primary),
                               text = it.title,
                               color=Color.White,
                               fontFamily = kalPurush,
                               style = MaterialTheme.typography.titleLarge,
                               textAlign = TextAlign.Center
                           )
                       }
                       it.data?.let { it1 ->
                           Text(
                               modifier=Modifier.padding(16.dp),
                               text = it1,
                               fontFamily = kalPurush,
                               style = MaterialTheme.typography.titleMedium
                           )
                       }

                   }
               }

            }
        }
}

@Preview
@Composable
fun PreviewTypeOneScreen() {
    TypeOneScreen(sampleTypeOne)
}