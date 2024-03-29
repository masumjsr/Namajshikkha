package com.fsit.sohojnamaj.util.praytimes

import android.content.Context
import android.location.Geocoder
import android.net.ConnectivityManager
import android.util.Log
import androidx.databinding.ObservableField
import com.google.android.gms.maps.model.LatLng
import com.fsit.sohojnamaj.constants.LocaleConstants.ASR
import com.fsit.sohojnamaj.constants.LocaleConstants.DHUHR
import com.fsit.sohojnamaj.constants.LocaleConstants.FAJR
import com.fsit.sohojnamaj.constants.LocaleConstants.HOUR
import com.fsit.sohojnamaj.constants.LocaleConstants.ISYA
import com.fsit.sohojnamaj.constants.LocaleConstants.LEFT_UNTIL
import com.fsit.sohojnamaj.constants.LocaleConstants.MAGHRIB
import com.fsit.sohojnamaj.constants.LocaleConstants.MINUTE
import com.fsit.sohojnamaj.constants.LocaleConstants.NEXT_PRAYER_S
import com.fsit.sohojnamaj.constants.LocaleConstants.locale
import com.fsit.sohojnamaj.data.Prefs
import com.fsit.sohojnamaj.PrayerTime
import com.fsit.sohojnamaj.util.LocaleProvider
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

/**
 * Created by Mayburger on 2/15/19.
 */

object PrayerTimeHelper {



    fun getPrayerTimeFromPrefs(
        mContext: Context,
        cal: Calendar = Calendar.getInstance(),
        offset: IntArray = intArrayOf(),
        latLng: LatLng,
        method: Int,
        majhab: Int
    ): PrayerTime {
        getCurrentPrayerTimeString(mContext, latLng,cal,offset,method,majhab)
        return Prefs.praytime
    }



    fun isNetworkAvailable(ctx: Context): Boolean {
        val connectivityManager =
            ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    fun PrayerTime.getTimeUntilNextPrayerString(): String {
        val now = SimpleDateFormat("HH:mm").format(Calendar.getInstance().time)
        return when {
            now < fajr!! -> countTimeLight(fajr!!, LocaleProvider.getInstance().getString(FAJR))
            now < dhuhr!! -> countTimeLight(dhuhr!!, LocaleProvider.getInstance().getString(DHUHR))
            now < asr!! -> countTimeLight(asr!!, LocaleProvider.getInstance().getString(ASR))
            now < maghrib!! -> countTimeLight(
                maghrib!!,
                LocaleProvider.getInstance().getString(MAGHRIB)
            )
            now < isya!! -> countTimeLight(isya!!, LocaleProvider.getInstance().getString(ISYA))
            else -> countTimeLight(fajr!!, LocaleProvider.getInstance().getString(FAJR))
        }
    }

    fun getNextPrayer(nextPrayer: ObservableField<String>, now: String, p1: PrayerTime) {
        when {
            now < p1.fajr!! -> nextPrayer.set(LocaleProvider.getInstance().getString(FAJR))
            now < p1.dhuhr!! -> nextPrayer.set(LocaleProvider.getInstance().getString(DHUHR))
            now < p1.asr!! -> nextPrayer.set(LocaleProvider.getInstance().getString(ASR))
            now < p1.maghrib!! -> nextPrayer.set(
                LocaleProvider.getInstance().getString(MAGHRIB)
            )
            now < p1.isya!! -> nextPrayer.set(LocaleProvider.getInstance().getString(ISYA))
            else -> {
                nextPrayer.set(LocaleProvider.getInstance().getString(FAJR))
            }
        }
    }

    fun PrayerTime.getCurrentPrayerTimeString(): String? {
        val now = SimpleDateFormat("HH:mm").format(Calendar.getInstance().time)

        return when {
            now < fajr!! -> fajr
            now < dhuhr!! -> dhuhr
            now < asr!! -> asr
            now < maghrib!! -> maghrib
            now < isya!! -> isya
            else -> {
                fajr
            }
        }
    }

    fun PrayerTime.getPreviousPrayerTimeString(): String? {
        val now = SimpleDateFormat("HH:mm").format(Calendar.getInstance().time)
        return when {
            now < fajr!! -> isya
            now < dhuhr!! -> fajr
            now < asr!! -> dhuhr
            now < maghrib!! -> asr
            now < isya!! -> maghrib
            else -> {
                fajr
            }
        }
    }
    fun PrayerTime.getCurrentPrayerName(): String? {
        val now = SimpleDateFormat("HH:mm").format(Calendar.getInstance().time)
        return when {
            now < fajr!! -> FAJR.locale()
            now < dhuhr!! -> DHUHR.locale()
            now < asr!! -> ASR.locale()
            now < maghrib!! -> MAGHRIB.locale()
            now < isya!! -> ISYA.locale()
            else -> {
                FAJR.locale()
            }
        }
    }
    fun PrayerTime.getPreviousPrayerName(): String? {
        val now = SimpleDateFormat("HH:mm").format(Calendar.getInstance().time)
        return when {
            now < fajr!! -> ISYA.locale()
            now < dhuhr!! -> FAJR.locale()
            now < asr!! -> DHUHR.locale()
            now < maghrib!! -> ASR.locale()
            now < isya!! -> MAGHRIB.locale()
            else -> {
                FAJR.locale()
            }
        }
    }

    fun countTime(endTime: String): String {
        val cal = Calendar.getInstance()
        val nowHour = cal.get(Calendar.HOUR_OF_DAY)
        val nowMin = cal.get(Calendar.MINUTE)
        val m = Pattern.compile("(\\d{2}):(\\d{2})").matcher(endTime)
        require(m.matches()) { "Invalid time format: $endTime" }
        val endHour = Integer.parseInt(m.group(1)!!)
        val endMin = Integer.parseInt(m.group(2)!!)
        require(!(endHour >= 24 || endMin >= 60)) { "Invalid time format: $endTime" }
        var minutesLeft = endHour * 60 + endMin - (nowHour * 60 + nowMin)
        if (minutesLeft < 0)
            minutesLeft += 24 * 60 // Time passed, so time until 'end' tomorrow
        val hours = minutesLeft / 60
        val minutes = minutesLeft - hours * 60
        return "${LocaleProvider.getInstance().getString(NEXT_PRAYER_S)} " +
                "$hours ${LocaleProvider.getInstance().getString(HOUR)} " +
                "$minutes ${LocaleProvider.getInstance().getString(MINUTE)}"
//            return hours.toString() + "h " + minutes + "m ${StringProvider.getInstance().getString(LEFT_UNTIL)} " + prayer
    }

    fun countTimeLight(endTime: String, prayer: String): String {
        val cal = Calendar.getInstance()
        val nowHour = cal.get(Calendar.HOUR_OF_DAY)
        val nowMin = cal.get(Calendar.MINUTE)
        val m = Pattern.compile("(\\d{2}):(\\d{2})").matcher(endTime)
        require(m.matches()) { "Invalid time format: $endTime" }
        val endHour = Integer.parseInt(m.group(1)!!)
        val endMin = Integer.parseInt(m.group(2)!!)
        require(!(endHour >= 24 || endMin >= 60)) { "Invalid time format: $endTime" }
        var minutesLeft = endHour * 60 + endMin - (nowHour * 60 + nowMin)
        if (minutesLeft < 0)
            minutesLeft += 24 * 60 // Time passed, so time until 'end' tomorrow
        val hours = minutesLeft / 60
        val minutes = minutesLeft - hours * 60
//            return "${StringProvider.getInstance().getString(TIME_LEFT_UNTIL)}$prayer: " +
//                    "$hours ${StringProvider.getInstance().getString(HOUR)} " +
//                    "$minutes ${StringProvider.getInstance().getString(MINUTE)}"

        val minutesString =
            if (minutes != 0) "$minutes ${LocaleProvider.getInstance().getString(MINUTE)} " else ""

        return hours.toString() + " ${
            LocaleProvider.getInstance().getString(HOUR)
        } $minutesString${LocaleProvider.getInstance().getString(LEFT_UNTIL)} " + prayer
    }


    fun getCurrentPrayerTimeString(
        ctx: Context,
        latLng: LatLng,
        cal: Calendar = Calendar.getInstance(),
        offset: IntArray,
        method: Int,
        majhab: Int
    ): PrayerTime {
        Prefs.userCoordinates = latLng
        var address: String? = "Cannot get location"

        try {
            if (isNetworkAvailable(ctx)) {
                val gcd = Geocoder(ctx, Locale.getDefault())
                val addresses = gcd.getFromLocation(latLng.latitude, latLng.longitude, 1)!!
                if (addresses.size > 0) {
                    address = if(addresses[0].thoroughfare!=null)addresses[0].thoroughfare else "অজানা"
                    Prefs.userCity = address?:"অজানা"
                }
            } else {
                if (Prefs.userCity != "") {
                    address = Prefs.userCity
                }
            }
        } catch (e: Exception) {
            Log.i("123321", "getCurrentPrayerTimeString: expection while get lcoation name =${e.message}")
        }

        val prayers = PrayTimeScript()
        prayers.setTimeFormat(prayers.Time24)
        prayers.setCalcMethod(method+1)

        prayers.setAsrJuristic(if(majhab==0)1 else 0)
        prayers.setAdjustHighLats(Prefs.higherLatitudes)
        val offsets = intArrayOf(
            Prefs.fajrOffset,
            2,
            Prefs.dhuhrOffset,
            Prefs.asrOffset,
            2,
            Prefs.maghribOffset,
            Prefs.isyaOffset
        )

        prayers.tune(offset)
        val mCalendar = GregorianCalendar()
        val mTimeZone = mCalendar.timeZone
        val mGMTOffset = mTimeZone.rawOffset



        val prayerTimes = prayers.getPrayerTimes(
            cal,
            latLng.latitude,
            latLng.longitude,
            TimeUnit.HOURS.convert(mGMTOffset.toLong(), TimeUnit.MILLISECONDS).toDouble()
        )
        val prayerTime = PrayerTime(
            address,
            prayerTimes[0],
            prayerTimes[1],
            prayerTimes[2],
            prayerTimes[3],
            prayerTimes[4],
            prayerTimes[5],
            prayerTimes[6]
        )
        Prefs.praytime = prayerTime
        return prayerTime
    }

}
