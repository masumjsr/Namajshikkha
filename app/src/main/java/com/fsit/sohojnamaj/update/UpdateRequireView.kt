package com.fsit.sohojnamaj.update


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import se.warting.inappupdate.R

@Preview
@Composable
fun updte() {
    UpdateRequiredView(update = { /*TODO*/ }) {
        
    }
}

@Composable
 fun UpdateRequiredView(update: () -> Unit,onCancelled: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.in_app_update_compose_update_required_title),
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.W700)
        )

        Text(
            text = stringResource(id = R.string.in_app_update_compose_update_required_body),
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.W700)
        )

        Spacer(modifier = Modifier.height(50.dp))

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(start=24.dp, end =24.dp), horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )

        {
            Button(modifier = Modifier,
                onClick = { update() }) {
                Text(
                    text = stringResource(id = R.string.in_app_update_compose_update_required_button),
                )

            }
            Text(text = "Cancel", modifier = Modifier.clickable {
            onCancelled.invoke()
        })
        }
    }
}



@Composable
internal fun UpdateInProgress(progress: Float) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.in_app_update_compose_update_progress_title),
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.W700)
        )

        Text(
            text = stringResource(id = R.string.in_app_update_compose_update_progress_body),
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.W700)
        )

        if (!progress.isNaN()) {
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator(progress = progress)
        }
    }
}
