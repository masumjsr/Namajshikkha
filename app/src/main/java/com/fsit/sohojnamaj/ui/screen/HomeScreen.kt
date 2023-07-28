package com.fsit.sohojnamaj.ui.screen

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxInterstitialAd
import com.fsit.sohojnamaj.MainActivity
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
import com.fsit.sohojnamaj.util.dateUtil.toTimeFormat
import com.fsit.sohojnamaj.util.loadInterstitial
import com.fsit.sohojnamaj.util.showInterstitial
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*




@Composable
fun HomeScreenRoute(
    viewModel: HomeViewModel = hiltViewModel(),
    onSettingClick: () -> Unit,
    onQuranClick: () -> Unit,
    onSortQuranClick: () -> Unit,
    onSubMenuClick: (Int) -> Unit,
    onTasbhiClick: () -> Unit,
    onNameClick: () -> Unit,
    onCompassScreen: () -> Unit,
    onZakatClick: () -> Unit,
    onDonationClick: () -> Unit,
) {
    val currentWaqt by viewModel.currentWaqt.collectAsStateWithLifecycle()
    val currentLocation by viewModel.locationData.collectAsStateWithLifecycle()
    val offset by viewModel.offsetData.collectAsStateWithLifecycle()
    val context= LocalView.current.context

 HomeScreen(
     current =currentWaqt,
     offset=offset,
     onSettingClick =onSettingClick,
     onQuranClick=onQuranClick,
     onSortQuranClick=onSortQuranClick,
     onSubMenuClick = onSubMenuClick,
     currentLocation =currentLocation,
     onLocationFound = { viewModel.updateLocation(it) },
     onNameClick = onNameClick,
     onCompassScreen=onCompassScreen,
     onTasbhiClick=onTasbhiClick,
     onZakatClick=onZakatClick,
     onDonationClick=onDonationClick,

 )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    current: Prayer?,
    offset: Int,
    onSettingClick: () -> Unit,
    onQuranClick: () -> Unit,
    onSortQuranClick: () -> Unit,
    onSubMenuClick: (Int) -> Unit,
    currentLocation: String?,
    onLocationFound: (LatLng) -> Unit,
    onNameClick: () -> Unit,
    onCompassScreen: () -> Unit,
    onTasbhiClick: () -> Unit,
    onZakatClick: () -> Unit,
    onDonationClick: () -> Unit,
) {
    val context= LocalView.current.context
    loadInterstitial(context)
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
          Log.i("123321", "HomeScreen: dismissed dialog")
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
                DateSection(buttonText,offset){

                    if (permissionState.status.isGranted){
                        isDismissedDialog=false
                        isDismissedDialog=true
                    }
                    else {
                        latLng=""
                        isShowDialog = true
                    }
                }
                CurrentWaqt(current)

                NextWaqt(current)
                DonationSlide(onDonationClick=onDonationClick)
                Sahari(current)
                ItemList(
                    onQuranClick={ showInterstitial(context){onQuranClick.invoke()}},
                    onSortQuranClick={ showInterstitial(context){onSortQuranClick.invoke()}},
                    onSubMenuClick={ showInterstitial(context){onSubMenuClick.invoke(it)} },
                    onNameClick={ showInterstitial(context){onNameClick.invoke()}},
                    onCompassScreen={ showInterstitial(context){onCompassScreen.invoke()}},
                    onTasbhiClick={ showInterstitial(context){onTasbhiClick.invoke()}},
                    onZakatClick={ showInterstitial(context){onZakatClick.invoke()}},
                )
                Prayers(current)
                Forbidden(current)
            }

            }
            }


        }

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalPagerApi::class
)
@Composable
fun DonationSlide(onDonationClick: () -> Unit) {
    val context= LocalView.current.context
    val icons= listOf(R.drawable.rate,R.drawable.charity)
    val titles= listOf("৫ স্টার দিন","ডোনেশন দিন")
    val description= listOf("অ্যাপটি ভাল লেগে থাকলে প্লেস্টোরে গিয়ে ভাল রিভিউ এবং ৫ স্টার দিন","ডোনেশনের মাধ্যমে ভবিষ্যতে আরো দীনি কাজ করতে উদ্বুদ্ধ করুন")
    val state = rememberPagerState()


    LaunchedEffect(key1 = state.currentPage) {
        launch {
            delay(3000)
            with(state) {
                val target = if (currentPage < 2 - 1) currentPage + 1 else 0

               state.animateScrollToPage(target)
            }
        }
    }
    HorizontalPager(
        state = state,
        count = 2
    ) { page ->
    OutlinedCard(

        onClick = {

                  if(page ==0){
                      val uri: Uri = Uri.parse("market://details?id=com.fsit.sohojnamaj")
                      val goToMarket = Intent(Intent.ACTION_VIEW, uri)
                      // To count with Play market backstack, After pressing back button,
                      // to taken back to our application, we need to add following flags to intent.
                      goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or
                              Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                              Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
                      try {
                          context.startActivity(goToMarket)
                      } catch (e: ActivityNotFoundException) {
                          context.startActivity(Intent(Intent.ACTION_VIEW,
                              Uri.parse("http://play.google.com/store/apps/details?id=com.fsit.sohojnamaj")))
                      }
                  }
                else onDonationClick.invoke()
        },
        modifier = Modifier.padding(top=8.dp)

    ) {
        Row(modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()){
            Image(
                modifier= Modifier
                    .size(48.dp)
                    .clip(CircleShape)


                    .background(MaterialTheme.colorScheme.primary)
                    .padding(5.dp),
                painter = painterResource(id = icons[page]), contentDescription = null)
            Column(modifier = Modifier

                .padding(5.dp)) {
                Text(text = titles[page], fontFamily = kalPurush, style =MaterialTheme.typography.titleMedium, fontWeight =
                FontWeight.Bold)
                Text(text = description[page], fontFamily = kalPurush, style = MaterialTheme.typography.bodySmall)
            }

        }
    }
    }
}

@Composable
fun HandleLocation(context: Context,onLocationFound:(LatLng)->Unit) {


    val locationRequest=LocationRequest.Builder(Priority.PRIORITY_BALANCED_POWER_ACCURACY, Long.MAX_VALUE)
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

            fusedLocationClient.getCurrentLocation(Priority.PRIORITY_BALANCED_POWER_ACCURACY, object : CancellationToken() {
                override fun onCanceledRequested(p0: OnTokenCanceledListener) = CancellationTokenSource().token

                override fun isCancellationRequested() = false
            })
                .addOnSuccessListener { location: Location? ->
                    if (location == null)
                        Toast.makeText(context, "Cannot get location.", Toast.LENGTH_SHORT).show()
                    else {
                        Log.i("123321", "HandleLocation: location founded")

                        onLocationFound.invoke(LatLng(location.latitude,location.longitude))
                    }

                }
                .addOnFailureListener {
                    Log.i("123321", "HandleLocation: failure")
                }

        }

    }
    task.addOnSuccessListener {
        // All location settings are satisfied. The client can initialize
        // location requests here.
        // ...

        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_BALANCED_POWER_ACCURACY, object : CancellationToken() {
            override fun onCanceledRequested(p0: OnTokenCanceledListener) = CancellationTokenSource().token

            override fun isCancellationRequested() = false
        })
            .addOnSuccessListener { location: Location? ->
                if (location == null)
                    Toast.makeText(context, "Cannot get location.", Toast.LENGTH_SHORT).show()
                else {
                    Log.i("123321", "HandleLocation: location founded")

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
             if(settingResultRequest!=null){
                 settingResultRequest.launch(
                     IntentSenderRequest.Builder(exception.resolution).build()
                 )
             }
            } catch (sendEx: IntentSender.SendIntentException) {
                Log.i("123321", "HandleLocation:401: ${sendEx.message}")
                // Ignore the error.
            }
        }
        else{

            Log.i("123321", "HandleLocation: ${exception.message}")
        }
    }




    }





@Composable
fun ItemList(
    onQuranClick: () -> Unit,
    onSortQuranClick: () -> Unit,
    onSubMenuClick: (Int) -> Unit,
    onNameClick: () -> Unit,
    onCompassScreen: () -> Unit,
    onTasbhiClick: () -> Unit,
    onZakatClick: () -> Unit,
) {
    Row(
    modifier =Modifier.fillMaxWidth(),
){
        val context= LocalView.current.context

    ItemCard(icon=R.drawable.hadis,title="হাদিস"){onSubMenuClick.invoke(11)}
    ItemCard(icon=R.drawable.a_01_allah,title="আল্লাহ্‌র নাম"){onNameClick.invoke()}
    ItemCard(icon=R.drawable.qibla,title="কিবলা"){

        val pm: PackageManager = context.packageManager
        if (!pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_COMPASS)) {
            Toast.makeText(context, "কম্পাস সেনসর নেই \n" +
                    "দুখিঃত , আপনার মোবাইলে কম্পাস সেনসর না থাকার কারনে এই ফিচারটি ব্যবহার করা সম্ভব হচ্ছে না ", Toast.LENGTH_SHORT).show()
            // This device does not have a compass, turn off the compass feature
        }
       else  onCompassScreen.invoke()}
}
        Row(
        modifier =Modifier.fillMaxWidth(),
        ){
         ItemCard(icon=R.drawable.tasbih,title="তাজবি"){onTasbhiClick.invoke()}
            ItemCard(icon=R.drawable.quran2,title="কুরআন"){onQuranClick()}
         ItemCard(icon=R.drawable.oju,title="গোসল ও ওযু"){onSubMenuClick.invoke(1)}
    }
    Row(
        modifier =Modifier.fillMaxWidth(),
    ){
        ItemCard(icon=R.drawable.kalema,title="পাঁচ কালেমা সমূহ"){onSubMenuClick.invoke(2)}
        ItemCard(icon=R.drawable.dua,title="নামাজের দোয়া"){onSubMenuClick.invoke(3)}
        ItemCard(icon=R.drawable.niyot,title="নামাজের নিয়ত"){onSubMenuClick.invoke(4)}
    }
    Row(
        modifier =Modifier.fillMaxWidth(),
    ){
        ItemCard(icon=R.drawable.mosque,title="জুম্মার নামাজ"){onSubMenuClick.invoke(5)}
        ItemCard(icon=R.drawable.iftar,title="রোযা ও তারাবী"){onSubMenuClick.invoke(6)}
        ItemCard(icon=R.drawable.eid,title="দুই ইদ"){onSubMenuClick.invoke(7)}
    }
    Row(
        modifier =Modifier.fillMaxWidth(),
    ){
        ItemCard(icon=R.drawable.jakat,title="যাকাত"){onSubMenuClick.invoke(8)}
        ItemCard(icon=R.drawable.hajj,title="হজ ও ওমরা"){onSubMenuClick.invoke(9)}
        ItemCard(icon=R.drawable.qurbani,title="কুরবানী ও আকিকা "){onSubMenuClick.invoke(7)}
    }
  Row(
        modifier =Modifier.fillMaxWidth(),
    ){
        ItemCard(icon=R.drawable.sura,title="ছোট সূরা"){onSortQuranClick.invoke()}
        ItemCard(icon=R.drawable.quranlearn,title="সহীহ কুরআন শিক্ষা"){onSubMenuClick.invoke(12)}
        ItemCard(icon=R.drawable.calculator,title="যাকাত ক্যালকুলেটর"){onZakatClick.invoke()}
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
        Icon(


            tint=MaterialTheme.colorScheme.primary,
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
                fontFamily = kalPurush,
                color =  MaterialTheme.colorScheme.error
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
            containerColor = Color(51, 150, 30),
            contentColor = Color.White
        )
    ) {
        if (current?.isIfterOver == true) {
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                text = "আগামীকালের সেহেরী এবং ইফতারের সময়সূচী",
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center,
                fontFamily = kalPurush
            )
        }
        Row(
            modifier = Modifier
                .padding(0.dp)
                .fillMaxWidth()
        ) {



                Column(
                    modifier = Modifier.weight(0.5f)
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        text = "সাহারী শেষ", style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center,fontFamily = kalPurush)

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        text = "ইফতার", style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center, fontFamily = kalPurush)


                }
           Column (
                modifier = Modifier.weight(0.5f)
            ) {


               Text(
                   modifier = Modifier
                       .fillMaxWidth()
                       .padding(8.dp),
                   text = current?.nextSahari ?: "-",
                   style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center,
                   fontFamily = kalPurush
               )
                 Text(
                     modifier = Modifier
                         .fillMaxWidth()
                         .padding(8.dp),
                     text = current?.nextIfter ?: "-", style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center,
                     fontFamily = kalPurush)


            }

        }

        Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
            PulsatingCircles()
            Text(
                modifier = Modifier
                    .padding(8.dp),
                text = "পরবর্তী ${if (current?.isIfterOver == true) "সাহারি" else "ইফতার"}",
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = kalPurush
            )
            Text(
                modifier = Modifier
                    .padding(bottom = 8.dp,top=8.dp),
                text = current?.nextTimeLeft ?: "-",
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = kalPurush
            )

        }
        LinearProgressIndicator(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            progress = current?.ifterProgress ?: 0f,
            color = Color.White,
            trackColor = Color.White.copy(0.3f)
        )


    }
}

@Composable
private fun Prayers(current: Prayer?) {
    current?.let {
       if(it.all.size==5){
           ElevatedCard(
               modifier = Modifier
                   .padding(top = 10.dp)
                   .fillMaxWidth(),
               colors = CardDefaults.elevatedCardColors(
                   containerColor = Color(13, 13, 14, 255),
                   contentColor = Color.White
               )
           ) {

               Text(
                   modifier = Modifier
                       .padding(8.dp)
                       .fillMaxWidth(),
                   text = "নামাজের সময়সূচী",
                   style = MaterialTheme.typography.titleSmall,
                   textAlign = TextAlign.Center,
                   fontFamily = kalPurush
               )

               Row(
                   modifier = Modifier
                       .padding(8.dp)
                       .fillMaxWidth()
               ) {


                   Column(
                       modifier = Modifier.weight(0.5f)
                   ) {
                       Text(
                           modifier = Modifier.fillMaxWidth(),
                           text = "ফজর", style = MaterialTheme.typography.bodyMedium)

                       Text(
                           modifier = Modifier
                               .fillMaxWidth()
                               .padding(8.dp),
                           text =  "যোহর",
                           style = MaterialTheme.typography.bodyMedium,
                           fontFamily = kalPurush
                       )
                       Text(
                           modifier = Modifier
                               .fillMaxWidth()
                               .padding(8.dp),
                           text =  "আসর",
                           style = MaterialTheme.typography.bodyMedium,
                           fontFamily = kalPurush
                       )
                       Text(
                           modifier = Modifier
                               .fillMaxWidth()
                               .padding(8.dp),
                           text =  "মাগরিব",
                           style = MaterialTheme.typography.bodyMedium,
                           fontFamily = kalPurush
                       )


                       Text(
                           modifier = Modifier
                               .fillMaxWidth()
                               .padding(8.dp),
                           text =  "এশা",
                           style = MaterialTheme.typography.bodyMedium,
                           fontFamily = kalPurush
                       )

                   }
                   Column(
                       modifier = Modifier.weight(0.5f)
                   ) {
                       Text(
                           modifier = Modifier.fillMaxWidth(),
                           text = it.all[0].toTimeFormat(), style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center)

                       Text(
                           modifier = Modifier
                               .fillMaxWidth()
                               .padding(8.dp),
                           text = it.all[1].toTimeFormat(),
                           style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center,
                           fontFamily = kalPurush
                       )
                       Text(
                           modifier = Modifier
                               .fillMaxWidth()
                               .padding(8.dp),
                           text =  it.all[2].toTimeFormat(),
                           style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center,
                           fontFamily = kalPurush
                       )
                       Text(
                           modifier = Modifier
                               .fillMaxWidth()
                               .padding(8.dp),
                           text =  it.all[3].toTimeFormat(),
                           style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center,
                           fontFamily = kalPurush
                       )


                       Text(
                           modifier = Modifier
                               .fillMaxWidth()
                               .padding(8.dp),
                           text =  it.all[4].toTimeFormat(),
                           style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center,
                           fontFamily = kalPurush
                       )

                   }

               }
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
            containerColor = if (current?.forbiddenRange == true) MaterialTheme.colorScheme.error else
                Color(51, 150, 30),
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
private fun DateSection(currentLocation: String,offset:Int,onButtonClick:()->Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val calendar = HijriCalendar()
        calendar.add(Calendar.DAY_OF_MONTH,offset-1)
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
        current = Prayer(
            name = "মাগরিব",
            PrayerRange(0,1234,1234),
            text="7:10 PM - 5:50 AM",
            all = listOf(),

        ),
        offset = 0,
        onSettingClick = {},
        onQuranClick = {},
        onSortQuranClick = {},
        onSubMenuClick = {},
        currentLocation = "",
        onLocationFound = {},
        onNameClick = {},
        onCompassScreen ={},
        onTasbhiClick = {},
        onZakatClick = {},
        onDonationClick = {}

    )

}

/*
@Preview(locale = "en")
@Composable

fun PreviewHomeScreenEn() {
    HomeScreen()

}*/
