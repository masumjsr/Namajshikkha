package com.fsit.sohojnamaj.model

data class Prayer(
    val name: String? = null,
    val prayer: PrayerRange? = null,
    val index: Int = 0,
    val text: String = "",
    val timeLeft: String = "",
    val progress: Float = 0f,
    val next: String = "",
    val nextText: String = "",
    val forbiddenRange: Boolean = false,
    val isIfterOver: Boolean = false,
    val nextSahari: String = "",
    val nextIfter: String = "",
    val nextTimeLeft: String = "",
    val forbiddenTime: List<ForbiddenTime> = emptyList(),
    val all: List<PrayerRange> = emptyList(),
    val hijriOffset: Int =0
)
