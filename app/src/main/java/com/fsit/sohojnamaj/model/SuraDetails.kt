package com.fsit.sohojnamaj.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sura_details")
data class SuraDetails(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val sura:Int,
    val ayatNumber:Int,
    val arabic:String,
    val ayat:String,
    val text:String
)
val sampleSuraDetails= listOf(
    SuraDetails(0,0,1,"اَلْحَمْدُ لِلّٰهِ رَبِّ الْعٰلَمِیْنَۙ","আল হামদুলিল্লা-হি রাব্বিল ‘আ-লামীন।","সকল প্রশংসা জগত সমূহের প্রতিপালক আল্লাহরই,"),
    SuraDetails(0,0,2,"الرَّحْمٰنِ الرَّحِیْمِۙ","আররাহমা-নির রাহীম।","যিনি দয়াময়, পরম দয়ালু,"),
    SuraDetails(0,0,3,"مٰلِكِ یَوْمِ الدِّیْنِؕ","আল হামদুলিল্লা-হি রাব্বিল ‘আ-লামীন।","সকল প্রশংসা জগত সমূহের প্রতিপালক আল্লাহরই,"),
    SuraDetails(0,0,4,"اَلْحَمْدُ لِلّٰهِ رَبِّ الْعٰلَمِیْنَۙ","মা-লিকি ইয়াওমিদ্দীন।","কর্মফল দিবসের মালিক।"),
    SuraDetails(0,0,5,"اَلْحَمْدُ لِلّٰهِ رَبِّ الْعٰلَمِیْنَۙ","আল হামদুলিল্লা-হি রাব্বিল ‘আ-লামীন।","সকল প্রশংসা জগত সমূহের প্রতিপালক আল্লাহরই,"),
    SuraDetails(0,0,6,"اَلْحَمْدُ لِلّٰهِ رَبِّ الْعٰلَمِیْنَۙ","আল হামদুলিল্লা-হি রাব্বিল ‘আ-লামীন।","সকল প্রশংসা জগত সমূহের প্রতিপালক আল্লাহরই,"),
)
