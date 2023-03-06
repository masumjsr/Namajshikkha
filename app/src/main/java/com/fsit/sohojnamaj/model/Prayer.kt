package com.fsit.sohojnamaj.model

data class Prayer(
    val name: String?=null,
    val prayer:PrayerRange?=null,
    val index:Int=0,
    val text:String="",
    val timeLeft:String="",
    val progress:Float=0f
)
