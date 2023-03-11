package com.fsit.sohojnamaj.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="sura")
data class Sura(
    @PrimaryKey(autoGenerate = false)
    val sura_no:Int,
    val sura_name:String,
    val sura_mean:String,
    val total_ayat:String,
    val sura_discended:String,
    val audioFile:String
)

val sampleSura= arrayListOf<Sura>(
    Sura(0,"আল ফাতিহা","সূচনা","রুকু-১, আয়াত-৭","মক্কায় অবতীর্ণ","001-al-fatihah.mp3"),
    Sura(0,"আল বাকারা","বকনা-বাছুর","রুকু-৪০, আয়াত-২৮৬","মদীনায় অবতীর্ণ","001-al-fatihah.mp3"),
    Sura(0,"আল বাকারা","বকনা-বাছুর","রুকু-৪০, আয়াত-২৮৬","মদীনায় অবতীর্ণ","001-al-fatihah.mp3"),
    Sura(0,"আল বাকারা","বকনা-বাছুর","রুকু-৪০, আয়াত-২৮৬","মদীনায় অবতীর্ণ","001-al-fatihah.mp3"),
    Sura(0,"আল বাকারা","বকনা-বাছুর","রুকু-৪০, আয়াত-২৮৬","মদীনায় অবতীর্ণ","001-al-fatihah.mp3"),
    Sura(0,"আল বাকারা","বকনা-বাছুর","রুকু-৪০, আয়াত-২৮৬","মদীনায় অবতীর্ণ","001-al-fatihah.mp3"),

)