package com.fsit.sohojnamaj.ui.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fsit.sohojnamaj.data.repository.LocalRepository
import com.fsit.sohojnamaj.data.repository.PrayerSettingRepository
import com.fsit.sohojnamaj.model.*
import com.fsit.sohojnamaj.util.dateUtil.*
import com.fsit.sohojnamaj.util.praytimes.PrayerTimeHelper
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val localRepository: LocalRepository,
    private val prayerSettingRepository: PrayerSettingRepository
) : ViewModel(){
    fun updateLocation(it: LatLng) {
       viewModelScope.launch {
           prayerSettingRepository.updateLocation(it)
       }
    }

    val userData=localRepository.userData
    val locationData:StateFlow<String?> =localRepository.userData

        .map {
            it.location

        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )

    val offsetData:StateFlow<Int> =prayerSettingRepository.prayerPreferenceData

        .map {
            it.hijri


        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = 0
        )

    val currentWaqt= userData
        .map {waqtData->

            val waqtDate=Calendar.getInstance();waqtDate.timeInMillis=waqtData.fajr.toDate()
            val todayDate=Calendar.getInstance()

                if(waqtData.location.isNotEmpty()&&waqtDate.get(Calendar.DAY_OF_MONTH)==todayDate.get(Calendar.DAY_OF_MONTH)){

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




                    val all=AllPrayerRange(
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


                    val rangeArray = arrayOf(
                        previousIsha,
                        fajrRange,
                        morningForbiddenRange,
                        duhaRange,
                        noonForbiddern,
                        dhurRange,
                        asrRange,
                        evenningForbidden,
                        magribRange,
                        isha,
                        nextFajrRange,
                        nextMagribRange
                    )


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
                        nextFajrRange.start,
                        nextMagribRange.start
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
                        ),
                        all= listOf(fajrRange,dhurRange,asrRange,magribRange,isha),

                    )
                }
            else Prayer()



        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = Prayer()

    )




    init {

        viewModelScope.launch {
            /*while (true) {
                delay(60*1000)
                systemTime.value=System.currentTimeMillis()
            }*/




          prayerSettingRepository.prayerPreferenceData.collectLatest {



           if(it.latLng!= LatLng(0.0,0.0)){
               val yesterday=Calendar.getInstance()
               yesterday.add(Calendar.DAY_OF_MONTH,-1)
               val today =Calendar.getInstance()
               today.timeInMillis=System.currentTimeMillis()
               val tommorrow =Calendar.getInstance()
               tommorrow.add(Calendar.DAY_OF_MONTH,1)
               val offset= intArrayOf(
                   it.offsetModel.fajr,
                   it.offsetModel.dhur,
                   it.offsetModel.asr,
                   it.offsetModel.magrib,
                   it.offsetModel.isha)

               val praytime = PrayerTimeHelper.getPrayerTimeFromPrefs( context,today, offset =offset,it.latLng, method = it.method,majhab=it.majhab)
               val praytimeYesterDay = PrayerTimeHelper.getPrayerTimeFromPrefs(
                   context,
                   yesterday,
                   offset,
                   it.latLng, it.method, it.majhab
               )
               val praytimeTommorow = PrayerTimeHelper.getPrayerTimeFromPrefs(
                   context,
                   tommorrow,
                   offset,
                   it.latLng, it.method, it.majhab
               )


               localRepository.updateUserData(
                   UserData(
                       previousIsha = praytimeYesterDay.isya?:"",
                       fajr = praytime.fajr?:"",
                       dhur = praytime.dhuhr?:"",
                       sunrise =praytime.sunrise?:"",
                       asr = praytime.asr?:"",
                       maghrib = praytime.maghrib ?:"",
                       isha = praytime.isya?:"",
                       nextFajr = praytimeTommorow.fajr?:"",
                       nextMagrib = praytimeTommorow.maghrib?:"",
                       location = praytime.address?:""
                   )
               )
           }



          }


        }

    }

    private val prayerNameList = arrayListOf("এশা","ফজর", "নিষিদ্ধ সময় ","সালাতুল দুহা","নিষিদ্ধ সময়","যুহর","আসর","নিষিদ্ধ সময়","মাগরিব","এশা","ফজর")






}