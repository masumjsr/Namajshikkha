package com.fsit.sohojnamaj.model.dua

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "type_two")
data class TypeTwoItem(
    @PrimaryKey(autoGenerate = false)
    val id:Int,
    val sub_category:Int,
    val arabic:String?,
    val bengali:String?,
    val meaning:String?,
    val english:String?,
    val other:String?

)

val sampleTypeTwo= listOf(
    TypeTwoItem(0,0,"نَوَيْتُ الْغُسْلَ لِرَفْعِ الْجَنَابَةِ","নাওয়াইতুল গুসলা লিরাফয়ি’ল্ জানাবাতি।","আমি নাপাকি দূর করিবার জন্য গোসলের নিয়ত করিতেছি।","I intend to take a bath to get rid of the impurity.",null)
)
