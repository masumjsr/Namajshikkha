package com.fsit.sohojnamaj.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
    onTypeOneClick:(Int)->Unit,
    onTypeTwoClick:(Int)->Unit,
    viewModel: SubCategoryViewModel = hiltViewModel(),
)
{

    Log.i("123321", "SubCategoryScreenRoute:  sub cat screen")
    val subCategory by viewModel.subcategoryList.collectAsStateWithLifecycle()
    Log.i("123321", "SubCategoryScreenRoute: $subCategory")
    SubCategoryScreen(subCategory=subCategory,onTypeOneClick=onTypeOneClick,onTypeTwoClick=onTypeTwoClick)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubCategoryScreen(
    subCategory: List<SubCategory>,
    onTypeOneClick: (Int) -> Unit,
    onTypeTwoClick: (Int) -> Unit
) {
        Scaffold (
            topBar = { TopAppBar(title = {Text("Title2")}) }
                ){

            LazyColumn(modifier = Modifier.padding(it) ){
               items(subCategory){
                   Card(
                       onClick = {
                                 if (it.type==0){
                                     onTypeOneClick.invoke(it.id)
                                 }
                           else {
                               onTypeTwoClick.invoke(it.id)
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
    SubCategoryScreen(sampleSubCategory, {}, {  })
}