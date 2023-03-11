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
