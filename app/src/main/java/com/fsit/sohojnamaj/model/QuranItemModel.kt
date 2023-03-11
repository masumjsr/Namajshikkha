package com.fsit.sohojnamaj.model

class QuranItemModel(
    var tv_Sura_Number: String,
    var tv_Sura_Name: String,
    var tv_Sura_Meaning: String,
    var tv_Sura_Ayat_No: String,
    var tv_Sura_Discended: String,
    var jSonNumber: String,
    var tvShaneNujul: String,
    var tvFojilot: String,
    var audiLinken: String,
    var audioLinkBn: String,
    var audioFileName: String,
    var number1: String,
    var number2: String
) {

    fun getjSonNumber(): String {
        return jSonNumber
    }

    fun setjSonNumber(jSonNumber: String) {
        this.jSonNumber = jSonNumber
    }
}