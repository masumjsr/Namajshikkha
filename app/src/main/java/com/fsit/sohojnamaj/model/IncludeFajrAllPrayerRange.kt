package com.fsit.sohojnamaj.model

data class IncludeFajrAllPrayerRange(
    val allPrayerRange: AllPrayerRange,
    val nextFajrRange:PrayerRange=PrayerRange(),

    )
