package com.example.myapplication

import android.graphics.Bitmap

class Profile(
    var name: String,
    var height: String,
    var weight: String,
    var age: String,
    var gender: String,
    var city: String,
    var imagePath: String,
    var active: Boolean,
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

        if (gender == "")
        {
            gender = "NA"
        }

        if (city == "")
        {
            city = "NA"
        }

        if (imagePath == "")
        {
            imagePath = "NA"
        }

        active = false
        bmr = "NA"
        weightGoal = "NA"
    }

    fun printForStoring(): String {
        return "${this.name} ${this.height} ${this.weight} " +
                "${this.age} ${this.gender} ${this.city} " +
                "${this.imagePath}, ${this.active} ${this.bmr}" +
                "${this.weightGoal}"
    }
}

