package com.fsit.sohojnamaj.model

data class Ayat(
    val ar: AyatAr,
    val bn: AyatBn,
)
val sampleAyat= listOf<Ayat>(
    Ayat(sampleAyatAr[0], sampleAyatBn[0]),
    Ayat(sampleAyatAr[1], sampleAyatBn[1]),
    Ayat(sampleAyatAr[2], sampleAyatBn[2]),
    Ayat(sampleAyatAr[3], sampleAyatBn[3]),
)
