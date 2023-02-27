package com.fsit.sohojnamaj.model

data class Hijri(
    val date: String,
    val day: String,
    val designation: Designation,
    val format: String,
    val holidays: List<Any>,
    val month: MonthX,
    val weekday: WeekdayX,
    val year: String
)