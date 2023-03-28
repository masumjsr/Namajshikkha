package com.fsit.sohojnamaj.ui.screen

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fsit.sohojnamaj.model.dua.SubCategory
import com.fsit.sohojnamaj.model.dua.sampleSubCategory
import com.fsit.sohojnamaj.ui.theme.kalPurush
import com.fsit.sohojnamaj.ui.viewModel.SubCategoryViewModel

@Composable
fun SubCategoryScreenRoute(
    onBackClick: () -> Unit,
    onTypeOneClick: (Int, String) -> Unit,
    onTypeTwoClick: (Int, String) -> Unit,
    viewModel: SubCategoryViewModel = hiltViewModel(),
    onAlifBaaScreen: () -> Unit,
    onNuktaScreen: () -> Unit
)
{

    val subCategory by viewModel.subcategoryList.collectAsStateWithLifecycle()
    val title= viewModel.title
    val id =viewModel.id
    SubCategoryScreen(
        id=id,
        title=title,
        subCategory=subCategory,
        onTypeOneClick=onTypeOneClick,
        onTypeTwoClick=onTypeTwoClick,
        onBackClick=onBackClick,
        onAlifBaaScreen=onAlifBaaScreen,
        onNuktaScreen=onNuktaScreen
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubCategoryScreen(
    id: Int,
    subCategory: List<SubCategory>,
    onTypeOneClick: (Int, String) -> Unit,
    onTypeTwoClick: (Int, String) -> Unit,
    title: String,
    onBackClick: () -> Unit,
    onAlifBaaScreen: () -> Unit,
    onNuktaScreen: () -> Unit
) {
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
                title = {Text(title)},

            ) }
                ){

            LazyColumn(modifier = Modifier.padding(it) ){
                if (id==12){
                    item {

                        Card(
                            onClick = {
                            onAlifBaaScreen.invoke()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 10.dp, end = 10.dp, bottom = 5.dp)
                        ) {

                                Text(
                                    modifier=Modifier.padding(16.dp),
                                    text = "বাংলা উচ্চারণসহ আরবি ২৯ টি হরফ",
                                    fontFamily = kalPurush,
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        Card(
                            onClick = {
                            onNuktaScreen.invoke()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 10.dp, end = 10.dp, bottom = 5.dp)
                        ) {

                                Text(
                                    modifier=Modifier.padding(16.dp),
                                    text = "নুকতা",
                                    fontFamily = kalPurush,
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }


                    }
                }
               items(subCategory){
                   Card(
                       onClick = {
                                 if (it.type==0){
                                     onTypeOneClick.invoke(it.id,it.title)
                                 }
                           else {
                               onTypeTwoClick.invoke(it.id,it.title)
                                 }
                       },
                       modifier = Modifier
                           .fillMaxWidth()
                           .padding(start = 10.dp, end = 10.dp, bottom = 5.dp)
                   ) {
                       it.title?.let { it1 ->
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
fun PreviewSubCategoryScreen() {
    SubCategoryScreen(0, sampleSubCategory, { _, _->}, { _, _-> }, "title", {}, {}, {})
}