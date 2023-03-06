package com.fsit.sohojnamaj.ui.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fsit.sohojnamaj.data.repository.NetworkRepository
import com.fsit.sohojnamaj.data.repository.PrayerRepository
import com.fsit.sohojnamaj.model.*
import com.fsit.sohojnamaj.util.dateUtil.*
import com.fsit.sohojnamaj.util.praytimes.PrayTime
import com.fsit.sohojnamaj.util.sync.combine
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val networkRepository: NetworkRepository,
    private val prayerRepository: PrayerRepository,
) : ViewModel(){

    val todayPrayer =  prayerRepository.prayer(today())
    val yesterdayPrayer =  prayerRepository.prayer(yesterday())
    val systemTime= MutableStateFlow(System.currentTimeMillis())
    val tomorrowPrayer =  prayerRepository.prayer(tomorrow())

    private val combineFlow=combine(todayPrayer,yesterdayPrayer,tomorrowPrayer,systemTime){ waqtData, yesterday, tomorrowPrayer,systemtime->

        val fajrRange = PrayerRange(0,start = waqtData.Fajr.toISO8601Date(),waqtData.Sunrise.toISO8601Date()-1.toMilisFromMinutes())
        val morningForbiddenRange =PrayerRange(1,start = waqtData.Sunrise.toISO8601Date(),waqtData.Sunrise.toISO8601Date()+15.toMilisFromMinutes())
        val duhaRange= PrayerRange(2,morningForbiddenRange.end+1.toMilisFromMinutes(),waqtData.Dhuhr.toISO8601Date()-6.toMilisFromMinutes())
        val noonForbiddern= PrayerRange(3,duhaRange.end+1.toMilisFromMinutes(),waqtData.Dhuhr.toISO8601Date()-1.toMilisFromMinutes())
        val dhurRange = PrayerRange(4,waqtData.Dhuhr.toISO8601Date(),waqtData.Asr.toISO8601Date()-1.toMilisFromMinutes())
        val asrRange= PrayerRange(5,waqtData.Asr.toISO8601Date(),waqtData.Maghrib.toISO8601Date()-16.toMilisFromMinutes())
        val evenningForbidden = PrayerRange(6,asrRange.end,waqtData.Maghrib.toISO8601Date()-1.toMilisFromMinutes())
        val magribRange = PrayerRange(7,waqtData.Maghrib.toISO8601Date(),waqtData.Isha.toISO8601Date()-1.toMilisFromMinutes())
        val isha = PrayerRange(8,waqtData.Isha.toISO8601Date(),tomorrowPrayer.Fajr.toISO8601Date()-1.toMilisFromMinutes())
        val previousIsha = PrayerRange(10,yesterday.Isha.toISO8601Date(),waqtData.Fajr.toISO8601Date()-1.toMilisFromMinutes())
        val nextFajrRange = PrayerRange(id=9,start=tomorrowPrayer.Fajr.toISO8601Date(),end=tomorrowPrayer.Sunrise .toISO8601Date()-1.toMilisFromMinutes())



        AllPrayerRange(
            previousIsha=previousIsha,
            fajrRange=fajrRange,
            morningForbiddenRange=morningForbiddenRange,
            duhaRange=duhaRange,
            noonForbidden =noonForbiddern,
            dhurRange=dhurRange,
            asrRange = asrRange,
            eveningForbidden = evenningForbidden,
            magribRange=magribRange,
            ishaRange = isha,
            nextFajrRange = nextFajrRange)

    }


    init {
        Log.i("123321", ": ")
        viewModelScope.launch {
            while (true) {
                delay(60*1000)
                systemTime.value=System.currentTimeMillis()
            }


        }

    }
fun setupAlerm(context: Context) {
    viewModelScope.launch {
        Log.i("123321", "inside scope: ")
        combine(todayPrayer, tomorrowPrayer) { waqtData, tomorrowPrayer ->


            val alermModel = AlarmModel(
                fajr = waqtData.Fajr.toISO8601Date(),
                dhur = waqtData.Dhuhr.toISO8601Date(),
                asr = waqtData.Asr.toISO8601Date(),
                magrib = waqtData.Maghrib.toISO8601Date(),
                isha = waqtData.Isha.toISO8601Date(),
                nextFajr = tomorrowPrayer.Fajr.toISO8601Date()
            )
            PrayTime.schedule(context, alermModel)


        }
            .collectLatest {  }

    }
}


    private val prayerNameList = arrayListOf("এশা","ফযর", "নিষিদ্ধ সময় ","সালাতুল দুহা","নিষিদ্ধ সময়","যুহর","আসর","নিষিদ্ধ সময়","মাগরিব","এশা","ফযর")

    val currentPrayer :StateFlow<Prayer> = combineFlow

        .filterNot {
            it.nextFajrRange.start==-1L
        }
        .map {

            val rangeArray = arrayOf(it.previousIsha,it.fajrRange,it.morningForbiddenRange,it.duhaRange,it.noonForbidden,it.dhurRange,it.asrRange,it.eveningForbidden,it.magribRange,it.ishaRange,it.nextFajrRange)
            val nextPrayerRange = arrayOf(it.fajrRange,it.duhaRange,it.dhurRange,it.asrRange,it.magribRange,it.ishaRange,it.nextFajrRange)
            val startRangeArray= arrayListOf(
                it.previousIsha.start,
                it.fajrRange.start,
                it.morningForbiddenRange.start,
                it.duhaRange.start,
                it.noonForbidden.start,
                it.dhurRange.start,
                it.asrRange.start,
                it.eveningForbidden.start,
                it.magribRange.start,
                it.ishaRange.start,
                it.nextFajrRange.start
            )
            val closest= startRangeArray.findClosest(System.currentTimeMillis())
            val nearest=startRangeArray.indexOf(closest)

         Prayer(
             name = prayerNameList[nearest],
             prayer = rangeArray[nearest],
             text= rangeArray[nearest].toTimeFormat(),
             timeLeft = rangeArray[nearest].timeLeft(),
             progress = ((100*( System.currentTimeMillis()- rangeArray[nearest].start)/(rangeArray[nearest].end - rangeArray[nearest].start))).toFloat()/100f
         )



        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = Prayer()

        )




}