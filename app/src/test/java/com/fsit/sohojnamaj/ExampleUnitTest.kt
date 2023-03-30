package com.fsit.sohojnamaj

import com.fsit.sohojnamaj.database.dao.FakePrayerDao
import com.fsit.sohojnamaj.model.PrayerTime
import com.fsit.sohojnamaj.util.PrayerTimeUtil
import com.fsit.sohojnamaj.util.WaqtPrayerTimeUtil
import com.fsit.sohojnamaj.util.calender.primecalendar.hijri.HijriCalendar
import com.fsit.sohojnamaj.util.dateUtil.findClosest
import com.fsit.sohojnamaj.util.dateUtil.toDateFormat
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    lateinit var prayerTimeUtil: PrayerTimeUtil

    @Before
        fun setup(){

            val fixCalender=Calendar.getInstance()
            fixCalender.set(2023,2,4)
           // prayerTimeUtil= PrayerTimeUtil()
        }

    @Test
    fun addition_isCorrect() {
        val calendar = HijriCalendar()
        println("\n\n*****************\n"+calendar.longDateString)
        //assertEquals("১০, ফাল্গুন, ১৪২৯ বঙ্গাব্দ, বসন্ত কাল, রোজ বৃহস্পতিবার", Bongabdo().now())

        var asr ="16:28 (+06)".substringBefore(" ")
        print(asr)
        val dateFormat=SimpleDateFormat("HH:mm")
     assertEquals(SimpleDateFormat("yyyyMMdd").format(Date(1682478061L*1000)),"20230426")
        val  calender = Calendar.getInstance()
     assertEquals((calender.get(Calendar.MONTH)+1).toString(),"3")
      calender.add(Calendar.MONTH,1)
        assertEquals((calender.get(Calendar.MONTH)+1).toString(),"4")


    }

    @Test
    fun testEnglishToBangla(){
        //if(1680096783037 in 1680096778000.. 1680096782000)


        //assertEquals((1680096783494 in (1680096780000-2000).. (1680096780000+2000)),true)
    }
    @Test
    fun testDateConversion(){
        val  calender = Calendar.getInstance()
        val today =  SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).format(calender.time)
        calender.add(Calendar.DAY_OF_MONTH,1)
        val tommorow =  SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).format(calender.time)
        assertEquals(today,"20230305")
        assertEquals(tommorow,"20230306")

    }

    val prayerList = arrayListOf(


        PrayerTime(
            date="20230303",
            Asr="16:28 (+06)",
            Dhuhr="12:15 (+06)",
            Fajr="05:09 (+06)",
            Firstthird="22:13 (+06)",
            Imsak="04:59 (+06)",
            Isha="19:22 (+06)",
            Lastthird="02:18 (+06)",
            Maghrib="18:07 (+06)",
            Midnight="00:15 (+06)",
            Sunrise="06:23 (+06)",
            Sunset="06:23 (+06)"),
        PrayerTime(
            date="20230304",
            Asr="2023-03-01T16:28:00+06:00 (+06)",
            Dhuhr= "2023-03-01T12:16:00+06:00 (+06)",
            Fajr="2023-03-01T05:10:00+06:00 (+06)",
            Firstthird="22:13 (+06)",
            Imsak="04:58 (+06)",
            Isha="2023-03-01T19:21:00+06:00 (+06)",
            Lastthird="02:18 (+06)",
            Maghrib="2023-03-01T18:06:00+06:00 (+06)",
            Midnight="00:15 (+06)",
            Sunrise= "2023-03-01T06:25:00+06:00 (+06)",
            Sunset="06:23 (+06)"),
        PrayerTime(
            date="20230305",
            Asr= "2023-03-05T16:29:00+06:00 (+06)",
            Dhuhr="2023-03-05T12:15:00+06:00 (+06)",
            Fajr="2023-03-05T05:07:00+06:00 (+06)",
            Firstthird="22:13 (+06)",
            Imsak="04:57 (+06)",
            Isha="2023-03-05T19:23:00+06:00 (+06)",
            Lastthird="02:17 (+06)",
            Maghrib="2023-03-05T18:08:00+06:00 (+06)",
            Midnight="00:15 (+06)",
            Sunrise="2023-03-05T06:22:00+06:00 (+06)",
            Sunset="06:22 (+06)"
        ),
        PrayerTime(
            date="20230306",
            Asr= "2023-03-06T16:30:00+06:00 (+06)",
            Dhuhr="2023-03-06T12:14:00+06:00 (+06)",
            Fajr="2023-03-06T05:06:00+06:00 (+06)",
            Firstthird="22:13 (+06)",
            Imsak="04:57 (+06)",
            Isha="2023-03-06T19:23:00+06:00 (+06)",
            Lastthird="02:17 (+06)",
            Maghrib="2023-03-06T18:08:00+06:00 (+06)",
            Midnight="00:15 (+06)",
            Sunrise="2023-03-06T06:21:00+06:00 (+06)",
            Sunset="06:22 (+06)"
        )
    )




    @Test
    fun testISO86DateParser() {
        val df1: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val string1 = "2023-03-01T05:10:00+06:00 (+06)".substringBefore("+")
        val result1: Date = df1.parse(string1)
        println(SimpleDateFormat("hh:mm dd MMM yyyy").format(result1))

        val df2: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        val string2 = "2001-07-04T12:08:56.235-07:00"
        val result2: Date = df2.parse(string2)
        println(SimpleDateFormat("hh:mm dd MMM yyyy").format(result2))

    }
    @Test
    fun nearest() {

      val nearestList = listOf(1678057560000,1678083240000,1678098540000,1678104480000,1678108980000)
    }
    @Test
    fun nextPrayerName() {

        assertEquals(prayerTimeUtil.prayerNameList[prayerTimeUtil.nextPrayName()!!.id],"Next Fajr")
    }

    @Test fun compareCalanderAndSystem(){
        val calender = Calendar.getInstance()
        assertEquals(calender.timeInMillis,System.currentTimeMillis())
    }


}