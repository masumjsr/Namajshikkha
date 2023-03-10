package com.fsit.sohojnamaj.model

import com.google.android.gms.maps.model.LatLng

data class PrayerPreferenceModel(
    val offsetModel: OffsetModel=OffsetModel(),
    val latLng: LatLng=LatLng(0.0,0.0),
    val soundModel: SoundModel=SoundModel(),
    val hijri: Int=0,
    val method: Int =0,
    val majhab: Int = 0,
    val sound: Int = 0,
    val vibration: Int = 0,
    val darkMode: Int = 0

)
