package com.fsit.sohojnamaj.util.calender.bangla

val banglaMonths = arrayOf(
"বৈশাখ",
"জ্যৈষ্ঠ",
"আষাঢ়",
"শ্রাবণ",
"ভাদ্র",
"আশ্বিন",
"কার্তিক",
"অগ্রহায়ণ",
"পৌষ",
"মাঘ",
"ফাল্গুন",
"চৈত্র"
)
val banglaMonthsEn = arrayOf(
"Boishakh",
"Joishţho",
"Ashar",
"Srabon",
"Bhadro",
"Aash-shin",
"Kartik",
"Ôgrohaion",
"Poush",
"Magh",
"Falgun",
"Choitro"
)

val banglaWeekDays = mapOf(
    1 to "রবিবার",
    2 to "সোমবার",
3 to "মঙ্গলবার",
4 to "বুধবার",
5 to "বৃহস্পতিবার",
6 to "শুক্রবার",
7 to "শনিবার"
)




val banglaSeasons = arrayOf("গ্রীষ্ম", "বর্ষা", "শরৎ", "হেমন্ত", "শীত", "বসন্ত")

val totalDaysInMonthNew = arrayOf(31, 31, 31, 31, 31, 30, 30, 30, 30, 30, 30, 30)

fun isLeapYear( year:Int) =
((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);

fun translateNumbersToBangla( inputNumber:String):String {
    var number = inputNumber
    number = number.replace("0", "০");
    number = number.replace("1", "১");
    number = number.replace("2", "২");
    number = number.replace("3", "৩");
    number = number.replace("4", "৪");
    number = number.replace("5", "৫");
    number = number.replace("6", "৬");
    number = number.replace("7", "৭");
    number = number.replace("8", "৮");
    number = number.replace("9", "৯");
    return number;
}
