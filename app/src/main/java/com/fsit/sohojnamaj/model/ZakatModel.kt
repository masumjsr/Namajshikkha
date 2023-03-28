package com.fsit.sohojnamaj.model

data class ZakatModel(
    val id:Int,
    val title:String,
    var amount:String,
    val isAdding:Boolean
)
val modelList=
    arrayListOf(
        ZakatModel(0,"স্বর্ণের মুল্য","",true),
        ZakatModel(1,"রুপার মুল্য","",true),
        ZakatModel(2,"ক্যাশ টাকা","",true),
        ZakatModel(3,"ব্যাংক একাউন্টে ক্যাশ টাকা","",true),
        ZakatModel(4,"বিনিয়োগ/শেয়ার/তহবিল","",true),
        ZakatModel(5,"অন্যান্য বিনিয়োগ","",true),
        ZakatModel(6,"বাসা ভাড়া থেকে প্রাপ্ত আয়","",true),
        ZakatModel(7,"অন্যান্য সম্পত্তি","",true),
        ZakatModel(8,"ব্যবসায় জমাকৃত পণ্য","",true),
        ZakatModel(9,"কৃষিকাজ থেকে প্রাপ্ত আয়ের পরিমান","",true),
        ZakatModel(10,"চতুষ্পদ জন্তু বা গবাদি পশু / মৎস্য","",true),
        ZakatModel(11,"পেনশন থেকে প্রাপ্ত আয়","",true),
        ZakatModel(12,"জমিদারী সম্পদ","",true),
        ZakatModel(13,"মুল্যবান পাথর","",true),
        ZakatModel(14,"ক্রেডিট কার্ডের ঋণের পরিমান","",false),
        ZakatModel(15,"গাড়ির ঋণের পরিমান","",false),
        ZakatModel(16,"ব্যবসার জন্য ঋণের পরিমান","",false),
        ZakatModel(17,"পারিবারিক ঋণের পরিমান","",false),
        ZakatModel(18,"অন্যান্য ঋন","",false),
    )