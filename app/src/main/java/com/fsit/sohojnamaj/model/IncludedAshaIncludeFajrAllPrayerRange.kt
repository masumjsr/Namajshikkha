package com.fsit.sohojnamaj.model

data class IncludedAshaIncludeFajrAllPrayerRange(
    val allPrayerRange: IncludeFajrAllPrayerRange,
    val previousAshaRange:PrayerRange=PrayerRange(),

    )
