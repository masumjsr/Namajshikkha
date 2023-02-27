package com.aminography.primecalendar.common

import com.aminography.primecalendar.PrimeCalendar
import com.fsit.sohojnamaj.util.calender.primecalendar.civil.CivilCalendar
import com.fsit.sohojnamaj.util.calender.primecalendar.hijri.HijriCalendar
import java.util.*

/**
 * A factory class which creates calendar objects based on desired type.
 *
 * @author aminography
 */
@Suppress("unused")
object CalendarFactory {

    @JvmStatic
    fun <T : PrimeCalendar> newInstance(clazz: Class<T>): T = clazz.getDeclaredConstructor().newInstance()

    @JvmStatic
    fun newInstance(type: CalendarType): PrimeCalendar {
        return when (type) {
            CalendarType.CIVIL -> CivilCalendar()
            CalendarType.HIJRI -> HijriCalendar()
        }
    }

    @JvmStatic
    fun newInstance(type: CalendarType, locale: androidx.compose.ui.text.intl.Locale): PrimeCalendar {
        return when (type) {
            CalendarType.CIVIL -> CivilCalendar(locale = locale)
            CalendarType.HIJRI -> HijriCalendar(locale = locale)
        }
    }

    @JvmStatic
    fun newInstance(type: CalendarType, timeZone: TimeZone): PrimeCalendar {
        return when (type) {
            CalendarType.CIVIL -> CivilCalendar(timeZone)
            CalendarType.HIJRI -> HijriCalendar(timeZone)
        }
    }

    @JvmStatic
    fun newInstance(type: CalendarType, timeZone: TimeZone, locale: androidx.compose.ui.text.intl.Locale): PrimeCalendar {
        return when (type) {
            CalendarType.CIVIL -> CivilCalendar(timeZone, locale)
            CalendarType.HIJRI -> HijriCalendar(timeZone, locale)
        }
    }

}
