package com.fsit.sohojnamaj.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="sura")
data class Sura(
    @PrimaryKey(autoGenerate = false)
    val sura_no:Int,
    val sura_name:String,
    val para:Int,
    val total_ayat:Int,
    val eng_name:String,
)

val sampleSura= arrayListOf<Sura>(
    Sura(0,"আল ফাতিহা",0,7,""),
    Sura(0,"আল বাকারা",0,286,""),
    Sura(0,"আল ইমরান",0,260,""),
    Sura(0,"আল ফাতিহা",0,7,""),
    Sura(0,"আল ফাতিহা",0,7,""),
    Sura(0,"আল ফাতিহা",0,7,""),
    Sura(0,"আল ফাতিহা",0,7,""),
    Sura(0,"আল ফাতিহা",0,7,""),
    Sura(0,"আল ফাতিহা",0,7,""),
)