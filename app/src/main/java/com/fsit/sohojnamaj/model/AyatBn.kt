package com.fsit.sohojnamaj.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ayat_bn")
data class AyatBn(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val sura: Int,
    val aya: Int,
    val text:String

)
val sampleAyatBn= arrayOf(
    AyatBn(0,0,0,"শুরু করছি আল্লাহর নামে যিনি পরম করুণাময়, অতি দয়ালু।"),
    AyatBn(0,0,0, "যাবতীয় প্রশংসা আল্লাহ তাআলার যিনি সকল সৃষ্টি জগতের পালনকর্তা।"),
    AyatBn(0,0,0,  "যিনি নিতান্ত মেহেরবান ও দয়ালু।"),
    AyatBn(0,0,0,  "যিনি বিচার দিনের মালিক।"),
)
