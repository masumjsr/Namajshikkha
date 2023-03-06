package com.fsit.sohojnamaj.data

import com.fsit.sohojnamaj.R
import com.fsit.sohojnamaj.constants.RingType
import com.pixplicity.easyprefs.library.Prefs

object Prefs {
    const val PREF_KEY_MUADZIN = "pref_key_muadzin"
    const val PREF_KEY_RING_FAJR = "pref_adzan_fajr"
    const val PREF_KEY_RING_DHUHR = "pref_adzan_dhuhr"
    const val PREF_KEY_RING_ASR = "pref_adzan_asr"
    const val PREF_KEY_RING_MAGHRIB = "pref_adzan_maghrib"
    const val PREF_KEY_RING_ISYA = "pref_adzan_isya"

    val muadzin: Int
    get() =com.pixplicity.easyprefs.library.Prefs.getInt(PREF_KEY_MUADZIN, R.raw.mansour_zahrany)

    var ringFajr: Int
        get() = Prefs.getInt(PREF_KEY_RING_FAJR, RingType.SOUND)
        set(value) {
            Prefs.putInt(PREF_KEY_RING_FAJR, value)
        }
    var ringDhuhr: Int
        get() = Prefs.getInt(PREF_KEY_RING_DHUHR, RingType.SOUND)
        set(value) {
            Prefs.putInt(PREF_KEY_RING_DHUHR, value)
        }
    var ringAsr: Int
        get() = Prefs.getInt(PREF_KEY_RING_ASR, RingType.SOUND)
        set(value) {
            Prefs.putInt(PREF_KEY_RING_ASR, value)
        }
    var ringMaghrib: Int
        get() = Prefs.getInt(PREF_KEY_RING_MAGHRIB, RingType.SOUND)
        set(value) {
            Prefs.putInt(PREF_KEY_RING_MAGHRIB, value)
        }
    var ringIsya: Int
        get() = Prefs.getInt(PREF_KEY_RING_ISYA, RingType.SOUND)
        set(value) {
            Prefs.putInt(PREF_KEY_RING_ISYA, value)
        }

}