package com.fsit.sohojnamaj.util.dateUtil

import android.util.Log
import com.fsit.sohojnamaj.model.PrayerRange
import com.fsit.sohojnamaj.util.calender.bangla.translateNumbersToBangla
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun today(
     calender: Calendar = Calendar.getInstance(),
 ): String {
    val today = SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).format(calender.time)
    return today
}
fun tomorrow(
    calender: Calendar = Calendar.getInstance(),

    ): String {
    calender.add(Calendar.DAY_OF_MONTH,1)
    val today = SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).format(calender.time)
    return today
}
fun yesterday(
    calender: Calendar = Calendar.getInstance(),

    ): String {
    calender.add(Calendar.DAY_OF_MONTH,-1)
    val today = SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).format(calender.time)
    return today
}
fun String.toISO8601Date(): Long {
    val df1: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
    val string1 = this.substringBefore("+")
    return df1.parse(string1).time
}

fun List<Long?>.findClosest(input: Long) = fold(null) { acc: Long?, num ->
    val closest = if (num!!<= input && (acc == null || num!! > acc)) num else acc
    if (closest == input)
        return@findClosest closest
    else return@fold closest
}

fun Long.toDateFormat()=SimpleDateFormat("HH:mm dd MMM yyyy", Locale.getDefault()).format(this)
fun PrayerRange?.toTimeFormat():String{
    return if(this!=null){
        "${SimpleDateFormat("hh:mm a", Locale.getDefault()).format(this.start)} - ${SimpleDateFormat("hh:mm a", Locale.getDefault()).format(this.end)}"
    }
    else return "-"
}
fun PrayerRange?.timeLeft():String{
    return if(this!=null){
        Log.i(
            "123321",
            "timeLeft: System = ${System.currentTimeMillis()} target= ${this.end}  difference= ${this.end - System.currentTimeMillis()} which is "
        + SimpleDateFormat("hh:mm", Locale.getDefault()).format((this.end-System.currentTimeMillis()))
        )

        val calendar = Calendar.getInstance()

        var different: Long = this.end - System.currentTimeMillis()


        val secondsInMilli: Long = 1000
        val minutesInMilli = secondsInMilli * 60
        val hoursInMilli = minutesInMilli * 60


        val elapsedHours = different / hoursInMilli
        different = different % hoursInMilli

        val elapsedMinutes = different / minutesInMilli
        different = different % minutesInMilli


        "${translateNumbersToBangla(elapsedHours.toString())} ঘন্টা ${translateNumbersToBangla(elapsedMinutes.toString())} মিনিট"
    }
    else return "-"
}
fun Int.toMilisFromMinutes()=this*1000L
