package com.example.myapplication

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Users")
data class Profile(
    @PrimaryKey(autoGenerate = true)
    var uID : Int,

    @ColumnInfo(name= "name")
    var name: String?,
    @ColumnInfo(name= "height")
    var height: String?, // saved as the index selection in the spinner (0 = 4' tall)
    @ColumnInfo(name= "weight")
    var weight: String?,
    @ColumnInfo(name= "age")
    var age: String?,
    @ColumnInfo(name= "gender")
    var gender: String?,
    @ColumnInfo(name= "city")
    var city: String?,
    @ColumnInfo(name= "country")
    var country: String?,
    @ColumnInfo(name= "imagePath")
    var imagePath: String?,
    @ColumnInfo(name= "active")
    var active: String?,
    @ColumnInfo(name= "bmr")
    var bmr: String?,
    @ColumnInfo(name= "bmi")
    var bmi: String?,
    @ColumnInfo(name= "regimen")
    var regimen: String?,
    @ColumnInfo(name= "weightGoal")
    var weightGoal: String?,
    @ColumnInfo(name= "image")
    var image: Bitmap?,
)
