package com.fsit.sohojnamaj.ui.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fsit.sohojnamaj.data.repository.LocalRepository
import com.fsit.sohojnamaj.data.repository.NetworkRepository
import com.fsit.sohojnamaj.data.repository.PrayerRepository
import com.fsit.sohojnamaj.data.repository.PrayerSettingRepository
import com.fsit.sohojnamaj.model.*
import com.fsit.sohojnamaj.util.dateUtil.*
import com.fsit.sohojnamaj.util.praytimes.PrayerTimeHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val networkRepository: NetworkRepository,
    private val prayerRepository: PrayerRepository,
    private val localRepository: LocalRepository,
    private val prayerSettingRepository: PrayerSettingRepository
) : ViewModel(){

    val userData=localRepository.userData

    val currentWaqt= userData
        .map {waqtData->

            val fajrRange = PrayerRange(0,start = waqtData.fajr.toDate(),waqtData.sunrise.toDate()-1.toMilisFromMinutes())
            val morningForbiddenRange =PrayerRange(1,start = waqtData.sunrise.toDate(),waqtData.sunrise.toDate()+15.toMilisFromMinutes())
            val duhaRange= PrayerRange(2,morningForbiddenRange.end+1.toMilisFromMinutes(),waqtData.dhur.toDate()-6.toMilisFromMinutes())
            val noonForbiddern= PrayerRange(3,duhaRange.end+1.toMilisFromMinutes(),waqtData.dhur.toDate()-1.toMilisFromMinutes())
            val dhurRange = PrayerRange(4,waqtData.dhur.toDate(),waqtData.asr.toDate()-1.toMilisFromMinutes())
            val asrRange= PrayerRange(5,waqtData.asr.toDate(),waqtData.maghrib.toDate()-16.toMilisFromMinutes())
            val evenningForbidden = PrayerRange(6,asrRange.end,waqtData.maghrib.toDate()-1.toMilisFromMinutes())
            val magribRange = PrayerRange(7,waqtData.maghrib.toDate(),waqtData.isha.toDate()-1.toMilisFromMinutes())
            val isha = PrayerRange(8,waqtData.isha.toDate(),waqtData.nextFajr.toDate()-1.toMilisFromMinutes())
            val previousIsha = PrayerRange(10,waqtData.previousIsha.toDate(),waqtData.fajr.toDate()-1.toMilisFromMinutes())
            val nextFajrRange = PrayerRange(id=9,start=waqtData.nextFajr.toDate(),end=-1)
            val nextMagribRange = PrayerRange(7,waqtData.nextMagrib.toDate(),-1)




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
                nextFajrRange = nextFajrRange,
                nextMagribRange=nextMagribRange
            )


            val rangeArray = arrayOf(previousIsha,fajrRange,morningForbiddenRange,duhaRange,noonForbiddern,dhurRange,asrRange,evenningForbidden,magribRange,isha,nextFajrRange)

            val startRangeArray= arrayListOf(
                previousIsha.start,
                fajrRange.start,
                morningForbiddenRange.start,
                duhaRange.start,
                noonForbiddern.start,
                dhurRange.start,
                asrRange.start,
                evenningForbidden.start,
                magribRange.start,
                isha.start,
                nextFajrRange.start
            )
            val closest= startRangeArray.findClosest(System.currentTimeMillis())
            val nearest=startRangeArray.indexOf(closest)
            val forbiddenRange=nearest==1||nearest==3||nearest==6
            val forbiddenRange2=nearest==2||nearest==4||nearest==7
            val next =if( forbiddenRange)nearest+2 else nearest+1

            val isIftarOver=System.currentTimeMillis()>magribRange.start


            val nextSahri=if(isIftarOver.not())fajrRange.start else nextFajrRange.start
            val nextIftar =if(isIftarOver.not())magribRange.start else nextMagribRange.start
            val timeleft =if(isIftarOver.not())nextIftar.timeLeft() else nextSahri.timeLeft()



            Prayer(
                name = prayerNameList[nearest],
                prayer = rangeArray[nearest],
                text= rangeArray[nearest].toTimeFormat(),
                timeLeft = rangeArray[nearest].timeLeft(),
                progress = ((100*( System.currentTimeMillis()- rangeArray[nearest].start)/(rangeArray[nearest].end - rangeArray[nearest].start))).toFloat()/100f,
                next = prayerNameList[next],
                nextText = rangeArray[next].toTimeFormat(),
                forbiddenRange = forbiddenRange2,
                isIfterOver = isIftarOver,
                nextIfter = nextIftar.toTimeFormat(),
                nextSahari = nextSahri.toTimeFormat(),
                nextTimeLeft = timeleft,
                forbiddenTime = arrayListOf(
                    ForbiddenTime("নিষিদ্ধ সময়(সকাল)",morningForbiddenRange.toTimeFormat()),
                    ForbiddenTime("নিষিদ্ধ সময় (দুপুর)",noonForbiddern.toTimeFormat()),
                    ForbiddenTime("নিষিদ্ধ সময় (সন্ধ্যা)",evenningForbidden.toTimeFormat())
                )
            )



        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = Prayer()

    )

    private val todayPrayer =  prayerRepository.prayer(today())
    private val yesterdayPrayer =  prayerRepository.prayer(yesterday())
    val systemTime= MutableStateFlow(System.currentTimeMillis())
    private val tomorrowPrayer =  prayerRepository.prayer(tomorrow())

    private val combineFlow=combine(todayPrayer,yesterdayPrayer,tomorrowPrayer,systemTime){ waqtData, yesterday, tomorrowPrayer,systemtime->

        if(waqtData==null ||yesterday==null||tomorrowPrayer==null)null
       else {
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
            val nextMagribRange = PrayerRange(7,tomorrowPrayer.Maghrib.toISO8601Date(),tomorrowPrayer.Isha.toISO8601Date()-1.toMilisFromMinutes())




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
                nextFajrRange = nextFajrRange,
                nextMagribRange=nextMagribRange
            )
        }

    }


    init {
        Log.i("123321", ": ")
        viewModelScope.launch {
            /*while (true) {
                delay(60*1000)
                systemTime.value=System.currentTimeMillis()
            }*/


          prayerSettingRepository.prayerPreferenceData.collectLatest {


              val yesterday=Calendar.getInstance()
              yesterday.add(Calendar.DAY_OF_MONTH,-1)
              val today =Calendar.getInstance()
              today.timeInMillis=System.currentTimeMillis()
              val tommorrow =Calendar.getInstance()
              tommorrow.add(Calendar.DAY_OF_MONTH,1)
              val offset= intArrayOf(
                  it.offsetModel.fajr,
                  it.offsetModel.sunrise,
                  it.offsetModel.dhur,
                  it.offsetModel.asr,it.offsetModel.sunset,it.offsetModel.magrib,it.offsetModel.isha)

              val praytime = PrayerTimeHelper.getPrayerTimeFromPrefs( context,today, offset =offset)
              val praytimeYesterDay = PrayerTimeHelper.getPrayerTimeFromPrefs(
                  context,
                  yesterday,
                  offset
              )
              val praytimeTommorow = PrayerTimeHelper.getPrayerTimeFromPrefs(
                  context,
                  tommorrow,
                  offset
              )

              localRepository.updateUserData(
                  UserData(
                      previousIsha = praytimeYesterDay.isya?:"",
                      fajr = praytime.fajr?:"",
                      dhur = praytime.dhuhr?:"",
                      asr = praytime.asr?:"",
                      maghrib = praytime.maghrib ?:"",
                      isha = praytime.isya?:"",
                      nextFajr = praytimeTommorow.fajr?:"",
                      nextMagrib = praytimeTommorow.maghrib?:""
                  )
              )



          }


        }

    }
fun setupAlerm(context: Context) {
    viewModelScope.launch {
        Log.i("123321", "inside scope: ")
        combine(todayPrayer, tomorrowPrayer) { waqtData, tomorrowPrayer ->


           if(waqtData!=null && tomorrowPrayer!=null){
               val alermModel = AlarmModel(
                   fajr = waqtData.Fajr.toISO8601Date(),
                   dhur = waqtData.Dhuhr.toISO8601Date(),
                   asr = waqtData.Asr.toISO8601Date(),
                   magrib = waqtData.Maghrib.toISO8601Date(),
                   isha = waqtData.Isha.toISO8601Date(),
                   nextFajr = tomorrowPrayer.Fajr.toISO8601Date()
               )
               //PrayTime.schedule(context, alermModel)
           }


        }
            .collectLatest {  }

    }
}


    private val prayerNameList = arrayListOf("এশা","ফজর", "নিষিদ্ধ সময় ","সালাতুল দুহা","নিষিদ্ধ সময়","যুহর","আসর","নিষিদ্ধ সময়","মাগরিব","এশা","ফজর")

    val currentPrayer :StateFlow<Prayer?> = combineFlow

        .filterNot {
            it==null
        }
        .map {

          it?.let {
              val rangeArray = arrayOf(it.previousIsha,it.fajrRange,it.morningForbiddenRange,it.duhaRange,it.noonForbidden,it.dhurRange,it.asrRange,it.eveningForbidden,it.magribRange,it.ishaRange,it.nextFajrRange)

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
              val forbiddenRange=nearest==1||nearest==3||nearest==6
              val forbiddenRange2=nearest==2||nearest==4||nearest==7
              val next =if( forbiddenRange)nearest+2 else nearest+1

              val isIftarOver=System.currentTimeMillis()>it.magribRange.start


              val nextSahri=if(isIftarOver.not())it.fajrRange.start else it.nextFajrRange.start
              val nextIftar =if(isIftarOver.not())it.magribRange.start else it.nextMagribRange.start
              val timeleft =if(isIftarOver.not())nextIftar.timeLeft() else nextSahri.timeLeft()



              Prayer(
                  name = prayerNameList[nearest],
                  prayer = rangeArray[nearest],
                  text= rangeArray[nearest].toTimeFormat(),
                  timeLeft = rangeArray[nearest].timeLeft(),
                  progress = ((100*( System.currentTimeMillis()- rangeArray[nearest].start)/(rangeArray[nearest].end - rangeArray[nearest].start))).toFloat()/100f,
                  next = prayerNameList[next],
                  nextText = rangeArray[next].toTimeFormat(),
                  forbiddenRange = forbiddenRange2,
                  isIfterOver = isIftarOver,
                  nextIfter = nextIftar.toTimeFormat(),
                  nextSahari = nextSahri.toTimeFormat(),
                  nextTimeLeft = timeleft,
                  forbiddenTime = arrayListOf(
                      ForbiddenTime("নিষিদ্ধ সময়(সকাল)",it.morningForbiddenRange.toTimeFormat()),
                      ForbiddenTime("নিষিদ্ধ সময় (দুপুর)",it.noonForbidden.toTimeFormat()),
                      ForbiddenTime("নিষিদ্ধ সময় (সন্ধ্যা)",it.eveningForbidden.toTimeFormat())
              )
              )
          }



        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = Prayer()

        )




}