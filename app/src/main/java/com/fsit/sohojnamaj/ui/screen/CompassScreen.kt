package com.fsit.sohojnamaj.ui.screen

import android.location.Location
import android.util.Log
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fsit.sohojnamaj.R
import com.fsit.sohojnamaj.model.RotationTarget
import com.fsit.sohojnamaj.ui.viewModel.CompassViewModel
import com.fsit.sohojnamaj.util.qibla.Compass
import com.fsit.sohojnamaj.util.qibla.SOTWFormatter
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CompassScreenRoute(
    onBackClick: () -> Unit,
    viewModel: CompassViewModel = hiltViewModel(),
) {
    val location by viewModel.locationData.collectAsStateWithLifecycle()

    Log.i("123321", "CompassScreenRoute: $location")
   if(location.latitude!=0.0 && location.longitude!=0.0){
       CompassScreen(
           onBackClick =onBackClick,
           location = location
       )
   }
}

@Composable
fun CompassScreen(onBackClick: () -> Unit, location: LatLng,  viewModel: CompassViewModel = hiltViewModel()) {
    val context= LocalView.current.context
    var currentAzimuth = 0f
    var compass =Compass(context)
    var rotate by remember {
        mutableStateOf(0f)
    }
    CompassPage(viewModel.isFacingQibla,
        viewModel.qilbaRotation,
        viewModel.compassRotation,
        viewModel.locationAddress,
        goToBack = { },
        refreshLocation = {

        })


    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        // Create an observer that triggers our remembered callbacks
        // for lifecycle events
        val observer = LifecycleEventObserver { _, event ->
            when (event) {




                Lifecycle.Event.ON_PAUSE -> {
                    compass.stop()

                }
                Lifecycle.Event.ON_DESTROY-> {
                   compass.stop()
                }
                Lifecycle.Event.ON_START->{
                    compass.start()
                }
                Lifecycle.Event.ON_RESUME->{
                    compass.start()
                }
                else -> {}
            }
        }

        // Add the observer to the lifecycle
        lifecycleOwner.lifecycle.addObserver(observer)

        // When the effect leaves the Composition, remove the observer
        onDispose {

            //mediaPlayer.pause()
            lifecycleOwner.lifecycle.removeObserver(observer)

        }


    }




     var currentDegree = 0f
     var currentDegreeNeedle = 0f


    compass.setListener(object :Compass.CompassListener{

        override fun onNewAzimuth(degree: Float) {





            val CurrentLoc = Location("service Provider").apply {
                latitude = location.latitude
                longitude = location.longitude
            }
            val destinationLoc = Location("service Provider").apply {
                latitude = 21.422487
                longitude = 39.826206
            }

            var bearTo: Float = CurrentLoc.bearingTo(destinationLoc)
            if (bearTo < 0) bearTo += 360
            var direction: Float = bearTo - degree
            if (direction < 0) direction += 360

            val isFacingQibla = direction in 359.0..360.0 || direction in 0.0..1.0

            val qiblaRoation = RotationTarget(currentDegreeNeedle, direction)
            currentDegreeNeedle = direction
            val compassRotation = RotationTarget(currentDegree, -degree)
            currentDegree = -degree

            Log.i("123321", "onSensorChanged: current degree is $currentDegreeNeedle")
            viewModel.updateCompass(qiblaRoation, compassRotation, isFacingQibla)









        }

        override fun onAccuracyChanged(accuracy: Int) {

        }
    })
}







@Preview
@Composable
fun CompassScreenPreview() {
CompassScreen({}, LatLng(0.0,0.0))
}