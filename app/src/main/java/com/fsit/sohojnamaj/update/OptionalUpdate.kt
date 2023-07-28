package com.fsit.sohojnamaj.update


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fsit.sohojnamaj.MainActivity
import com.fsit.sohojnamaj.R
import com.google.android.play.core.ktx.AppUpdateResult
import kotlinx.coroutines.launch
import se.warting.inappupdate.compose.rememberInAppUpdateState

// Try launch update in another way
private const val APP_UPDATE_REQUEST_CODE = 86500

@Composable
fun OptionalUpdate(content: @Composable () -> Unit) {
    val inAppUpdateState = rememberInAppUpdateState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    when (val state = inAppUpdateState.appUpdateResult) {
        AppUpdateResult.NotAvailable -> content()
        is AppUpdateResult.Available -> {
            var showContent by remember {
                mutableStateOf(false)
            }
            if (showContent) content()
            else UpdateRequiredView(onCancelled = {
                showContent=true
            }, update =  {
                state.startFlexibleUpdate(context as MainActivity, APP_UPDATE_REQUEST_CODE)
            })
        }
        is AppUpdateResult.InProgress -> {
            UpdateInProgress(
                progress = state.installState.bytesDownloaded()
                    .toFloat() / state.installState.totalBytesToDownload().toFloat()
            )
        }
        is AppUpdateResult.Downloaded -> {
            UpdateDownloadedView {
                scope.launch {
                    state.completeUpdate()
                }
            }
        }
    }
}


@Composable
internal fun UpdateDownloadedView(update: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.in_app_update_compose_update_complete_title),
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.W700)
        )

        Text(
            text = stringResource(id = R.string.in_app_update_compose_update_complete_body),
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.W700)
        )

        Spacer(modifier = Modifier.height(50.dp))

        Button(modifier = Modifier,
            onClick = { update() }) {
            Text(
                text = stringResource(id = R.string.in_app_update_compose_update_complete_button),
            )
        }
    }
}