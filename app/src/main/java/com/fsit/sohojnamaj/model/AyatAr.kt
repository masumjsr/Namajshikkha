package com.fsit.sohojnamaj.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ayat_ar")
data class AyatAr(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val sura: Int,
    val VerseIDAr: Int,
    val ayat:String

)

val sampleAyatAr= arrayOf(
    AyatAr(0,0,0,"بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ"),
    AyatAr(0,0,0,"الْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ"),
    AyatAr(0,0,0,"الرَّحْمَٰنِ الرَّحِيمِ"),
    AyatAr(0,0,0, "مَالِكِ يَوْمِ الدِّينِ"),
)
