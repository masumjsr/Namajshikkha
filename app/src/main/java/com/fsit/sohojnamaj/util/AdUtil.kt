package com.fsit.sohojnamaj.util

import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.fsit.sohojnamaj.BuildConfig
import com.fsit.sohojnamaj.R
import com.google.android.ads.nativetemplates.NativeTemplateStyle
import com.google.android.ads.nativetemplates.TemplateView
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback


var mInterstitialAd: InterstitialAd? = null


 val ADMOB_APP_OPEN_ID =if(!BuildConfig.DEBUG) "ca-app-pub-1390178544592179/9439269802" else "ca-app-pub-3940256099942544/3419835294"
 val ADMOB_INTERSTITIAL_ID =if(!BuildConfig.DEBUG) "ca-app-pub-1390178544592179/5904970041" else "ca-app-pub-3940256099942544/1033173712"
 val ADMOB_NATIVE_AD_ID = if(!BuildConfig.DEBUG)"ca-app-pub-7132529534932682/7174963923" else "ca-app-pub-3940256099942544/2247696110"
val ADMOB_BANNER_AD_ID = if(!BuildConfig.DEBUG)"ca-app-pub-1390178544592179/9345004689" else "ca-app-pub-3940256099942544/6300978111"



fun loadInterstitial(context: Context) {
    Log.i("123321", "loadInterstitial: loading admob ad")
    InterstitialAd.load(
        context,
        //"ca-app-pub-3940256099942544/1033173712", //Change this with your own AdUnitID!
        ADMOB_INTERSTITIAL_ID,
        AdRequest.Builder().build(),
        object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.i("123321", "onAdFailedToLoad: interstital  reason :${adError.message}")
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
            }
        }
    )
}
fun Context.findActivity(): ComponentActivity? = when (this) {
    is ComponentActivity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

fun showInterstitial(context: Context, onAdDismissed: () -> Unit) {
    Log.i("123321", "showInterstitial: showinterstitial")
    val activity = context.findActivity()

    if (mInterstitialAd != null && activity != null) {
        mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdFailedToShowFullScreenContent(e: AdError) {

                onAdDismissed()
                mInterstitialAd = null
            }

            override fun onAdDismissedFullScreenContent() {
                mInterstitialAd = null

                onAdDismissed()
            }
        }
        mInterstitialAd?.show(activity)
    }
    else

        onAdDismissed()

}

@Composable
fun BannerAds(
    modifier: Modifier,
    adListener: AdListener? = null
) {

    val deviceCurrentWidth = LocalConfiguration.current.screenWidthDp
    val padding = 1
    var i by remember { mutableStateOf(0) }
    var containerWidth by remember { mutableStateOf<Int?>(null) }

    val context = LocalContext.current
    val adUnitId = ADMOB_BANNER_AD_ID

    Box(
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()
            .background(color = Color.White)
            .onSizeChanged {
                containerWidth = it.width
            },
        contentAlignment = Alignment.Center
    ) {

        Text(text = "Showing Ad")
        AndroidView(factory = {
            AdView(it)
        }, modifier = modifier, update = {
            if (it.adSize == null)
                it.setAdSize(
                    AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(
                        context,
                        deviceCurrentWidth - padding * 2
                    )
                )
            try {
                it.adUnitId = adUnitId //Can only be set once
            } catch (e: Exception) {
                e.printStackTrace()
            }
            if (adListener != null)
                it.adListener = adListener

            it.loadAd(AdRequest.Builder().build())
        })

    }
}
@Composable
 fun AdmobBanner() {
    val context= LocalView.current.context
    AndroidView(
        modifier = Modifier
            .padding(top = 10.dp, bottom = 10.dp)
            .fillMaxWidth(),
        factory = { context ->

            val view = LayoutInflater.from(context).inflate(R.layout.ad_layout, null, false)



            // do whatever you want...
            view // return the view
        },
        update = { view ->

            val adLoader: AdLoader = AdLoader.Builder(context, ADMOB_NATIVE_AD_ID)
                .forNativeAd { nativeAd ->

                    val styles =
                        NativeTemplateStyle.Builder()
                            .build()
                    val template: TemplateView = view.findViewById(R.id.my_template)
                    template.setStyles(styles)
                    template.setNativeAd(nativeAd)
                }
                .build()

            adLoader.loadAd(AdRequest.Builder().build())

            // Update the view

        }
    )
}