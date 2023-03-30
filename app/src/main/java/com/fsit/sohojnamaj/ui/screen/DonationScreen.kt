package com.fsit.sohojnamaj.ui.screen

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CopyAll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fsit.sohojnamaj.ui.theme.kalPurush

@Composable
fun DonationScreenRoute(onBackClick: () -> Unit) {

    DonationScreen(onBackClick = onBackClick)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DonationScreen(onBackClick: () -> Unit) {
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
                title = { Text("ডোনেশন") },

                )
        }
    ) {

        Column(modifier = Modifier.padding(it)) {


            DonationItem("বিকাশ পার্সোনাল","+880-1760189974")
            DonationItem("রকেট পার্সোনাল","+880-1760189974")
            DonationItem("নগদ পার্সোনাল","+880-1760189974")
            DonationItem("পেপাল","salauddinm04@gmail.com")

        }
    }
}

@Composable
private fun DonationItem(title: String,number:String) {
    val context= LocalView.current.context

    Card(modifier = Modifier.padding(5.dp)) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                fontFamily = kalPurush,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = number,
                fontFamily = kalPurush,
                style = MaterialTheme.typography.titleLarge
            )
            IconButton(onClick = {

                val clipboard =
                    context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("label", number)
                clipboard.setPrimaryClip(clip)

                Toast.makeText(context, "Text Copied Successfully", Toast.LENGTH_SHORT).show()
            }) {
                Icon(imageVector = Icons.Default.CopyAll, contentDescription = null)
            }
        }
    }
}

@Composable
private fun donationItem(context: Context) {
    Card(modifier = Modifier.padding(5.dp)) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "বিকাশ পার্সোনাল",
                fontFamily = kalPurush,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "+880-1760189974",
                fontFamily = kalPurush,
                style = MaterialTheme.typography.titleLarge
            )
            IconButton(onClick = {

                val clipboard =
                    context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("label", "")
                clipboard.setPrimaryClip(clip)

                Toast.makeText(context, "Text Copied Successfully", Toast.LENGTH_SHORT).show()
            }) {
                Icon(imageVector = Icons.Default.CopyAll, contentDescription = null)
            }
        }
    }
}

@Preview
@Composable
fun previewDonationScreen() {
    DonationScreen(onBackClick = {})

}