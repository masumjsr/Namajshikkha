package com.fsit.sohojnamaj.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fsit.sohojnamaj.R

@Entity(tableName = "name")
data class Name(
    val serial:String,
    val arabic:String,
    val meaning:String,
    val fajoliot:String,
    val logo:Int,
    val audio:Int,
    @PrimaryKey(autoGenerate = true)
val id:Int=0,
)

val sampleName = listOf(

        Name(
            "১ ",
            "আল্লাহ",
            "আল্লাহ",
            "প্রত্যহ ১০০০ বার এই নামের যিকির করলে ঈমান দৃঢ় ও মযবুত হয়।",
            R.drawable.a_01_allah,
            R.raw.a_01_allah
        )
    ,

            Name(
                "২ ",
                "আর রাহমান",
                "পরম দয়ালু",
                "প্রত্যেক নামাযের পর ১০০ বার পড়লে, ইনশাআল্লাহ্‌ তার অন্তর থেকে সব ধরনের কঠোরতা ও অলসতা দূর হয়ে যাবে।",
                R.drawable.a_02_ar_rahman,
                R.raw.a_02_ar_rahman
            )
            ,

            Name(
                "৩ ",
                "আর-রহী'ম",
                "অতিশয়-মেহেরবান",
                "প্রত্যেক নামাযের পর ১০০ বার করে পাঠ করলে, ইনশাআল্লাহ্‌ পৃথিবীর সকল বিপদ আপদ থেকে নিরাপদ থাকবে।",
                R.drawable.a_03_ar_rahim,
                R.raw.a_03_ar_rahim
            )
           ,

            Name(
                "৪ ",
                "আল-মালিক",
                "সর্বকর্তৃত্বময়",
                "ফযরের নামাজের পর অধিকহারে পাঠ করবে, আল্লাহ্‌ তায়ালা তাকে ধনবান করে দিবেন।",
                R.drawable.a_04_al_malik,
                R.raw.a_04_al_malik
            )
            ,

            Name(
                "৫ ",
                "আল-কুদ্দুস",
                "নিষ্কলুষ, অতি পবিত্র",
                "প্রত্যহ শেষ রাতে (উয়া কুদ্দূসু) নামুটি ১০০০ বার পড়লে রোগ ব্যধি থেকে মুক্ত থাকা যায়।",
                R.drawable.a_05_al_kuddsh,
                R.raw.a_05_al_kuddsh
            )

)
