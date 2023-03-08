package com.fsit.sohojnamaj

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class PrayerTime(var address: String? = null, var fajr: String?="", var sunrise: String? = "", var dhuhr: String? = "", var asr: String? = "", var sunset: String? = "", var maghrib: String? = "", var isya: String? = ""):
    Parcelable

