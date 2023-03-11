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

)