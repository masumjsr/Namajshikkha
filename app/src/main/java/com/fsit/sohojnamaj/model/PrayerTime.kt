package com.fsit.sohojnamaj.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName ="prayerTime")
data class PrayerTime(
    @PrimaryKey(autoGenerate = false)
    val date:String="",
    val Asr: String="",
    val Dhuhr: String="",
    val Fajr: String="",
    val Firstthird: String="",
    val Imsak: String="",
    val Isha: String="",
    val Lastthird: String="",
    val Maghrib: String="",
    val Midnight: String="",
    val Sunrise: String="",
    val Sunset: String="",
)