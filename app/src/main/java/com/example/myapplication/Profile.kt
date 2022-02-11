package com.example.myapplication

import android.graphics.Bitmap

class Profile(
    var name: String,
    var height: String,
    var weight: String,
    var age: String,
    var sex: String,
    var city: String,
    var imagePath: String,
    var active: String,
    var bmr: String,
    var weightGoal: String,
    var image: Bitmap?,
){
    init {
        if (this.name == "")
            this.name = "NA"

        if (this.height == "")
            this.height = "NA"

        if (weight == "")
        {
            weight = "NA"
        }

        if (age == "")
        {
            age = "NA"
        }

        if (sex == "")
        {
            sex = "NA"
        }

        if (city == "")
        {
            city = "NA"
        }

        if (imagePath == "")
        {
            imagePath = "NA"
        }

        active = "0"
        weightGoal = "0"
    }

    fun printForStoring(): String {
        return "${this.name} ${this.height} ${this.weight} " +
                "${this.age} ${this.sex} ${this.city} " +
                "${this.imagePath} ${this.active} " +
                "${this.weightGoal}"
    }
}

