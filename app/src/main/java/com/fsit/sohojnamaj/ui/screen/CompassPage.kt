package com.fsit.sohojnamaj.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Forward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.fsit.sohojnamaj.R
import com.fsit.sohojnamaj.model.RotationTarget
import com.fsit.sohojnamaj.ui.theme.kalPurush
import com.fsit.sohojnamaj.ui.util.ActionButton
import com.fsit.sohojnamaj.ui.util.TextBody
import com.fsit.sohojnamaj.ui.util.TextHeading
import com.fsit.sohojnamaj.ui.util.TextHeadingXLarge
import com.fsit.sohojnamaj.ui.util.TextTitle

@Composable
fun CompassPage(
    isFacingQilba: Boolean,
    qilbaRotation: RotationTarget,
    compassRotation: RotationTarget,
    locationAddress: String,
    goToBack: () -> Unit,
    refreshLocation: () -> Unit
) {
    Scaffold {
        val realDegree = 360 - qilbaRotation.to

        Column(modifier = Modifier.padding(it).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {


            Text(
                modifier = Modifier.padding(16.dp),
                text ="${realDegree.toInt()}°",
                fontSize =75.sp,
                fontFamily = kalPurush
            )

            if (realDegree in 1f..180f && !isFacingQilba)

                    Text(
                        text = "বাম দিকে ঘুরান",
                        fontFamily = kalPurush,
                        style = MaterialTheme.typography.titleLarge
            )
            if (realDegree in 181f..360f && !isFacingQilba)
                Text(
                    text = "ডান দিকে ঘুরান",
                    fontFamily = kalPurush,
                    style = MaterialTheme.typography.titleLarge)

            if (isFacingQilba)
                Text(
                text = "আপনি কিবলার দিকে আছেন",
                fontFamily = kalPurush,
                style = MaterialTheme.typography.titleLarge
            )
           ConstraintLayout(modifier = Modifier
                .padding(it)
                .fillMaxSize()) {
                val (bg, back, refresh, title, degree, description, location, windDir, compass) = createRefs()




                Image(
                    modifier = Modifier
                        .rotate(compassRotation.to)
                        .constrainAs(windDir) {
                            top.linkTo(bg.bottom)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    painter = painterResource(
                        R.drawable.ic_wind_direction_night

                    ),
                    contentDescription = "",
                )

                Image(
                    modifier = Modifier
                        .rotate(qilbaRotation.to)
                        .constrainAs(compass) {
                            top.linkTo(windDir.top)
                            bottom.linkTo(windDir.bottom)
                            start.linkTo(windDir.start)
                            end.linkTo(windDir.end)
                        },
                    painter = painterResource(id = R.drawable.ic_compass_direction),
                    contentDescription = ""
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewCompassPage() {
    CompassPage(false,
        RotationTarget(0f, 294f),
        RotationTarget(0f, 0f),
        "Desa Muara, Kecamatan Suranenggala, Kabupaten Cirebon",
        {},
        {})
}