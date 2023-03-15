package com.fsit.sohojnamaj.model.dua

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sub_category")
data class SubCategory (
    @PrimaryKey(autoGenerate = false)
    val id:Int,
    val category:Int,
    val type:Int=0,
    val title:String,
)
val sampleSubCategory= listOf(
    SubCategory(0,0,0,"গোসল কি?"),
    SubCategory(0,0,0,"গোসল চার-প্রকার"),
    SubCategory(0,0,0,"গোসলের নিয়ত")
)