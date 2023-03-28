package com.fsit.sohojnamaj.util.calender.bangla

import androidx.compose.ui.text.intl.Locale
import com.aminography.primecalendar.hijri.HijriCalendarUtils
import com.fsit.sohojnamaj.util.calender.primecalendar.hijri.HijriCalendar
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.floor

class Bongabdo {
    var bDay=0
    var bMonth=0
    var bYear=0
    var bSeason=0
    var bWeekDay=0
    var version="new"

   fun Bongabdo.addMonth(year :Int, month: Int) {
        bYear = if(month % 12 == 0)  year - 1 else year;
        bMonth = if(month % 12 == 0)  12 else month % 12;
        bDay = 1;
    }
    fun isnew() =this.version == "new0";

   fun now(locale: Locale=Locale.current):String {
        val today = Calendar.getInstance()
       today.timeInMillis=System.currentTimeMillis()
       return toBanglaDate(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH),locale);
    }

   fun toBanglaDate(gYear: Int, gMonth: Int, gDay: Int, locale: Locale): String {
        var totalDaysInMonth = totalDaysInMonthNew

       if( isLeapYear(gYear) && isnew())
         totalDaysInMonth[10] = 29
        else totalDaysInMonth[10] = 31

        /* if (isLeapYear(gYear)){
          totalDaysInMonth[10] = 31;
        } */

        val banglaYear =
        if((gMonth < 3 || (gMonth == 3 && gDay < 14)))  gYear - 594 else gYear - 593;

        val epochYear =
        if((gMonth < 4 || (gMonth == 4 && gDay < 14)))  gYear - 1 else gYear;

       val banglaCalendar=Calendar.getInstance()
           banglaCalendar.set(gYear,gMonth,gDay)
       val epochCalendar=Calendar.getInstance()
           epochCalendar.set(epochYear,4,13)
       val diff =banglaCalendar.timeInMillis-epochCalendar.timeInMillis



        var difference = TimeUnit.DAYS.convert(diff,TimeUnit.MILLISECONDS)

        var banglaMonthIndex = 0;


       for (i in 1..12){
           if (difference <= totalDaysInMonth[i]) {
               banglaMonthIndex = i;
               break
           }
           difference -= totalDaysInMonth[i];


       }





        var banglaDate = difference;

        var banglaSeason = banglaSeasons[floor((banglaMonthIndex / 2.0)).toInt()];

        var banglaWeekDay = banglaCalendar.get(Calendar.DAY_OF_WEEK);
       print(banglaWeekDay)

       return when (locale.language) {
           HijriCalendar.DEFAULT_LOCALE -> {
               bDate(
                   translateNumbersToBangla(banglaYear.toString()), banglaMonths[banglaMonthIndex], translateNumbersToBangla(banglaDate.toString()),
                   banglaWeekDays[banglaWeekDay]!!, banglaSeason);
           }
           else -> {
               bDate(
                  banglaYear.toString(), banglaMonthsEn[banglaMonthIndex], banglaDate.toString(),
                   banglaWeekDays[banglaWeekDay]!!, banglaSeason);
           }
       }

    }


    fun bDate(year:String, month:String, day:String, weekday:String, season:String):String {


        return "$day $month $year"
    }

}