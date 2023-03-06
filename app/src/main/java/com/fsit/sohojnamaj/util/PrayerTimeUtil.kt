package com.fsit.sohojnamaj.util

import com.fsit.sohojnamaj.database.dao.FakePrayerDao
import com.fsit.sohojnamaj.database.dao.PrayerDao
import com.fsit.sohojnamaj.model.PrayerRange
import com.fsit.sohojnamaj.util.dateUtil.*
import java.util.*
import javax.inject.Inject

class PrayerTimeUtil (
    private  val waqtData: WaqtPrayerTimeUtil= WaqtPrayerTimeUtil(prayerDao = FakePrayerDao()),
){

    val prayerNameList = arrayListOf("Fajr","Forbidden Time for Morning", "Salatul Duha","Forbidden Time for Noon","Dhur","Asr","Forbidden Time for Evening","Magrib","Isha","Next Fajr")
    val fajrRange = PrayerRange(0,start = waqtData.fajr,waqtData.sunrise-1.toMilisFromMinutes())
    val morningForbiddenRange =PrayerRange(1,start = waqtData.sunrise,waqtData.sunrise+15.toMilisFromMinutes())
    val duhaRange= PrayerRange(2,morningForbiddenRange.end+1.toMilisFromMinutes(),waqtData.Dhuhr-6.toMilisFromMinutes())
    val noonForbiddern= PrayerRange(3,duhaRange.end+1.toMilisFromMinutes(),waqtData.Dhuhr-1.toMilisFromMinutes())
    val dhurRange = PrayerRange(4,waqtData.Dhuhr,waqtData.Asr-1.toMilisFromMinutes())
    val asrRange= PrayerRange(5,waqtData.Asr,waqtData.Maghrib-16.toMilisFromMinutes())
    val evenningForbidden = PrayerRange(6,asrRange.end,waqtData.Maghrib-1.toMilisFromMinutes())
    val magribRange = PrayerRange(7,waqtData.Maghrib,waqtData.Isha-1.toMilisFromMinutes())
    val isha = PrayerRange(8,waqtData.Isha,waqtData.nextFajr-1.toMilisFromMinutes())
    val nextFajrRange = PrayerRange(id=9,start=waqtData.nextFajr,end=waqtData.nextSunrise-1.toMilisFromMinutes())

    val morningForbidden =waqtData.sunrise
    val duha =morningForbidden+15.toMilisFromMinutes()

    val rangeArray = arrayOf(fajrRange,morningForbiddenRange,duhaRange,noonForbiddern,dhurRange,asrRange,evenningForbidden,magribRange,isha)
    val nextPrayerRange = arrayOf(fajrRange,duhaRange,dhurRange,asrRange,magribRange,isha,nextFajrRange)

    val prayerArray = arrayListOf(fajrRange.start,morningForbidden,duha,waqtData.Dhuhr,waqtData.Asr,waqtData.Maghrib,waqtData.Isha,waqtData.nextFajr)

    val prayerName = arrayListOf("Fajr", "Duha", "Dhuhr", "Asr", "Maghrib", "Isha", "nextFaj")


    fun currentPrayName(raw: Calendar = Calendar.getInstance()):PrayerRange?{
for(prayer in rangeArray){
            if(prayer.start<=System.currentTimeMillis()&&prayer.end>=System.currentTimeMillis()) {
                return prayer
            }
        }
        return null
    }
    fun nextPrayName(raw: Calendar = Calendar.getInstance()):PrayerRange?{

for(prayer in nextPrayerRange){

            if(prayer.start<=raw.timeInMillis&&prayer.end>=raw.timeInMillis) {
                return nextPrayerRange[nextPrayerRange.indexOf(prayer)+1]
            }
        }
        return null
    }



}