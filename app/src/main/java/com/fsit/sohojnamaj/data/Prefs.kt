package com.fsit.sohojnamaj.data

import com.fsit.sohojnamaj.R

object Prefs {
    const val PREF_KEY_MUADZIN = "pref_key_muadzin"

    val muadzin: Int
    get() =com.pixplicity.easyprefs.library.Prefs.getInt(PREF_KEY_MUADZIN, R.raw.mansour_zahrany)
}