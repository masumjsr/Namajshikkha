package com.aminography.primecalendar.common

import com.aminography.primecalendar.PrimeCalendar
import com.fsit.sohojnamaj.util.calender.primecalendar.civil.CivilCalendar
import com.fsit.sohojnamaj.util.calender.primecalendar.hijri.HijriCalendar
import java.util.*

/**
 * @author aminography
 */

/**
 * Returns an instance of [CivilCalendar] which is equivalent to the time of the current calendar.
 */
fun Calendar.toCivil(): CivilCalendar =
    CivilCalendar(timeZone).also { it.adjustWith(this) }

/**
 * Returns an instance of [PersianCalendar] which is equivalent to the time of the current calendar.
 */

/**
 * Returns an instance of [HijriCalendar] which is equivalent to the time of the current calendar.
 */
fun Calendar.toHijri(): HijriCalendar =
    HijriCalendar(timeZone).also { it.adjustWith(this) }

/**
 * Returns an instance of [JapaneseCalendar] which is equivalent to the time of the current calendar.
 */

/**
 * Returns an instance of [PrimeCalendar] which is equivalent to the time of the current calendar.
 */
fun Calendar.toPrimeCalendar(calendarType: CalendarType): PrimeCalendar =
    CalendarFactory.newInstance(calendarType, timeZone).also { it.adjustWith(this) }