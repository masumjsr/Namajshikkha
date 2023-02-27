package com.fsit.sohojnamaj

import com.fsit.sohojnamaj.util.calender.primecalendar.hijri.HijriCalendar
import com.fsit.sohojnamaj.util.calender.bangla.Bongabdo
import org.junit.Test

import org.junit.Assert.*
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val calendar = HijriCalendar()
        println("\n\n*****************\n"+calendar.longDateString)
        assertEquals("১০, ফাল্গুন, ১৪২৯ বঙ্গাব্দ, বসন্ত কাল, রোজ বৃহস্পতিবার", Bongabdo().now())
    }

    @Test
    fun testEnglishToBangla(){

    }
}