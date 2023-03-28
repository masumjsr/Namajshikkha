package com.fsit.sohojnamaj.ui.screen

import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import  com.fsit.sohojnamaj.R
import androidx.compose.ui.unit.sp
import com.fsit.sohojnamaj.ui.theme.kalPurush


@Composable
fun TasbhiScreenRoute(onBackClick: () -> Unit) {
TasbhiScreen(onBackClick)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasbhiScreen(onBackClick: () -> Unit) {
    var numberR by remember {
        mutableStateOf(0f)
    }
    var numberTotal by remember {
        mutableStateOf(0f)
    }
    val context= LocalView.current.context

    val mp=MediaPlayer.create(context,R.raw.sound)

   Scaffold(
       topBar = {
           TopAppBar(
               title = {Text("তজবীহ")},
               navigationIcon = {
                   IconButton(onClick = {onBackClick.invoke()}) {
                       Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                   }
               }
           )
       }
   ) {
      Column(
          modifier = Modifier
              .padding(it)
              .clickable(

                  interactionSource = remember { MutableInteractionSource() },
                  indication = rememberRipple(
                      radius = 500.dp,
                      bounded = false,
                      color = MaterialTheme.colorScheme.primary
                  ),
                  onClick = {

                      mp.start()
                      numberTotal++
                      if (numberR == 33f) {
                          val v = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?
// Vibrate for 500 milliseconds
// Vibrate for 500 milliseconds
                          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                              v!!.vibrate(
                                  VibrationEffect.createOneShot(
                                      500,
                                      VibrationEffect.DEFAULT_AMPLITUDE
                                  )
                              )
                          } else {
                              //deprecated in API 26
                              v!!.vibrate(500)
                          }
                          numberR = 1f
                      } else numberR++
                  },
              )
              .fillMaxSize(),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.SpaceBetween
      ) {
            Spacer(modifier = Modifier)
          Column(
              modifier =Modifier.padding(16.dp)
                  ,
              horizontalAlignment = Alignment.CenterHorizontally
          ) {
                Button(onClick = { /*TODO*/ }, enabled = false, modifier = Modifier.padding(20.dp)) {
                    Text(text ="সর্বমোটঃ${String.format("%,d",numberTotal.toInt())}" )
                }
              CircularProgressbar2(numberTemp =numberR)
              Button(onClick = { numberR=0f;numberTotal=0f; }, modifier = Modifier.padding(20.dp)) {
                  Text(text ="রিসেট" )
              }
          }
      }
   }
}




@Composable
fun CircularProgressbar2(
    number: Float = 60f,
    numberStyle: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp
    ),
    size: Dp = 280.dp,
    thickness: Dp = 16.dp,
    animationDuration: Int = 100,
    animationDelay: Int = 0,
    foregroundIndicatorColor: Color = Color(0xFF35898f),
    backgroundIndicatorColor: Color = foregroundIndicatorColor.copy(alpha = 0.5f),
    extraSizeForegroundIndicator: Dp = 12.dp,
    numberTemp: Float,
) {

    // It remembers the number value

    var numberR=numberTemp
    // Number Animation
    val animateNumber = animateFloatAsState(
        targetValue = numberR,
        animationSpec = tween(
            durationMillis = if(numberR==1f)1000 else 100,
            delayMillis = animationDelay
        )
    )

    // This is to start the animation when the activity is opened
    LaunchedEffect(Unit) {
        numberR = number
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(size = size)
    ) {
        androidx.compose.foundation.Canvas(
            modifier = Modifier
                .size(size = size)
        ) {

            // Background circle
            drawCircle(
                color = backgroundIndicatorColor,
                radius = size.toPx() / 2,
                style = Stroke(width = thickness.toPx(), cap = StrokeCap.Round)
            )

            val sweepAngle = (animateNumber.value / 33) * 360

            // Foreground circle
            drawArc(
                color = foregroundIndicatorColor,
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke((thickness + extraSizeForegroundIndicator).toPx(), cap = StrokeCap.Round)
            )
        }

        // Text that shows number inside the circle
        Text(
            text =String.format("%,d", (animateNumber.value).toInt()),
            style = numberStyle,
            fontFamily = kalPurush,
            fontSize = 60.sp
        )
    }


}

@Composable
private fun ButtonProgressbar(
    backgroundColor: Color = Color(0xFF35898f),
    onClickButton: () -> Unit,
) {
    Button(
        onClick = {
            onClickButton()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor
        )
    ) {
        Text(
            text = "Animate with Random Value",
            color = Color.White,
            fontSize = 16.sp
        )
    }
}


@Preview
@Composable
fun PreviewTasbhiScreen() {
    TasbhiScreen({})

}