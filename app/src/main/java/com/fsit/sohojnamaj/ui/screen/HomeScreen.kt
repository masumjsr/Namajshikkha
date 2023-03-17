package com.fsit.sohojnamaj.ui.screen

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fsit.sohojnamaj.R
import com.fsit.sohojnamaj.model.Prayer
import com.fsit.sohojnamaj.model.PrayerRange
import com.fsit.sohojnamaj.ui.theme.kalPurush
import com.fsit.sohojnamaj.ui.util.PulsatingCircles
import com.fsit.sohojnamaj.ui.util.ShowAlertDialog
import com.fsit.sohojnamaj.ui.viewModel.HomeViewModel
import com.fsit.sohojnamaj.util.calender.bangla.Bongabdo
import com.fsit.sohojnamaj.util.calender.primecalendar.civil.CivilCalendar
import com.fsit.sohojnamaj.util.calender.primecalendar.hijri.HijriCalendar
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.google.android.gms.tasks.Task
import java.util.*

@Composable
fun HomeScreenRoute(
    viewModel: HomeViewModel = hiltViewModel(),
    onSettingClick: () -> Unit,
    onQuranClick: () -> Unit,
    onSubMenuClick: (Int) -> Unit
) {
    val currentWaqt by viewModel.currentWaqt.collectAsStateWithLifecycle()
    val currentLocation by viewModel.locationData.collectAsStateWithLifecycle()
    val context= LocalView.current.context

 HomeScreen(
     current =currentWaqt,
     onSettingClick =onSettingClick,
     onQuranClick=onQuranClick,
     onSubMenuClick = onSubMenuClick,
     currentLocation =currentLocation,
     onLocationFound = { viewModel.updateLocation(it) }
 )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    current: Prayer?,
    onSettingClick: () -> Unit,
    onQuranClick: () -> Unit,
    onSubMenuClick: (Int) -> Unit,
    currentLocation: String?,
    onLocationFound:(LatLng)->Unit) {
    Scaffold(
        topBar = {
            TopAppBar (
                title = {
                    Text(
                        text = "নামাজের সময়",
                        fontFamily = kalPurush
                    )
                        },
                actions = {
                    IconButton(onClick = { onSettingClick.invoke()}) {
                        Icon(
                            modifier =Modifier
                                .padding(5.dp),
                            imageVector =Icons.Default.Settings,
                            contentDescription ="Settings")
                    }

                }
            )
        }
    ) {paddingValues->
        val context = LocalView.current.context
        var isShowDialog by remember { mutableStateOf(false) }
        var isDismissedDialog by remember { mutableStateOf(false) }
        var buttonText by remember {
            mutableStateOf(if(currentLocation?.isNotEmpty() == true)currentLocation else "Enable")
        }
        var latLng by remember { mutableStateOf("")}
        currentLocation?.let {
            if(it.isNotEmpty()) {
                buttonText = it
            }
           isShowDialog=(it== latLng)
        }
        val permissionState =  rememberPermissionState(
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if(isShowDialog){
            ShowAlertDialog(
                title = "লোকেশন প্রয়োজন",
                text ="নামাজের সঠিক সময় নির্ধারণ জন্য আপনার লোকেশন পারমিশন প্রয়োজন । আমরা আপনার লোকেশন তথ্য অ্যাপের বাইরে সংরক্ষণ করি না",
                positiveButton = "    ঠিক আছে" ,
                negativeButton ="না    " ,
                onDismiss = {
                    latLng= "searching"
                    isShowDialog=false
                },
                onAllowClick = {
                    latLng= "searching"
                    isShowDialog=false
                    permissionState.launchPermissionRequest()
                    isDismissedDialog=true

                }
            )
        }


      if(isDismissedDialog) {
          if(permissionState.status.isGranted){
             // buttonText="Searching"

              HandleLocation(context,onLocationFound)
          }

      }


        LazyColumn(
            modifier = Modifier

                .padding(top = paddingValues.calculateTopPadding(), start = 16.dp, end = 16.dp)
                .fillMaxWidth()
        ){

            item {
                DateSection(buttonText){

                    if (permissionState.status.isGranted){
                        isDismissedDialog=true
                    }
                    else {
                        latLng=""
                        isShowDialog = true
                    }
                }
                CurrentWaqt(current)

                NextWaqt(current)
                Sahari(current)
                ItemList(onQuranClick=onQuranClick,onSubMenuClick=onSubMenuClick)
                Forbidden(current)
            }

            }
            }


        }

@Composable
fun HandleLocation(context: Context,onLocationFound:(LatLng)->Unit) {


    val locationRequest=LocationRequest.Builder(Priority.PRIORITY_LOW_POWER, Long.MAX_VALUE)
        .setWaitForAccurateLocation(false)
        .build()

    val builder = LocationSettingsRequest.Builder()
        .addLocationRequest(locationRequest)

    val client: SettingsClient = LocationServices.getSettingsClient(context)
    val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

    val fusedLocationClient =LocationServices.getFusedLocationProviderClient(context)

    if ( ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.
        return
    }


    val settingResultRequest = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { activityResult ->
        if (activityResult.resultCode == Activity.RESULT_OK) {

            fusedLocationClient.getCurrentLocation(Priority.PRIORITY_LOW_POWER, object : CancellationToken() {
                override fun onCanceledRequested(p0: OnTokenCanceledListener) = CancellationTokenSource().token

                override fun isCancellationRequested() = false
            })
                .addOnSuccessListener { location: Location? ->
                    if (location == null)
                        Toast.makeText(context, "Cannot get location.", Toast.LENGTH_SHORT).show()
                    else {
                        val lat = location.latitude
                        val lon = location.longitude


                    }

                }

        }

    }
    task.addOnSuccessListener {
        // All location settings are satisfied. The client can initialize
        // location requests here.
        // ...

        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_LOW_POWER, object : CancellationToken() {
            override fun onCanceledRequested(p0: OnTokenCanceledListener) = CancellationTokenSource().token

            override fun isCancellationRequested() = false
        })
            .addOnSuccessListener { location: Location? ->
                if (location == null)
                    Toast.makeText(context, "Cannot get location.", Toast.LENGTH_SHORT).show()
                else {


                    onLocationFound.invoke(LatLng(location.latitude,location.longitude))
                }

            }
    }

    task.addOnFailureListener { exception ->
        if (exception is ResolvableApiException){
            // Location settings are not satisfied, but this can be fixed
            // by showing the user a dialog.
            try {
                // Show the dialog by calling startResolutionForResult(),
                // and check the result in onActivityResult().
                settingResultRequest.launch(
                    IntentSenderRequest.Builder(exception.resolution).build()
                )
            } catch (sendEx: IntentSender.SendIntentException) {
                // Ignore the error.
            }
        }
    }




    }





@Composable
fun ItemList(onQuranClick: () -> Unit,onSubMenuClick: (Int) -> Unit) {
        Row(
        modifier =Modifier.fillMaxWidth(),
        ){
         ItemCard(icon=R.drawable.praying,title="নামাজের সময়সূচী"){}
         ItemCard(icon=R.drawable.quran,title="কুরআন"){onQuranClick()}
         ItemCard(icon=R.drawable.wudu,title="গোসল ও ওযু"){onSubMenuClick.invoke(1)}
    }
    Row(
        modifier =Modifier.fillMaxWidth(),
    ){
        ItemCard(icon=R.drawable.muslim,title="পাঁচ কালেমা সমূহ"){onSubMenuClick.invoke(2)}
        ItemCard(icon=R.drawable.salat2,title="নামাজের দোয়া"){onSubMenuClick.invoke(3)}
        ItemCard(icon=R.drawable.shalat,title="নামাজের নিয়ত"){onSubMenuClick.invoke(4)}
    }
    Row(
        modifier =Modifier.fillMaxWidth(),
    ){
        ItemCard(icon=R.drawable.mosque,title="জুম্মার নামাজ"){onSubMenuClick.invoke(5)}
        ItemCard(icon=R.drawable.iftar,title="রোযা ও তারাবী"){onSubMenuClick.invoke(6)}
        ItemCard(icon=R.drawable.mosque2,title="দুই ইদ"){onSubMenuClick.invoke(7)}
    }
}

@Composable
fun RowScope.ItemCard(icon: Int, title: String,onItemClick:()->Unit) {
    Column(
        modifier = Modifier
            .clickable { onItemClick() }
            .padding(top = 16.dp)
            .weight(0.333f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier=Modifier.size(30.dp),
            painter = painterResource(id = icon), contentDescription = "icon")
        Text(
            modifier = Modifier.padding(top= 8.dp),
            text=title, style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            fontFamily = kalPurush
        )

    }

}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun Forbidden(current: Prayer?) {
    OutlinedCard(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .padding(top = 12.dp)
            .fillMaxWidth(),
        border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.error)
    ) {
        Column (
            modifier = Modifier.padding(8.dp)
                ){
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                text = "আজকের সম্ভাব্য নিষিদ্ধ সময়সমূহ",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = kalPurush
            )
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )

            current?.forbiddenTime?.let {
                it.forEach {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,

                        ) {
                        Text(
                            text = it.name,
                            style = MaterialTheme.typography.bodyMedium,
                            fontFamily = kalPurush
                        )
                        Text(
                            text = it.time,
                            style = MaterialTheme.typography.titleMedium,
                            fontFamily = kalPurush
                        )
                    }

                }
            }

        }
    }
}

@Composable
private fun Sahari(current: Prayer?) {
    ElevatedCard(
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color(99, 115, 128),
            contentColor = Color.White
        )
    ) {
        if (current?.isIfterOver == true) {
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                text = "আগামীকালের সময়সুচী",
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center,
                fontFamily = kalPurush
            )
        }
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {


                Column(
                    modifier = Modifier.weight(0.33f)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "সাহারী শেষ", style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center)

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        text = current?.nextSahari ?: "-",
                        style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center,
                        fontFamily = kalPurush
                    )

                }
           Column (
                modifier = Modifier.weight(0.33f)
            ) {

               Text(
                   modifier = Modifier.fillMaxWidth(),
                   text = "ইফতার", style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center,
                   fontFamily = kalPurush)
                 Text(
                     modifier = Modifier
                         .fillMaxWidth()
                         .padding(8.dp),
                     text = current?.nextIfter ?: "-", style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center,
                     fontFamily = kalPurush)


            }
            Column(
                modifier = Modifier.weight(0.33f)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                text = "পরবর্তী ${if (current?.isIfterOver == true) "সাহারি" else "ইফতার"}",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                    fontFamily = kalPurush
            )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    text = current?.nextTimeLeft ?: "-",
                    style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center,
                    fontFamily = kalPurush
                )

            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun NextWaqt(current: Prayer?) {
    OutlinedCard(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .padding(top = 12.dp)
            .fillMaxWidth(),
        border = BorderStroke(1.dp, color = Color(54, 168, 160))
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
        ) {
            Text(
                text = "পরবর্তী নামাজ",
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = kalPurush
            )
            Row() {
                Text(
                    modifier = Modifier,
                    text = current?.next ?: "-",
                    style = MaterialTheme.typography.titleMedium,
                    fontFamily = kalPurush
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = current?.nextText ?: "-",
                    style = MaterialTheme.typography.bodyLarge,
                    fontFamily = kalPurush
                )
            }

        }
    }
}

@Composable
private fun CurrentWaqt(current: Prayer?) {
    ElevatedCard(
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(
            containerColor = if (current?.forbiddenRange == true) MaterialTheme.colorScheme.error else Color(
                54,
                168,
                160
            ),
            contentColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = if (current?.forbiddenRange == true) "এখন চলছে" else "বর্তমান ওয়াক্ত",
                style = MaterialTheme.typography.titleSmall,
                fontFamily = kalPurush
            )
            Row(
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically

            ) {
                PulsatingCircles()
                Text(
                    modifier = Modifier
                        .padding(start = 10.dp),
                    text = current?.name ?: "-",
                    style = MaterialTheme.typography.titleMedium,
                    fontFamily = kalPurush
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = current?.text ?: "-",
                    style = MaterialTheme.typography.titleMedium,
                    fontFamily = kalPurush
                )
            }


            Text(
                text = "সময় বাকিঃ ${current?.timeLeft}",
                style = MaterialTheme.typography.titleSmall,
                fontFamily = kalPurush
            )


            LinearProgressIndicator(
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 10.dp)
                    .fillMaxWidth(),
                progress = current?.progress ?: 0f,
                color = Color.White,
                trackColor = Color.White.copy(0.3f)
            )
        }
    }
}


@Composable
private fun DateSection(currentLocation: String,onButtonClick:()->Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val calendar = HijriCalendar()
        val englishCalendar = CivilCalendar(locale = Locale("bn"))
        Column() {
            Text(
                text = calendar.longDateString,
                fontSize = 18.sp,
                fontFamily = kalPurush
            )
            Row() {


                Text(
                    text = englishCalendar.longDateString,
                    style = MaterialTheme.typography.bodyMedium,
                    fontFamily = kalPurush
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                    style = MaterialTheme.typography.bodyMedium,
                            text = "|",
                    fontFamily = kalPurush
                )
                Text(text = Bongabdo().now(locale = Locale("bn")),
                        style = MaterialTheme.typography.bodyMedium,
                    fontFamily = kalPurush
                )

            }
        }
        OutlinedButton(onClick = { onButtonClick.invoke()}) {
            Icon(imageVector = Icons.Default.LocationOn, contentDescription = "Location On")
            Text(text = currentLocation.ifEmpty { "Enable" },
                fontFamily = kalPurush)
        }
    }
}

@Preview(locale = "bn")
@Composable

fun PreviewHomeScreen() {
    HomeScreen(
        current = Prayer(name = "মাগরিব", PrayerRange(0,1234,1234),text="7:10 PM - 5:50 AM"),
        onSettingClick = {},
        onQuranClick = {},
        currentLocation = "",
        onLocationFound = {},
        onSubMenuClick = {}

    )

}

/*
@Preview(locale = "en")
@Composable

fun PreviewHomeScreenEn() {
    HomeScreen()

}*/
