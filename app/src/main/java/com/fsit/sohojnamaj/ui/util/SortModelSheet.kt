package com.fsit.sohojnamaj.ui.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.semantics.Role.Companion.RadioButton
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SortContent(onItemClick: (Int) -> Unit) {
KindRadioGroupUsage(onItemClick=onItemClick)

}


@Composable
fun KindRadioGroupUsage(onItemClick: (Int) -> Unit) {
    val kinds = listOf("Name A->Z","Name Z->A","Newest date first","Oldest date first","Largest first","Smallest first")
    val (selected, setSelected) = remember { mutableStateOf(2) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ){
        Text(
            text = "  Sort By",
            fontSize = 20.sp,
            modifier = Modifier.padding(8.dp)
        )
        KindRadioGroup(
            mItems = kinds,
           selected,
           setSelected={
               setSelected.invoke(it)
               onItemClick.invoke(it)
           }
        )

    }
}

@Composable
fun ThemeRadioGroupUsage(position :Int ,onItemClick: (Int) -> Unit) {
    val kinds = listOf("অটো",  "ডার্ক","লাইট")
    val (selected, setSelected) = remember { mutableStateOf(position) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ){

        ThemeRadioGroup(
            mItems = kinds,
           selected,
           setSelected={
               setSelected.invoke(it)
               onItemClick.invoke(it)
           }
        )

    }
}

@Composable
fun KindRadioGroup(
    mItems: List<String>,
    selected: Int,
    setSelected: (selected: Int) -> Unit,
) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            mItems.forEachIndexed { index,  item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selected == index,
                        onClick = {
                            setSelected(index)
                        },
                        enabled = true,
                    )
                    Text(
                        text = item,

                        modifier = Modifier.clickable {
                        setSelected(index)
                    },
                        fontSize = 14.sp
                    )
                }
            }
        }
    }

@Composable
fun ThemeRadioGroup(
    mItems: List<String>,
    selected: Int,
    setSelected: (selected: Int) -> Unit,
) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            mItems.forEachIndexed { index,  item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selected == index,
                        onClick = {
                            setSelected(index)
                        },
                        enabled = true,
                    )
                    Text(text = item, modifier = Modifier.clickable {

                        setSelected(index)
                    })
                }
            }
        }
    }



@Preview(showBackground = true)
@Composable
fun PreviewSortContent() {
    SortContent {

    }
}