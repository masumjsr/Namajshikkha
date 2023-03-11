package com.fsit.sohojnamaj

import android.media.AudioManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.fsit.sohojnamaj.database.dao.SuraDao
import com.fsit.sohojnamaj.database.dao.SuraDetailsDao
import com.fsit.sohojnamaj.model.QuranItemModel
import com.fsit.sohojnamaj.model.Sura
import com.fsit.sohojnamaj.model.SuraDetails
import com.fsit.sohojnamaj.ui.navigation.AppNavHost
import com.fsit.sohojnamaj.ui.navigation.AppState
import com.fsit.sohojnamaj.ui.navigation.rememberAppState
import com.fsit.sohojnamaj.ui.theme.NamajShikkhaTheme
import com.fsit.sohojnamaj.util.loadJSONFromAssets
import com.fsit.sohojnamaj.util.praytimes.Praytime
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.json.JSONArray
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var suraDetailsDao: SuraDetailsDao
    @Inject
    lateinit var suraDao: SuraDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        volumeControlStream = AudioManager.STREAM_ALARM
        Praytime.configureForegroundService(this)
        //  PrayTime.schedule(this)

      for(i in 1..114){

          var facilityModelList = ArrayList<SuraDetails>()
          val facilityJsonArray = JSONArray(applicationContext.loadJSONFromAssets("ar_$i.json")) // Extension Function call here
          for (n in 0 until facilityJsonArray.length()){

              val facilityJSONObject = facilityJsonArray.getJSONObject(n)

              facilityModelList.add(
                  SuraDetails(
                      0,
                      sura=i,
                      ayatNumber = facilityJSONObject.getInt("ayatNumber"),
                      arabic = facilityJSONObject.getString("arabic_indopak_asia"),
                      facilityJSONObject.getString("ayat"),
                      facilityJSONObject.getString("text"),
                  )
              )
          }

          Log.i("123321", "onCreate: $facilityModelList")
          CoroutineScope(Dispatchers.IO).launch{
              suraDetailsDao.updateSurah(facilityModelList)



          }

      }
        CoroutineScope(Dispatchers.IO).launch {
            suraDetailsDao.getSuraDetails().collectLatest {
                Log.i("123321", "onCreate: output$it")
            }
        }

        insertSura()


        val locale = Locale("bn")
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            createConfigurationContext(config)
        resources.updateConfiguration(config, resources.displayMetrics)
        setContent {
            NamajShikkhaTheme (darkTheme = false,dynamicColor = false){
                Locale.setDefault(Locale("bn"))
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainContent()
                }
            }
        }
    }

    private fun insertSura() {
        val mList = ArrayList<QuranItemModel>()
        mList.add(
            QuranItemModel(
                "১",
                "আল ফাতিহা",
                "সূচনা",
                "রুকু-১, আয়াত-৭",
                "মক্কায় অবতীর্ণ",
                "ar_1.json",
                "bn_sn_1.txt",
                "bn_fz_1.txt",
                "https://newsbd48.com/quranen/001-al-fatihah.mp3",
                "https://newsbd48.com/quranbn/001-al-fatihah.mp3",
                "001-al-fatihah.mp3",
                "228",
                "312"
            )
        )
        mList.add(
            QuranItemModel(
                "২",
                "আল বাকারা",
                "বকনা-বাছুর",
                "রুকু-৪০, আয়াত-২৮৬",
                "মদীনায় অবতীর্ণ",
                "ar_2.json",
                "bn_sn_2.txt",
                "bn_fz_2.txt",
                "https://newsbd48.com/quranen/002-al-baqarah.mp3",
                "https://newsbd48.com/quranbn/002-al-baqarah.mp3",
                "002-al-baqarah.mp3",
                "29696",
                "47104"
            )
        )
        mList.add(
            QuranItemModel(
                "৩",
                "আল ইমরান",
                "ইমরানের পরিবার",
                "রুকু-২০, আয়াত-২০০",
                "মদীনায় অবতীর্ণ",
                "ar_3.json",
                "bn_sn_3.txt",
                "bn_fz_3.txt",
                "https://newsbd48.com/quranen/003-al-imran.mp3",
                "https://newsbd48.com/quranbn/003-al-imran.mp3",
                "003-al-imran.mp3",
                "18432",
                "29696"
            )
        )
        mList.add(
            QuranItemModel(
                "৪",
                "আন নিসা",
                "মহিলা",
                "রুকু-২৪, আয়াত-১৭৬",
                "মদীনায় অবতীর্ণ",
                "ar_4.json",
                "bn_sn_4.txt",
                "bn_fz_4.txt",
                "https://newsbd48.com/quranen/004-an-nisa.mp3",
                "https://newsbd48.com/quranbn/004-an-nisa.mp3",
                "004-an-nisa.mp3",
                "18432",
                "29696"
            )
        )
        mList.add(
            QuranItemModel(
                "৫",
                "আল মায়িদাহ",
                "খাদ্য পরিবেশিত টেবিল",
                "রুকু-১৬, আয়াত-১২০",
                "মদীনায় অবতীর্ণ",
                "ar_5.json",
                "bn_sn_5.txt",
                "bn_fz_5.txt",
                "https://newsbd48.com/quranen/005-al-maidah.mp3",
                "https://newsbd48.com/quranbn/005-al-maidah.mp3",
                "005-al-maidah.mp3",
                "14336",
                "23552"
            )
        )
        mList.add(
            QuranItemModel(
                "৬",
                "আল আনআম",
                "গৃৃহপালিত পশু",
                "রুকু-২০, আয়াত-১৬৫",
                "মক্কায় অবতীর্ণ",
                "ar_6.json",
                "bn_sn_6.txt",
                "bn_fz_6.txt",
                "https://newsbd48.com/quranen/006-al-anam.mp3",
                "https://newsbd48.com/quranbn/006-al-anam.mp3",
                "006-al-anam.mp3",
                "17408",
                "26624"
            )
        )
        mList.add(
            QuranItemModel(
                "৭",
                "আল আরাফ",
                "উচু স্থানসমূহ",
                "রুকু-২৪, আয়াত-২০৬",
                "মক্কায় অবতীর্ণ",
                "ar_7.json",
                "bn_sn_7.txt",
                "bn_fz_7.txt",
                "https://newsbd48.com/quranen/007-al-araf.mp3",
                "https://newsbd48.com/quranbn/007-al-araf.mp3",
                "007-al-araf.mp3",
                "19456",
                "11264"
            )
        )
        mList.add(
            QuranItemModel(
                "৮",
                "আল আনফাল",
                "যুদ্ধে-লব্ধ ধনসম্পদ",
                "রুকু-১০, আয়াত-৭৫",
                "মদীনায় অবতীর্ণ",
                "ar_8.json",
                "bn_sn_8.txt",
                "bn_fz_8.txt",
                "https://newsbd48.com/quranen/008-al-anfal.mp3",
                "https://newsbd48.com/quranbn/008-al-anfal.mp3",
                "008-al-anfal.mp3",
                "7168",
                "21504"
            )
        )
        mList.add(
            QuranItemModel(
                "৯",
                "আত-তাওবাহ্‌",
                "অনুশোচনা",
                "রুকু-১৬, আয়াত-১২৯",
                "মদীনায় অবতীর্ণ",
                "ar_9.json",
                "bn_sn_9.txt",
                "bn_fz_9.txt",
                "https://newsbd48.com/quranen/009-at-taubah.mp3",
                "https://newsbd48.com/quranbn/009-at-taubah.mp3",
                "009-at-taubah.mp3",
                "14336",
                "15360"
            )
        )
        mList.add(
            QuranItemModel(
                "১০",
                "ইউনুস",
                "নবী ইউনুস",
                "রুকু-১১, আয়াত-১০৯",
                "মক্কায় অবতীর্ণ",
                "ar_10.json",
                "bn_sn_10.txt",
                "bn_fz_10.txt",
                "https://newsbd48.com/quranen/010-yunus.mp3",
                "https://newsbd48.com/quranbn/010-yunus.mp3",
                "010-yunus.mp3",
                "10240",
                "17408"
            )
        )
        mList.add(
            QuranItemModel(
                "১১",
                "হুদ",
                "নবী হুদ",
                "রুকু-১০, আয়াত-১২৩",
                "মক্কায় অবতীর্ণ",
                "ar_11.json",
                "bn_sn_11.txt",
                "bn_fz_11.txt",
                "https://newsbd48.com/quranen/011-hud.mp3",
                "https://newsbd48.com/quranbn/011-hud.mp3",
                "011-hud.mp3",
                "11264",
                "15360"
            )
        )
        mList.add(
            QuranItemModel(
                "১২",
                "ইউসুফ",
                "নবী ইউসুফ",
                "রুকু-১২, আয়াত-১১১",
                "মক্কায় অবতীর্ণ",
                "ar_12.json",
                "bn_sn_12.txt",
                "bn_fz_12.txt",
                "https://newsbd48.com/quranen/012-yusuf.mp3",
                "https://newsbd48.com/quranbn/012-yusuf.mp3",
                "012-yusuf.mp3",
                "9830",
                "7372"
            )
        )
        mList.add(
            QuranItemModel(
                "১৩",
                "আর-রাদ",
                "বজ্রনাদ",
                "রুকু-৬, আয়াত-৪৩",
                "মদীনায় অবতীর্ণ",
                "ar_13.json",
                "bn_sn_13.txt",
                "bn_fz_13.txt",
                "https://newsbd48.com/quranen/013-ar-rad.mp3",
                "https://newsbd48.com/quranbn/013-ar-rad.mp3",
                "013-ar-rad.mp3",
                "4812",
                "7372"
            )
        )
        mList.add(
            QuranItemModel(
                "১৪",
                "ইব্রাহীম",
                "নবী ইব্রাহিম",
                "রুকু-৭, আয়াত-৫২",
                "মক্কায় অবতীর্ণ",
                "ar_14.json",
                "bn_sn_14.txt",
                "bn_fz_14.txt",
                "https://newsbd48.com/quranen/014-ibrahim.mp3",
                "https://newsbd48.com/quranbn/014-ibrahim.mp3",
                "014-ibrahim.mp3",
                "4812",
                "5939"
            )
        )
        mList.add(
            QuranItemModel(
                "১৫",
                "আল হিজর",
                "পাথুরে পাহাড়",
                "রুকু-৬, আয়াত-৯৯",
                "মক্কায় অবতীর্ণ",
                "ar_15.json",
                "bn_sn_15.txt",
                "bn_fz_15.txt",
                "https://newsbd48.com/quranen/015-al-hijr.mp3",
                "https://newsbd48.com/quranbn/015-al-hijr.mp3",
                "015-al-hijr.mp3",
                "3686",
                "5939"
            )
        )
        mList.add(
            QuranItemModel(
                "১৬",
                "আন নাহল",
                "মৌমাছি",
                "রুকু-১৬, আয়াত-১২৮",
                "মক্কায় অবতীর্ণ",
                "ar_16.json",
                "bn_sn_16.txt",
                "bn_fz_16.txt",
                "https://newsbd48.com/quranen/016-an-nahl.mp3",
                "https://newsbd48.com/quranbn/016-an-nahl.mp3",
                "016-an-nahl.mp3",
                "9932",
                "15360"
            )
        )
        mList.add(
            QuranItemModel(
                "১৭",
                "বনী-ইসরাঈল",
                "ইসরায়েলের সন্তানগণ",
                "রুকু-১২, আয়াত-১১১",
                "মক্কায় অবতীর্ণ",
                "ar_17.json",
                "bn_sn_17.txt",
                "bn_fz_17.txt",
                "https://newsbd48.com/quranen/017-al-isra.mp3",
                "https://newsbd48.com/quranbn/017-al-isra.mp3",
                "017-al-isra.mp3",
                "7782",
                "12288"
            )
        )
        mList.add(
            QuranItemModel(
                "১৮",
                "আল কাহফ",
                "গুহা",
                "রুকু-১২, আয়াত-১১০",
                "মক্কায় অবতীর্ণ",
                "ar_18.json",
                "bn_sn_18.txt",
                "bn_fz_18.txt",
                "https://newsbd48.com/quranen/018-al-kahf.mp3",
                "https://newsbd48.com/quranbn/018-al-kahf.mp3",
                "018-al-kahf.mp3",
                "7884",
                "13312"
            )
        )
        mList.add(
            QuranItemModel(
                "১৯",
                "মারইয়াম",
                "মারিয়াম (নবী ঈসার মা)",
                "রুকু-৬, আয়াত-৯৮",
                "মক্কায় অবতীর্ণ",
                "ar_19.json",
                "bn_sn_19.txt",
                "bn_fz_19.txt",
                "https://newsbd48.com/quranen/019-maryam.mp3",
                "https://newsbd48.com/quranbn/019-maryam.mp3",
                "019-maryam.mp3",
                "5017",
                "8192"
            )
        )
        mList.add(
            QuranItemModel(
                "২০",
                "ত্বোয়া-হা",
                "ত্বোয়া-হা",
                "রুকু-৮, আয়াত-১৩৫",
                "মক্কায় অবতীর্ণ",
                "ar_20.json",
                "bn_sn_20.txt",
                "bn_fz_20.txt",
                "https://newsbd48.com/quranen/020-ta-ha.mp3",
                "https://newsbd48.com/quranbn/020-ta-ha.mp3",
                "020-ta-ha.mp3",
                "6348",
                "11264"
            )
        )
        mList.add(
            QuranItemModel(
                "২১",
                "আল আম্বিয়া",
                "নবীগণ",
                "রুকু-৭, আয়াত-১১২",
                "মক্কায় অবতীর্ণ",
                "ar_21.json",
                "bn_sn_21.txt",
                "bn_fz_21.txt",
                "https://newsbd48.com/quranen/021-al-anbiya.mp3",
                "https://newsbd48.com/quranbn/021-al-anbiya.mp3",
                "021-al-anbiya.mp3",
                "6144",
                "10035"
            )
        )
        mList.add(
            QuranItemModel(
                "২২",
                "আল হাজ্জ্ব",
                "হাজ্জ",
                "রুকু-১০, আয়াত-৭৮",
                "মক্কায় অবতীর্ণ",
                "ar_22.json",
                "bn_sn_22.txt",
                "bn_fz_22.txt",
                "https://newsbd48.com/quranen/022-al-hajj.mp3",
                "https://newsbd48.com/quranbn/022-al-hajj.mp3",
                "022-al-hajj.mp3",
                "6963",
                "10240"
            )
        )
        mList.add(
            QuranItemModel(
                "২৩",
                "আল মু'মিনূন",
                "বিশ্বাসীগণ",
                "রুকু-৬, আয়াত-১১৮",
                "মক্কায় অবতীর্ণ",
                "ar_23.json",
                "bn_sn_23.txt",
                "bn_fz_23.txt",
                "https://newsbd48.com/quranen/023-al-muminun.mp3",
                "https://newsbd48.com/quranbn/023-al-muminun.mp3",
                "023-al-muminun.mp3",
                "5836",
                "9011"
            )
        )
        mList.add(
            QuranItemModel(
                "২৪",
                "আন নূর",
                "আলো,জ্যোতি",
                "রুকু-৯, আয়াত-৬৪",
                "মদীনায় অবতীর্ণ",
                "ar_24.json",
                "bn_sn_24.txt",
                "bn_fz_24.txt",
                "https://newsbd48.com/quranen/024-an-nur.mp3",
                "https://newsbd48.com/quranbn/024-an-nur.mp3",
                "024-an-nur.mp3",
                "7168",
                "11264"
            )
        )
        mList.add(
            QuranItemModel(
                "২৫",
                "আল ফুরকান",
                "সত্য মিথ্যার পার্থক্য নির্ধারণকারী গ্রন্থ",
                "রুকু-৬, আয়াত-৭৭",
                "মক্কায় অবতীর্ণ",
                "ar_25.json",
                "bn_sn_25.txt",
                "bn_fz_25.txt",
                "https://newsbd48.com/quranen/025-al-furqan.mp3",
                "https://newsbd48.com/quranbn/025-al-furqan.mp3",
                "025-al-furqan.mp3",
                "4300",
                "7270"
            )
        )
        mList.add(
            QuranItemModel(
                "২৬",
                "আশ শুআরা",
                "কবিগণ",
                "রুকু-১১, আয়াত-২২৭",
                "মক্কায় অবতীর্ণ",
                "ar_26.json",
                "bn_sn_26.txt",
                "bn_fz_26.txt",
                "https://newsbd48.com/quranen/026-ash-shuara.mp3",
                "https://newsbd48.com/quranbn/026-ash-shuara.mp3",
                "026-ash-shuara.mp3",
                "7168",
                "12288"
            )
        )
        mList.add(
            QuranItemModel(
                "২৭",
                "আন নম্‌ল",
                "পিপীলিকা",
                "রুকু-৭, আয়াত-৯৩",
                "মক্কায় অবতীর্ণ",
                "ar_27.json",
                "bn_sn_27.txt",
                "bn_fz_27.txt",
                "https://newsbd48.com/quranen/027-an-naml.mp3",
                "https://newsbd48.com/quranbn/027-an-naml.mp3",
                "027-an-naml.mp3",
                "6144",
                "10240"
            )
        )
        mList.add(
            QuranItemModel(
                "২৮",
                "আল কাসাস",
                "ঘটনা,কাহিনী",
                "রুকু-৯, আয়াত-৮৮",
                "মক্কায় অবতীর্ণ",
                "ar_28.json",
                "bn_sn_28.txt",
                "bn_fz_28.txt",
                "https://newsbd48.com/quranen/028-al-qasas.mp3",
                "https://newsbd48.com/quranbn/028-al-qasas.mp3",
                "028-al-qasas.mp3",
                "7475",
                "12288"
            )
        )
        mList.add(
            QuranItemModel(
                "২৯",
                "আল আনকাবূত",
                "মাকড়সা",
                "রুকু-৭, আয়াত-৬৯",
                "মক্কায় অবতীর্ণ",
                "ar_29.json",
                "bn_sn_29.txt",
                "bn_fz_29.txt",
                "https://newsbd48.com/quranen/029-al-ankabut.mp3",
                "https://newsbd48.com/quranbn/029-al-ankabut.mp3",
                "029-al-ankabut.mp3",
                "5017",
                "8294"
            )
        )
        mList.add(
            QuranItemModel(
                "৩০",
                "আর রুম",
                "রোমান জাতি",
                "রুকু-৬, আয়াত-৬০",
                "মক্কায় অবতীর্ণ",
                "ar_30.json",
                "bn_sn_30.txt",
                "bn_fz_30.txt",
                "https://newsbd48.com/quranen/030-ar-rum.mp3",
                "https://newsbd48.com/quranbn/030-ar-rum.mp3",
                "030-ar-rum.mp3",
                "4608",
                "7065"
            )
        )
        mList.add(
            QuranItemModel(
                "৩১",
                "লোকমান",
                "একজন জ্ঞানী ব্যক্তি",
                "রুকু-৪, আয়াত-৩৪",
                "মক্কায় অবতীর্ণ",
                "ar_31.json",
                "bn_sn_31.txt",
                "bn_fz_31.txt",
                "https://newsbd48.com/quranen/031-luqman.mp3",
                "https://newsbd48.com/quranbn/031-luqman.mp3",
                "031-luqman.mp3",
                "2764",
                "4198"
            )
        )
        mList.add(
            QuranItemModel(
                "৩২",
                "সেজদাহ",
                "সিজদাহ",
                "রুকু-৩, আয়াত-৩০",
                "মক্কায় অবতীর্ণ",
                "ar_32.json",
                "bn_sn_32.txt",
                "bn_fz_32.txt",
                "https://newsbd48.com/quranen/032-as-sajdah.mp3",
                "https://newsbd48.com/quranbn/032-as-sajdah.mp3",
                "032-as-sajdah.mp3",
                "2048",
                "3072"
            )
        )
        mList.add(
            QuranItemModel(
                "৩৩",
                "আল আহযাব",
                "জোট",
                "রুকু-৯, আয়াত-৭৩",
                "মদীনায় অবতীর্ণ",
                "ar_33.json",
                "bn_sn_33.txt",
                "bn_fz_33.txt",
                "https://newsbd48.com/quranen/033-al-ahzab.mp3",
                "https://newsbd48.com/quranbn/033-al-ahzab.mp3",
                "033-al-ahzab.mp3",
                "6860",
                "10240"
            )
        )
        mList.add(
            QuranItemModel(
                "৩৪",
                "সাবা",
                "রানী সাবা",
                "রুকু-৬ আয়াত-৫৪",
                "মক্কায় অবতীর্ণ",
                "ar_34.json",
                "bn_sn_34.txt",
                "bn_fz_34.txt",
                "https://newsbd48.com/quranen/034-saba.mp3",
                "https://newsbd48.com/quranbn/034-saba.mp3",
                "034-saba.mp3",
                "4505",
                "6860"
            )
        )
        mList.add(
            QuranItemModel(
                "৩৫",
                "ফাতির",
                "আদি স্রষ্টা",
                "রুকু-৫, আয়াত-৪৫",
                "মক্কায় অবতীর্ণ",
                "ar_35.json",
                "bn_sn_35.txt",
                "bn_fz_35.txt",
                "https://newsbd48.com/quranen/035-fatir.mp3",
                "https://newsbd48.com/quranbn/035-fatir.mp3",
                "035-fatir.mp3",
                "4096",
                "6144"
            )
        )
        mList.add(
            QuranItemModel(
                "৩৬",
                "ইয়াসীন",
                "ইয়াসীন",
                "রুকু-৫, আয়াত-৮৩",
                "মক্কায় অবতীর্ণ",
                "ar_36.json",
                "bn_sn_36.txt",
                "bn_fz_36.txt",
                "https://newsbd48.com/quranen/036-ya-sin.mp3",
                "https://newsbd48.com/quranbn/036-ya-sin.mp3",
                "036-ya-sin.mp3",
                "4096",
                "6656"
            )
        )
        mList.add(
            QuranItemModel(
                "৩৭",
                "আস-সাফফাত",
                "সারিবদ্ধভাবে দাড়ানো",
                "রুকু-৫, আয়াত-১৮২",
                "মক্কায় অবতীর্ণ",
                "ar_37.json",
                "bn_sn_37.txt",
                "bn_fz_37.txt",
                "https://newsbd48.com/quranen/037-as-saffat.mp3",
                "https://newsbd48.com/quranbn/037-as-saffat.mp3",
                "037-as-saffat.mp3",
                "5734",
                "8704"
            )
        )
        mList.add(
            QuranItemModel(
                "৩৮",
                "ছোয়াদ",
                "আরবি বর্ণ",
                "রুকু-৫, আয়াত-৮৮",
                "মক্কায় অবতীর্ণ",
                "ar_38.json",
                "bn_sn_38.txt",
                "bn_fz_38.txt",
                "https://newsbd48.com/quranen/038-sad.mp3",
                "https://newsbd48.com/quranbn/038-sad.mp3",
                "038-sad.mp3",
                "4198",
                "6656"
            )
        )
        mList.add(
            QuranItemModel(
                "৩৯",
                "আয-যুমার",
                "দল-বদ্ধ জনতা",
                "রুকু-৮, আয়াত-৭৫",
                "মক্কায় অবতীর্ণ",
                "ar_39.json",
                "bn_sn_39.txt",
                "bn_fz_39.txt",
                "https://newsbd48.com/quranen/039-az-zumar.mp3",
                "https://newsbd48.com/quranbn/039-az-zumar.mp3",
                "039-az-zumar.mp3",
                "6348",
                "10137"
            )
        )
        mList.add(
            QuranItemModel(
                "৪০",
                "আল-মু'মিন",
                "বিশ্বাসী",
                "রুকু-৯, আয়াত-৮৫",
                "মক্কায় অবতীর্ণ",
                "ar_40.json",
                "bn_sn_40.txt",
                "bn_fz_40.txt",
                "https://newsbd48.com/quranen/040-ghafir.mp3",
                "https://newsbd48.com/quranbn/040-ghafir.mp3",
                "040-ghafir.mp3",
                "6144",
                "10137"
            )
        )
        mList.add(
            QuranItemModel(
                "৪১",
                "হা-মীম সেজদাহ",
                "সুস্পষ্ট বিবরণ",
                "রুকু-৬, আয়াত-৫৪",
                "মক্কায় অবতীর্ণ",
                "ar_41.json",
                "bn_sn_41.txt",
                "bn_fz_41.txt",
                "https://newsbd48.com/quranen/041-fussilat.mp3",
                "https://newsbd48.com/quranbn/041-fussilat.mp3",
                "041-fussilat.mp3",
                "4505",
                "7270"
            )
        )
        mList.add(
            QuranItemModel(
                "৪২",
                "আশ-শুরা",
                "পরামর্শ",
                "রুকু-৫, আয়াত-৫৩",
                "মক্কায় অবতীর্ণ",
                "ar_42.json",
                "bn_sn_42.txt",
                "bn_fz_42.txt",
                "https://newsbd48.com/quranen/042-ash-shura.mp3",
                "https://newsbd48.com/quranbn/042-ash-shura.mp3",
                "042-ash-shura.mp3",
                "4608",
                "7270"
            )
        )
        mList.add(
            QuranItemModel(
                "৪৩",
                "আয-যুখরুফ",
                "সোনাদানা",
                "রুকু-৭, আয়াত-৮৯",
                "মক্কায় অবতীর্ণ",
                "ar_43.json",
                "bn_sn_43.txt",
                "bn_fz_43.txt",
                "https://newsbd48.com/quranen/043-az-zukhruf.mp3",
                "https://newsbd48.com/quranbn/043-az-zukhruf.mp3",
                "043-az-zukhruf.mp3",
                "4812",
                "7782"
            )
        )
        mList.add(
            QuranItemModel(
                "৪৪",
                "আদ-দোখান",
                "ধোঁয়া",
                "রুকু-৩, আয়াত-৫৯",
                "মক্কায় অবতীর্ণ",
                "ar_44.json",
                "bn_sn_44.txt",
                "bn_fz_44.txt",
                "https://newsbd48.com/quranen/044-ad-dukhan.mp3",
                "https://newsbd48.com/quranbn/044-ad-dukhan.mp3",
                "044-ad-dukhan.mp3",
                "2355",
                "3481"
            )
        )
        mList.add(
            QuranItemModel(
                "৪৫",
                "আল জাসিয়া",
                "নতজানু",
                "রুকু-৪, আয়াত-৩৭",
                "মক্কায় অবতীর্ণ",
                "ar_45.json",
                "bn_sn_45.txt",
                "bn_fz_45.txt",
                "https://newsbd48.com/quranen/045-al-jathiyah.mp3",
                "https://newsbd48.com/quranbn/045-al-jathiyah.mp3",
                "045-al-jathiyah.mp3",
                "2560",
                "4096"
            )
        )
        mList.add(
            QuranItemModel(
                "৪৬",
                "আল আহক্বাফ",
                "বালুর পাহাড়",
                "রুকু-৪, আয়াত-৩৫",
                "মক্কায় অবতীর্ণ",
                "ar_46.json",
                "bn_sn_46.txt",
                "bn_fz_46.txt",
                "https://newsbd48.com/quranen/046-al-ahqaf.mp3",
                "https://newsbd48.com/quranbn/046-al-ahqaf.mp3",
                "046-al-ahqaf.mp3",
                "3788",
                "5939"
            )
        )
        mList.add(
            QuranItemModel(
                "৪৭",
                "মুহাম্মদ",
                "নবী মুহাম্মদ",
                "রুকু-৪, আয়াত-৩৮",
                "মদীনায় অবতীর্ণ",
                "ar_47.json",
                "bn_sn_47.txt",
                "bn_fz_47.txt",
                "https://newsbd48.com/quranen/047-muhammad.mp3",
                "https://newsbd48.com/quranbn/047-muhammad.mp3",
                "047-muhammad.mp3",
                "2867",
                "4608"
            )
        )
        mList.add(
            QuranItemModel(
                "৪৮",
                "আল ফাতহ",
                "বিজয় (মক্কা বিজয়)",
                "রুকু-৪, আয়াত-২৯",
                "মদীনায় অবতীর্ণ",
                "ar_48.json",
                "bn_sn_48.txt",
                "bn_fz_48.txt",
                "https://newsbd48.com/quranen/048-al-fath.mp3",
                "https://newsbd48.com/quranbn/048-al-fath.mp3",
                "048-al-fath.mp3",
                "2867",
                "4505"
            )
        )
        mList.add(
            QuranItemModel(
                "৪৯",
                "আল হুজরাত",
                "বাসগৃহসমূূহ",
                "রুকু-২, আয়াত-১৮",
                "মদীনায় অবতীর্ণ",
                "ar_49.json",
                "bn_sn_49.txt",
                "bn_fz_49.txt",
                "https://newsbd48.com/quranen/049-al-hujurat.mp3",
                "https://newsbd48.com/quranbn/049-al-hujurat.mp3",
                "049-al-hujurat.mp3",
                "2048",
                "2969"
            )
        )
        mList.add(
            QuranItemModel(
                "৫০",
                "ক্বাফ",
                "আরবি বর্ণ ক্বাফ",
                "রুকু-৩, আয়াত-৪৫",
                "মক্কায় অবতীর্ণ",
                "ar_50.json",
                "bn_sn_50.txt",
                "bn_fz_50.txt",
                "https://newsbd48.com/quranen/050-qaf.mp3",
                "https://newsbd48.com/quranbn/050-qaf.mp3",
                "050-qaf.mp3",
                "2150",
                "3276"
            )
        )
        mList.add(
            QuranItemModel(
                "৫১",
                "আয-যারিয়াত",
                "বিক্ষেপকারী বাতাস",
                "রুকু-৩, আয়াত-৬০",
                "মক্কায় অবতীর্ণ",
                "ar_51.json",
                "bn_sn_51.txt",
                "bn_fz_51.txt",
                "https://newsbd48.com/quranen/051-adh-dhariyat.mp3",
                "https://newsbd48.com/quranbn/051-adh-dhariyat.mp3",
                "051-adh-dhariyat.mp3",
                "2150",
                "3379"
            )
        )
        mList.add(
            QuranItemModel(
                "৫২",
                "আত্ব তূর",
                "পাহাড়",
                "রুকু-২, আয়াত-৪৯",
                "মদীনায় অবতীর্ণ",
                "ar_52.json",
                "bn_sn_52.txt",
                "bn_fz_52.txt",
                "https://newsbd48.com/quranen/052-at-tur.mp3",
                "https://newsbd48.com/quranbn/052-at-tur.mp3",
                "052-at-tur.mp3",
                "1945",
                "2969"
            )
        )
        mList.add(
            QuranItemModel(
                "৫৩",
                "আন-নাজম",
                "তারা",
                "রুকু-৩, আয়াত-৬২",
                "মক্কায় অবতীর্ণ",
                "ar_53.json",
                "bn_sn_53.txt",
                "bn_fz_53.txt",
                "https://newsbd48.com/quranen/053-an-najm.mp3",
                "https://newsbd48.com/quranbn/053-an-najm.mp3",
                "053-an-najm.mp3",
                "1843",
                "2969"
            )
        )
        mList.add(
            QuranItemModel(
                "৫৪",
                "আল ক্বামার",
                "চন্দ্র",
                "রুকু-৩, আয়াত-৫৫",
                "মক্কায় অবতীর্ণ",
                "ar_54.json",
                "bn_sn_54.txt",
                "bn_fz_54.txt",
                "https://newsbd48.com/quranen/054-al-qamar.mp3",
                "https://newsbd48.com/quranbn/054-al-qamar.mp3",
                "054-al-qamar.mp3",
                "1843",
                "3174"
            )
        )
        mList.add(
            QuranItemModel(
                "৫৫",
                "আর রাহমান",
                "অনন্ত করুণাময়",
                "রুকু-৩, আয়াত-৭৮",
                "মদীনায় অবতীর্ণ",
                "ar_55.json",
                "bn_sn_55.txt",
                "bn_fz_55.txt",
                "https://newsbd48.com/quranen/055-ar-rahman.mp3",
                "https://newsbd48.com/quranbn/055-ar-rahman.mp3",
                "055-ar-rahman.mp3",
                "2662",
                "4096"
            )
        )
        mList.add(
            QuranItemModel(
                "৫৬",
                "আল ওয়াকিয়াহ",
                "নিশ্চিত ঘটনা",
                "রুকু-৩, আয়াত-৯৬",
                "মক্কায় অবতীর্ণ",
                "ar_56.json",
                "bn_sn_56.txt",
                "bn_fz_56.txt",
                "https://newsbd48.com/quranen/056-al-waqiah.mp3",
                "https://newsbd48.com/quranbn/056-al-waqiah.mp3",
                "056-al-waqiah.mp3",
                "2764",
                "4198"
            )
        )
        mList.add(
            QuranItemModel(
                "৫৭",
                "আল হাদীদ",
                "লোহা",
                "রুকু-৪, আয়াত-২৯",
                "মদীনায় অবতীর্ণ",
                "ar_57.json",
                "bn_sn_57.txt",
                "bn_fz_57.txt",
                "https://newsbd48.com/quranen/057-al-hadid.mp3",
                "https://newsbd48.com/quranbn/057-al-hadid.mp3",
                "057-al-hadid.mp3",
                "3174",
                "5017"
            )
        )
        mList.add(
            QuranItemModel(
                "৫৮",
                "আল মুজাদালাহ",
                "অনুযোগকারিণী",
                "রুকু-৩, আয়াত-২২",
                "মদীনায় অবতীর্ণ",
                "ar_58.json",
                "bn_sn_58.txt",
                "bn_fz_58.txt",
                "https://newsbd48.com/quranen/058-al-mujadilah.mp3",
                "https://newsbd48.com/quranbn/058-al-mujadilah.mp3",
                "058-al-mujadilah.mp3",
                "2355",
                "3891"
            )
        )
        mList.add(
            QuranItemModel(
                "৫৯",
                "আল হাশর",
                "সমাবেশ",
                "রুকু-৩, আয়াত-২৪",
                "মদীনায় অবতীর্ণ",
                "ar_59.json",
                "bn_sn_59.txt",
                "bn_fz_59.txt",
                "https://newsbd48.com/quranen/059-al-hashr.mp3",
                "https://newsbd48.com/quranbn/059-al-hashr.mp3",
                "059-al-hashr.mp3",
                "2457",
                "3993"
            )
        )
        mList.add(
            QuranItemModel(
                "৬০",
                "আল মুমতাহিনাহ",
                "নারী, যাকে পরীক্ষা করা হবে",
                "রুকু-২, আয়াত-১৩",
                "মদীনায় অবতীর্ণ",
                "ar_60.json",
                "bn_sn_60.txt",
                "bn_fz_60.txt",
                "https://newsbd48.com/quranen/060-al-mumtahanah.mp3",
                "https://newsbd48.com/quranbn/060-al-mumtahanah.mp3",
                "060-al-mumtahanah.mp3",
                "1740",
                "2867"
            )
        )
        mList.add(
            QuranItemModel(
                "৬১",
                "আছ-ছাফ",
                "সারবন্দী সৈন্যদল",
                "রুকু-২, আয়াত-১৪",
                "মদীনায় অবতীর্ণ",
                "ar_61.json",
                "bn_sn_61.txt",
                "bn_fz_61.txt",
                "https://newsbd48.com/quranen/061-as-saff.mp3",
                "https://newsbd48.com/quranbn/061-as-saff.mp3",
                "061-as-saff.mp3",
                "1126",
                "1843"
            )
        )
        mList.add(
            QuranItemModel(
                "৬২",
                "আল জুমুআ",
                "সম্মেলন/শুক্রবার",
                "রুকু-২, আয়াত-১১",
                "মদীনায় অবতীর্ণ",
                "ar_62.json",
                "bn_sn_62.txt",
                "bn_fz_62.txt",
                "https://newsbd48.com/quranen/062-al-jumuah.mp3",
                "https://newsbd48.com/quranbn/062-al-jumuah.mp3",
                "062-al-jumuah.mp3",
                "835",
                "1331"
            )
        )
        mList.add(
            QuranItemModel(
                "৬৩",
                "আল মুনাফিকুন",
                "কপট বিশ্বাসীগণ",
                "রুকু-২, আয়াত-১১",
                "মদীনায় অবতীর্ণ",
                "ar_63.json",
                "bn_sn_63.txt",
                "bn_fz_63.txt",
                "https://newsbd48.com/quranen/063-al-munafiqun.mp3",
                "https://newsbd48.com/quranbn/063-al-munafiqun.mp3",
                "063-al-munafiqun.mp3",
                "1024",
                "1536"
            )
        )
        mList.add(
            QuranItemModel(
                "৬৪",
                "আত-তাগাবুন",
                "মোহ অপসারণ",
                "রুকু-২, আয়াত-১৮",
                "মদীনায় অবতীর্ণ",
                "ar_64.json",
                "bn_sn_64.txt",
                "bn_fz_64.txt",
                "https://newsbd48.com/quranen/064-at-taghabun.mp3",
                "https://newsbd48.com/quranbn/064-at-taghabun.mp3",
                "064-at-taghabun.mp3",
                "1228",
                "2150"
            )
        )
        mList.add(
            QuranItemModel(
                "৬৫",
                "আত্ব-ত্বালাক",
                "তালাক,বন্ধনমুক্তি",
                "রুকু-২, আয়াত-১২",
                "মদীনায় অবতীর্ণ",
                "ar_65.json",
                "bn_sn_65.txt",
                "bn_fz_65.txt",
                "https://newsbd48.com/quranen/065-at-talaq.mp3",
                "https://newsbd48.com/quranbn/065-at-talaq.mp3",
                "065-at-talaq.mp3",
                "1443",
                "2252"
            )
        )
        mList.add(
            QuranItemModel(
                "৬৬",
                "আত-তাহরীম",
                "নিষিদ্ধকরণ",
                "রুকু-২, আয়াত-১২",
                "মদীনায় অবতীর্ণ",
                "ar_66.json",
                "bn_sn_66.txt",
                "bn_fz_66.txt",
                "https://newsbd48.com/quranen/066-at-tahrim.mp3",
                "https://newsbd48.com/quranbn/066-at-tahrim.mp3",
                "066-at-tahrim.mp3",
                "1443",
                "2252"
            )
        )
        mList.add(
            QuranItemModel(
                "৬৭",
                "আল মুলক",
                "সার্বভৌম কর্তৃত্ব",
                "রুকু-২, আয়াত-৩০",
                "মক্কায় অবতীর্ণ",
                "ar_67.json",
                "bn_sn_67.txt",
                "bn_fz_67.txt",
                "https://newsbd48.com/quranen/067-al-mulk.mp3",
                "https://newsbd48.com/quranbn/067-al-mulk.mp3",
                "067-al-mulk.mp3",
                "1440",
                "2764"
            )
        )
        mList.add(
            QuranItemModel(
                "৬৮",
                "আল কলম",
                "কলম",
                "রুকু-২, আয়াত-৫২",
                "মক্কায় অবতীর্ণ",
                "ar_68.json",
                "bn_sn_68.txt",
                "bn_fz_68.txt",
                "https://newsbd48.com/quranen/068-al-qalam.mp3",
                "https://newsbd48.com/quranbn/068-al-qalam.mp3",
                "068-al-qalam.mp3",
                "1440",
                "2867"
            )
        )
        mList.add(
            QuranItemModel(
                "৬৯",
                "আল হাক্কাহ",
                "নিশ্চিত সত্য",
                "রুকু-২, আয়াত-৫২",
                "মক্কায় অবতীর্ণ",
                "ar_69.json",
                "bn_sn_69.txt",
                "bn_fz_69.txt",
                "https://newsbd48.com/quranen/069-al-haqqah.mp3",
                "https://newsbd48.com/quranbn/069-al-haqqah.mp3",
                "069-al-haqqah.mp3",
                "1536",
                "2457"
            )
        )
        mList.add(
            QuranItemModel(
                "৭০",
                "আল মা'আরিজ",
                "উন্নয়নের সোপান",
                "রুকু-২, আয়াত-৪৪",
                "মক্কায় অবতীর্ণ",
                "ar_70.json",
                "bn_sn_70.txt",
                "bn_fz_70.txt",
                "https://newsbd48.com/quranen/070-al-maarij.mp3",
                "https://newsbd48.com/quranbn/070-al-maarij.mp3",
                "070-al-maarij.mp3",
                "1228",
                "1945"
            )
        )
        mList.add(
            QuranItemModel(
                "৭১",
                "নূহ",
                "নবী নূহ",
                "রুকু-২, আয়াত-২৮",
                "মক্কায় অবতীর্ণ",
                "ar_71.json",
                "bn_sn_71.txt",
                "bn_fz_71.txt",
                "https://newsbd48.com/quranen/071-nuh.mp3",
                "https://newsbd48.com/quranbn/071-nuh.mp3",
                "071-nuh.mp3",
                "1126",
                "1843"
            )
        )
        mList.add(
            QuranItemModel(
                "৭২",
                "আল জিন",
                "জ্বিন সম্প্রদায়",
                "রুকু-২, আয়াত-২৮",
                "মক্কায় অবতীর্ণ",
                "ar_72.json",
                "bn_sn_72.txt",
                "bn_fz_72.txt",
                "https://newsbd48.com/quranen/072-al-jinn.mp3",
                "https://newsbd48.com/quranbn/072-al-jinn.mp3",
                "072-al-jinn.mp3",
                "1331",
                "2150"
            )
        )
        mList.add(
            QuranItemModel(
                "৭৩",
                "আল মুজাম্মিল",
                "বস্ত্র আচ্ছাদনকারী",
                "রুকু-২, আয়াত-২০",
                "মক্কায় অবতীর্ণ",
                "ar_73.json",
                "bn_sn_73.txt",
                "bn_fz_73.txt",
                "https://newsbd48.com/quranen/073-al-muzammil.mp3",
                "https://newsbd48.com/quranbn/073-al-muzammil.mp3",
                "073-al-muzammil.mp3",
                "954",
                "1638"
            )
        )
        mList.add(
            QuranItemModel(
                "৭৪",
                "আল মুদ্দাসসির",
                "পোশাক পরিহিত",
                "রুকু-২, আয়াত-৫৬",
                "মক্কায় অবতীর্ণ",
                "ar_74.json",
                "bn_sn_74.txt",
                "bn_fz_74.txt",
                "https://newsbd48.com/quranen/074-al-muddaththir.mp3",
                "https://newsbd48.com/quranbn/074-al-muddaththir.mp3",
                "074-al-muddaththir.mp3",
                "1228",
                "2252"
            )
        )
        mList.add(
            QuranItemModel(
                "৭৫",
                "আল ক্বিয়ামাহ",
                "পুনরুথান",
                "রুকু-২, আয়াত-৪০",
                "মক্কায় অবতীর্ণ",
                "ar_75.json",
                "bn_sn_75.txt",
                "bn_fz_75.txt",
                "https://newsbd48.com/quranen/075-al-qiyamah.mp3",
                "https://newsbd48.com/quranbn/075-al-qiyamah.mp3",
                "075-al-qiyamah.mp3",
                "827",
                "1433"
            )
        )
        mList.add(
            QuranItemModel(
                "৭৬",
                "আদ-দাহর",
                "মানুষ",
                "রুকু-২, আয়াত-৩১",
                "মদীনায় অবতীর্ণ",
                "ar_76.json",
                "bn_sn_76.txt",
                "bn_fz_76.txt",
                "https://newsbd48.com/quranen/076-al-insan.mp3",
                "https://newsbd48.com/quranbn/076-al-insan.mp3",
                "076-al-insan.mp3",
                "1228",
                "2150"
            )
        )
        mList.add(
            QuranItemModel(
                "৭৭",
                "আল মুরসালাত",
                "প্রেরিত পুরুষবৃন্দ",
                "রুকু-২, আয়াত-৫০",
                "মক্কায় অবতীর্ণ",
                "ar_77.json",
                "bn_sn_77.txt",
                "bn_fz_77.txt",
                "https://newsbd48.com/quranen/077-al-mursalat.mp3",
                "https://newsbd48.com/quranbn/077-al-mursalat.mp3",
                "077-al-mursalat.mp3",
                "1126",
                "1843"
            )
        )
        mList.add(
            QuranItemModel(
                "৭৮",
                "আন-নাবা",
                "মহাসংবাদ",
                "রুকু-২, আয়াত-৪০",
                "মক্কায় অবতীর্ণ",
                "ar_78.json",
                "bn_sn_78.txt",
                "bn_fz_78.txt",
                "https://newsbd48.com/quranen/078-an-naba.mp3",
                "https://newsbd48.com/quranbn/078-an-naba.mp3",
                "078-an-naba.mp3",
                "1126",
                "1740"
            )
        )
        mList.add(
            QuranItemModel(
                "৭৯",
                "আন-নাযিআ'ত",
                "প্রচেষ্টাকারী",
                "রুকু-২, আয়াত-৪৬",
                "মক্কায় অবতীর্ণ",
                "ar_79.json",
                "bn_sn_79.txt",
                "bn_fz_79.txt",
                "https://newsbd48.com/quranen/079-an-naziat.mp3",
                "https://newsbd48.com/quranbn/079-an-naziat.mp3",
                "079-an-naziat.mp3",
                "1024",
                "1740"
            )
        )
        mList.add(
            QuranItemModel(
                "৮০",
                "আবাসা",
                "তিনি ভ্রুকুটি করলেন",
                "রুকু-১, আয়াত-৪২",
                "মক্কায় অবতীর্ণ",
                "ar_80.json",
                "bn_sn_80.txt",
                "bn_fz_80.txt",
                "https://newsbd48.com/quranen/080-abasa.mp3",
                "https://newsbd48.com/quranbn/080-abasa.mp3",
                "080-abasa.mp3",
                "861",
                "1433"
            )
        )
        mList.add(
            QuranItemModel(
                "৮১",
                "আত-তাকভীর",
                "অন্ধকারাচ্ছন্ন",
                "রুকু-১, আয়াত-২৯",
                "মক্কায় অবতীর্ণ",
                "ar_81.json",
                "bn_sn_81.txt",
                "bn_fz_81.txt",
                "https://newsbd48.com/quranen/081-at-takwir.mp3",
                "https://newsbd48.com/quranbn/081-at-takwir.mp3",
                "081-at-takwir.mp3",
                "619",
                "979"
            )
        )
        mList.add(
            QuranItemModel(
                "৮২",
                "আল ইনফিতার",
                "বিদীর্ণ করা",
                "রুকু-১, আয়াত-১৯",
                "মক্কায় অবতীর্ণ",
                "ar_82.json",
                "bn_sn_82.txt",
                "bn_fz_82.txt",
                "https://newsbd48.com/quranen/082-al-infitar.mp3",
                "https://newsbd48.com/quranbn/082-al-infitar.mp3",
                "082-al-infitar.mp3",
                "535",
                "994"
            )
        )
        mList.add(
            QuranItemModel(
                "৮৩",
                "আত-মুত্বাফফিফীন",
                "প্রতারকগণ",
                "রুকু-১, আয়াত-৩৬",
                "মক্কায় অবতীর্ণ",
                "ar_83.json",
                "bn_sn_83.txt",
                "bn_fz_83.txt",
                "https://newsbd48.com/quranen/083-al-mutaffifin.mp3",
                "https://newsbd48.com/quranbn/083-al-mutaffifin.mp3",
                "083-al-mutaffifin.mp3",
                "1228",
                "1740"
            )
        )
        mList.add(
            QuranItemModel(
                "৮৪",
                "আল ইনশিকাক",
                "খন্ড-বিখন্ড করণ",
                "রুকু-১, আয়াত-২৫",
                "মক্কায় অবতীর্ণ",
                "ar_84.json",
                "bn_sn_84.txt",
                "bn_fz_84.txt",
                "https://newsbd48.com/quranen/084-al-inshiqaq.mp3",
                "https://newsbd48.com/quranbn/084-al-inshiqaq.mp3",
                "084-al-inshiqaq.mp3",
                "626",
                "1024"
            )
        )
        mList.add(
            QuranItemModel(
                "৮৫",
                "আল বুরুজ",
                "নক্ষত্রপুঞ্জ",
                "রুকু-১, আয়াত-২২",
                "মক্কায় অবতীর্ণ",
                "ar_85.json",
                "bn_sn_85.txt",
                "bn_fz_85.txt",
                "https://newsbd48.com/quranen/085-al-buruj.mp3",
                "https://newsbd48.com/quranbn/085-al-buruj.mp3",
                "085-al-buruj.mp3",
                "774",
                "1126"
            )
        )
        mList.add(
            QuranItemModel(
                "৮৬",
                "আত্ব-তারিক্ব",
                "রাতের আগন্তুক",
                "রুকু-১, আয়াত-১৭",
                "মক্কায় অবতীর্ণ",
                "ar_86.json",
                "bn_sn_86.txt",
                "bn_fz_86.txt",
                "https://newsbd48.com/quranen/086-at-tariq.mp3",
                "https://newsbd48.com/quranbn/086-at-tariq.mp3",
                "086-at-tariq.mp3",
                "392",
                "641"
            )
        )
        mList.add(
            QuranItemModel(
                "৮৭",
                "আল আ'লা",
                "সর্বোর্ধ্ব",
                "রুকু-১, আয়াত-১৯",
                "মক্কায় অবতীর্ণ",
                "ar_87.json",
                "bn_sn_87.txt",
                "bn_fz_87.txt",
                "https://newsbd48.com/quranen/087-al-ala.mp3",
                "https://newsbd48.com/quranbn/087-al-ala.mp3",
                "087-al-ala.mp3",
                "421",
                "711"
            )
        )
        mList.add(
            QuranItemModel(
                "৮৮",
                "আল গাশিয়াহ",
                "বিহ্বলকর ঘটনা",
                "রুকু-১, আয়াত-২৬",
                "মক্কায় অবতীর্ণ",
                "ar_88.json",
                "bn_sn_88.txt",
                "bn_fz_88.txt",
                "https://newsbd48.com/quranen/088-al-ghashiyah.mp3",
                "https://newsbd48.com/quranbn/088-al-ghashiyah.mp3",
                "088-al-ghashiyah.mp3",
                "524",
                "814"
            )
        )
        mList.add(
            QuranItemModel(
                "৮৯",
                "আল ফাজর",
                "ভোরবেলা",
                "রুকু-১, আয়াত-৩০",
                "মক্কায় অবতীর্ণ",
                "ar_89.json",
                "bn_sn_89.txt",
                "bn_fz_89.txt",
                "https://newsbd48.com/quranen/089-al-fajr.mp3",
                "https://newsbd48.com/quranbn/089-al-fajr.mp3",
                "089-al-fajr.mp3",
                "832",
                "1228"
            )
        )
        mList.add(
            QuranItemModel(
                "৯০",
                "আল বালাদ",
                "নগর",
                "রুকু-১, আয়াত-২০",
                "মক্কায় অবতীর্ণ",
                "ar_90.json",
                "bn_sn_90.txt",
                "bn_fz_90.txt",
                "https://newsbd48.com/quranen/090-al-balad.mp3",
                "https://newsbd48.com/quranbn/090-al-balad.mp3",
                "090-al-balad.mp3",
                "469",
                "693"
            )
        )
        mList.add(
            QuranItemModel(
                "৯১",
                "আশ-শামস",
                "সূর্য্য",
                "রুকু-১, আয়াত-১৫",
                "মক্কায় অবতীর্ণ",
                "ar_91.json",
                "bn_sn_91.txt",
                "bn_fz_91.txt",
                "https://newsbd48.com/quranen/091-ash-shams.mp3",
                "https://newsbd48.com/quranbn/091-ash-shams.mp3",
                "091-ash-shams.mp3",
                "330",
                "590"
            )
        )
        mList.add(
            QuranItemModel(
                "৯২",
                "আল লাইল",
                "রাত্রি",
                "রুকু-১, আয়াত-২১",
                "মক্কায় অবতীর্ণ",
                "ar_92.json",
                "bn_sn_92.txt",
                "bn_fz_92.txt",
                "https://newsbd48.com/quranen/092-al-lail.mp3",
                "https://newsbd48.com/quranbn/092-al-lail.mp3",
                "092-al-lail.mp3",
                "436",
                "687"
            )
        )
        mList.add(
            QuranItemModel(
                "৯৩",
                "আদ্ব-দ্বোহা",
                "পূর্বাহ্নের সূর্যকিরণ",
                "রুকু-১, আয়াত-১১",
                "মক্কায় অবতীর্ণ",
                "ar_93.json",
                "bn_sn_93.txt",
                "bn_fz_93.txt",
                "https://newsbd48.com/quranen/093-ad-duha.mp3",
                "https://newsbd48.com/quranbn/093-ad-duha.mp3",
                "093-ad-duha.mp3",
                "225",
                "423"
            )
        )
        mList.add(
            QuranItemModel(
                "৯৪",
                "আল ইনশিরাহ",
                "বক্ষ প্রশস্তকরণ",
                "রুকু-১, আয়াত-৮",
                "মক্কায় অবতীর্ণ",
                "ar_94.json",
                "bn_sn_94.txt",
                "bn_fz_94.txt",
                "https://newsbd48.com/quranen/094-ash-sharh.mp3",
                "https://newsbd48.com/quranbn/094-ash-sharh.mp3",
                "094-ash-sharh.mp3",
                "168",
                "276"
            )
        )
        mList.add(
            QuranItemModel(
                "৯৫",
                "ত্বীন",
                "ডুমুর",
                "রুকু-১, আয়াত-৮",
                "মক্কায় অবতীর্ণ",
                "ar_95.json",
                "bn_sn_95.txt",
                "bn_fz_95.txt",
                "https://newsbd48.com/quranen/095-at-tin.mp3",
                "https://newsbd48.com/quranbn/095-at-tin.mp3",
                "095-at-tin.mp3",
                "254",
                "394"
            )
        )
        mList.add(
            QuranItemModel(
                "৯৬",
                "আলাক",
                "রক্তপিন্ড",
                "রুকু-১, আয়াত-১৯",
                "মক্কায় অবতীর্ণ",
                "ar_96.json",
                "bn_sn_96.txt",
                "bn_fz_96.txt",
                "https://newsbd48.com/quranen/096-al-alaq.mp3",
                "https://newsbd48.com/quranbn/096-al-alaq.mp3",
                "096-al-alaq.mp3",
                "372",
                "642"
            )
        )
        mList.add(
            QuranItemModel(
                "৯৭",
                "কদর",
                "পরিমাণ",
                "রুকু-১, আয়াত-৫",
                "মক্কায় অবতীর্ণ",
                "ar_97.json",
                "bn_sn_97.txt",
                "bn_fz_97.txt",
                "https://newsbd48.com/quranen/097-al-qadr.mp3",
                "https://newsbd48.com/quranbn/097-al-qadr.mp3",
                "097-al-qadr.mp3",
                "177",
                "277"
            )
        )
        mList.add(
            QuranItemModel(
                "৯৮",
                "বাইয়্যিনাহ",
                "সুস্পষ্ট প্রমাণ",
                "রুকু-১, আয়াত-৮",
                "মক্কায় অবতীর্ণ",
                "ar_98.json",
                "bn_sn_98.txt",
                "bn_fz_98.txt",
                "https://newsbd48.com/quranen/098-al-baiyyinah.mp3",
                "https://newsbd48.com/quranbn/098-al-baiyyinah.mp3",
                "098-al-baiyyinah.mp3",
                "494",
                "724"
            )
        )
        mList.add(
            QuranItemModel(
                "৯৯",
                "যিলযাল",
                "ভূমিকম্প",
                "রুকু-১, আয়াত-৮",
                "মক্কায় অবতীর্ণ",
                "ar_99.json",
                "bn_sn_99.txt",
                "bn_fz_99.txt",
                "https://newsbd48.com/quranen/099-az-zalzalah.mp3",
                "https://newsbd48.com/quranbn/099-az-zalzalah.mp3",
                "099-az-zalzalah.mp3",
                "235",
                "361"
            )
        )
        mList.add(
            QuranItemModel(
                "১০০",
                "আল আদিয়াত",
                "অভিযানকারী",
                "রুকু-১, আয়াত-১১",
                "মক্কায় অবতীর্ণ",
                "ar_100.json",
                "bn_sn_100.txt",
                "bn_fz_100.txt",
                "https://newsbd48.com/quranen/100-al-adiyat.mp3",
                "https://newsbd48.com/quranbn/100-al-adiyat.mp3",
                "100-al-adiyat.mp3",
                "274",
                "430"
            )
        )
        mList.add(
            QuranItemModel(
                "১০১",
                "ক্বারিয়াহ",
                "মহাসংকট",
                "রুকু-১, আয়াত-১১",
                "মক্কায় অবতীর্ণ",
                "ar_101.json",
                "bn_sn_101.txt",
                "bn_fz_101.txt",
                "https://newsbd48.com/quranen/101-al-qariah.mp3",
                "https://newsbd48.com/quranbn/101-al-qariah.mp3",
                "101-al-qariah.mp3",
                "245",
                "356"
            )
        )
        mList.add(
            QuranItemModel(
                "১০২",
                "তাকাসূর",
                "প্রাচুর্য্যের প্রতিযোগিতা",
                "রুকু-১, আয়াত-৮",
                "মক্কায় অবতীর্ণ",
                "ar_102.json",
                "bn_sn_102.txt",
                "bn_fz_102.txt",
                "https://newsbd48.com/quranen/102-at-takathur.mp3",
                "https://newsbd48.com/quranbn/102-at-takathur.mp3",
                "102-at-takathur.mp3",
                "245",
                "373"
            )
        )
        mList.add(
            QuranItemModel(
                "১০৩",
                "আসর",
                "অপরাহ্ন",
                "রুকু-১, আয়াত-৩",
                "মক্কায় অবতীর্ণ",
                "ar_103.json",
                "bn_sn_103.txt",
                "bn_fz_103.txt",
                "https://newsbd48.com/quranen/103-al-asr.mp3",
                "https://newsbd48.com/quranbn/103-al-asr.mp3",
                "103-al-asr.mp3",
                "107",
                "175"
            )
        )
        mList.add(
            QuranItemModel(
                "১০৪",
                "হুমাযাহ",
                "পরনিন্দাকারী",
                "রুকু-১, আয়াত-৯",
                "মক্কায় অবতীর্ণ",
                "ar_104.json",
                "bn_sn_104.txt",
                "bn_fz_104.txt",
                "https://newsbd48.com/quranen/104-al-humazah.mp3",
                "https://newsbd48.com/quranbn/104-al-humazah.mp3",
                "104-al-humazah.mp3",
                "227",
                "340"
            )
        )
        mList.add(
            QuranItemModel(
                "১০৫",
                "ফীল",
                "হাতি",
                "রুকু-১, আয়াত-৫",
                "মক্কায় অবতীর্ণ",
                "ar_105.json",
                "bn_sn_105.txt",
                "bn_fz_105.txt",
                "https://newsbd48.com/quranen/105-al-fil.mp3",
                "https://newsbd48.com/quranbn/105-al-fil.mp3",
                "105-al-fil.mp3",
                "191",
                "275"
            )
        )
        mList.add(
            QuranItemModel(
                "১০৬",
                "কুরাইশ",
                "কুরাইশ গোত্র",
                "রুকু-১, আয়াত-৪",
                "মক্কায় অবতীর্ণ",
                "ar_106.json",
                "bn_sn_106.txt",
                "bn_fz_106.txt",
                "https://newsbd48.com/quranen/106-quraish.mp3",
                "https://newsbd48.com/quranbn/106-quraish.mp3",
                "106-quraish.mp3",
                "166",
                "235"
            )
        )
        mList.add(
            QuranItemModel(
                "১০৭",
                "মাউন",
                "সাহায্য-সহায়তা",
                "রুকু-১, আয়াত-৭",
                "মক্কায় অবতীর্ণ",
                "ar_107.json",
                "bn_sn_107.txt",
                "bn_fz_107.txt",
                "https://newsbd48.com/quranen/107-al-maun.mp3",
                "https://newsbd48.com/quranbn/107-al-maun.mp3",
                "107-al-maun.mp3",
                "255",
                "326"
            )
        )
        mList.add(
            QuranItemModel(
                "১০৮",
                "কাওসার",
                "প্রাচুর্য",
                "রুকু-১, আয়াত-৩",
                "মক্কায় অবতীর্ণ",
                "ar_108.json",
                "bn_sn_108.txt",
                "bn_fz_108.txt",
                "https://newsbd48.com/quranen/108-al-kauthar.mp3",
                "https://newsbd48.com/quranbn/108-al-kauthar.mp3",
                "108-al-kauthar.mp3",
                "96",
                "151"
            )
        )
        mList.add(
            QuranItemModel(
                "১০৯",
                "কাফিরুন",
                "অস্বীকারকারীগণ",
                "রুকু-১, আয়াত-৬",
                "মক্কায় অবতীর্ণ",
                "ar_109.json",
                "bn_sn_109.txt",
                "bn_fz_109.txt",
                "https://newsbd48.com/quranen/109-al-kafirun.mp3",
                "https://newsbd48.com/quranbn/109-al-kafirun.mp3",
                "109-al-kafirun.mp3",
                "212",
                "315"
            )
        )
        mList.add(
            QuranItemModel(
                "১১০",
                "নাসর",
                "বিজয়,সাহায্য",
                "রুকু-১, আয়াত-৩",
                "মদীনায় অবতীর্ণ",
                "ar_110.json",
                "bn_sn_110.txt",
                "bn_fz_110.txt",
                "https://newsbd48.com/quranen/110-an-nasr.mp3",
                "https://newsbd48.com/quranbn/110-an-nasr.mp3",
                "110-an-nasr.mp3",
                "137",
                "216"
            )
        )
        mList.add(
            QuranItemModel(
                "১১১",
                "লাহাব",
                "জ্বলন্ত অঙ্গার",
                "রুকু-১, আয়াত-৫",
                "মক্কায় অবতীর্ণ",
                "ar_111.json",
                "bn_sn_111.txt",
                "bn_fz_111.txt",
                "https://newsbd48.com/quranen/111-al-masad.mp3",
                "https://newsbd48.com/quranbn/111-al-masad.mp3",
                "111-al-masad.mp3",
                "163",
                "242"
            )
        )
        mList.add(
            QuranItemModel(
                "১১২",
                "আল ইখলাস",
                "একনিষ্ঠতা",
                "রুকু-১, আয়াত-৪",
                "মক্কায় অবতীর্ণ",
                "ar_112.json",
                "bn_sn_112.txt",
                "bn_fz_112.txt",
                "https://newsbd48.com/quranen/112-al-ikhlas.mp3",
                "https://newsbd48.com/quranbn/112-al-ikhlas.mp3",
                "112-al-ikhlas.mp3",
                "85",
                "162"
            )
        )
        mList.add(
            QuranItemModel(
                "১১৩",
                "আল ফালাক",
                "নিশিভোর",
                "রুকু-১, আয়াত-৫",
                "মদীনায় অবতীর্ণ",
                "ar_113.json",
                "bn_sn_113.txt",
                "bn_fz_113.txt",
                "https://newsbd48.com/quranen/113-al-falaq.mp3",
                "https://newsbd48.com/quranbn/113-al-falaq.mp3",
                "113-al-falaq.mp3",
                "130",
                "217"
            )
        )
        mList.add(
            QuranItemModel(
                "১১৪",
                "আন নাস",
                "মানবজাতি",
                "রুকু-১, আয়াত-৬",
                "মদীনায় অবতীর্ণ",
                "ar_114.json",
                "bn_sn_114.txt",
                "bn_fz_114.txt",
                "https://newsbd48.com/quranen/114-an-nas.mp3",
                "https://newsbd48.com/quranbn/114-an-nas.mp3",
                "114-an-nas.mp3",
                "196",
                "272"
            )
        )


        for (i in 1..114){
            val item=mList.get(i-1)
           CoroutineScope(Dispatchers.IO).launch {
               suraDao.updateSurah(
                   Sura(
                       i,item.tv_Sura_Name,item.tv_Sura_Meaning,item.tv_Sura_Ayat_No,item.tv_Sura_Discended,item.audioFileName
                   )
               )
           }
        }

    }
}

@Composable
fun MainContent(
    appState: AppState = rememberAppState(),

    ) {
    AppNavHost(
        navController = appState.navController,
        onBackClick = appState::onBackClick
    )
}


