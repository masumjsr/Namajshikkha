package com.fsit.sohojnamaj.model

data class AllPrayerRange(
    val previousIsha:PrayerRange= PrayerRange(),
    val fajrRange:PrayerRange=PrayerRange(),
    val morningForbiddenRange:PrayerRange=PrayerRange(),
    val duhaRange:PrayerRange=PrayerRange(),
    val noonForbidden:PrayerRange=PrayerRange(),
    val dhurRange:PrayerRange=PrayerRange(),
    val asrRange:PrayerRange=PrayerRange(),
    val eveningForbidden:PrayerRange=PrayerRange(),
    val magribRange:PrayerRange=PrayerRange(),
    val ishaRange:PrayerRange=PrayerRange(),
    val nextFajrRange:PrayerRange=PrayerRange(),
    val nextMagribRange:PrayerRange=PrayerRange(),

    )
