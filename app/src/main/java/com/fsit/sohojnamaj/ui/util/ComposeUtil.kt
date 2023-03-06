package com.fsit.sohojnamaj.ui.util

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun PulsatingCircles() {
    Column (modifier = Modifier.size(20.dp)){
        val infiniteTransition = rememberInfiniteTransition()
        val size by infiniteTransition.animateValue(
            initialValue = 5.dp,
            targetValue = 25.dp,
            Dp.VectorConverter,
            animationSpec = infiniteRepeatable(
                animation = tween(2000, easing = FastOutLinearInEasing),
                repeatMode = RepeatMode.Restart
            )
        )
        val alfa by infiniteTransition.animateValue(
            initialValue = 1f,
            targetValue = 0f,

            Float.VectorConverter,
            animationSpec = infiniteRepeatable(
                animation = tween(2000, easing = FastOutLinearInEasing),
                repeatMode = RepeatMode.Restart
            ),
        )
        val smallCircle by infiniteTransition.animateValue(
            initialValue = 0.dp,
            targetValue = 20.dp,
            Dp.VectorConverter,
            animationSpec = infiniteRepeatable(
                animation = tween(2000, easing = FastOutLinearInEasing),
                repeatMode = RepeatMode.Restart
            )
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            SimpleCircleShape2(
                size = size,
                color = Color.White.copy(alpha = alfa)
            )
            SimpleCircleShape2(
                size = smallCircle,
                color = Color.White.copy(alpha = alfa)
            )
            SimpleCircleShape2(
                size = 8.dp,
                color = Color.White
            )
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {

                }
            }
        }
    }
}

@Composable
fun SimpleCircleShape2(
    size: Dp,
    color: Color = Color.White,
    borderWidth: Dp = 0.dp,
    borderColor: Color = Color.LightGray.copy(alpha = 0.0f)
) {
    Column(
        modifier = Modifier
            .wrapContentSize(Alignment.Center)
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
                .background(
                    color
                )
                .border(borderWidth, borderColor)
        )
    }
}