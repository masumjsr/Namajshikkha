package com.fsit.sohojnamaj.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fsit.sohojnamaj.ui.theme.kalPurush
import com.fsit.sohojnamaj.R

@Composable
fun NuktaScreenRoute(onBackClick: () -> Unit) {
    NuktaScreen(onBackClick = onBackClick)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuktaScreen(onBackClick: () -> Unit) {
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
                title = { Text("নুকতাওয়ালা ও নুকতা ছাড়া হরফ") },

                )
        }
    )
    {
        Column(modifier = Modifier.padding(it).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "নুকতাঃ আরবি হরফের নিচে বা উপরে এক বা একাধিক ফোঁটা দেখাআ যায়। এই ফোঁটাকে নুকতা বলে। আরবি ২৯ টি হরফের মধ্যে নুকতাওয়ালা হরফ ১৫ টি, নুকতা ছাড়া হরফ ১৪টি । তার মধ্য")
            Text(
                modifier= Modifier
                    .fillMaxWidth().padding(5.dp)
                    .background(MaterialTheme.colorScheme.primary),
                text = "এক নুকতাওয়ালা হরফ ১০ টি",
                color= Color.White,
                fontFamily = kalPurush,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = R.drawable.haraf10),
                contentScale = ContentScale.Crop,
                contentDescription =null )
            Text(
                modifier= Modifier
                    .fillMaxWidth().padding(5.dp)
                    .background(MaterialTheme.colorScheme.primary),
                text = "দুই নুকতাওয়ালা হরফ ৩ টি",
                color= Color.White,
                fontFamily = kalPurush,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = R.drawable.haraf3),
                contentScale = ContentScale.Crop,
                contentDescription =null )
            Text(
                modifier= Modifier
                    .fillMaxWidth().padding(5.dp)
                    .background(MaterialTheme.colorScheme.primary),
                text = "তিন নুকতাওয়ালা হরফ ২ টি",
                color= Color.White,
                fontFamily = kalPurush,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = R.drawable.haraf2),
                contentScale = ContentScale.Crop,
                contentDescription =null )
            Text(
                modifier= Modifier
                    .fillMaxWidth().padding(5.dp)
                    .background(MaterialTheme.colorScheme.primary),
                text = "নুকতাছাড়া হরফ ১৪ টি",
                color= Color.White,
                fontFamily = kalPurush,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = R.drawable.haraf14
                ),
                contentScale = ContentScale.Crop,
                contentDescription =null )

        }
    }

}

@Preview
@Composable
fun PreviewNuktaScreen() {
    NuktaScreen({})
}
