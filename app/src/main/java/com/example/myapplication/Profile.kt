package com.example.myapplication

import android.graphics.Bitmap

class Profile(
    var name: String,
    var height: String, // saved as the index selection in the spinner (0 = 4' tall)
    var weight: String,
    var age: String,
    var gender: String,
    var city: String,
    var country: String,
    var imagePath: String,
    var active: String,
    var bmr: String,
    var bmi: String,
    var regimen: String,
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

        regimen = "0"
        active = "0"
        weightGoal = "0"
    }

    fun printForStoring(): String {
        return "${this.name} ${this.height} ${this.weight} " +
                "${this.age} ${this.gender} ${this.city} ${this.country} " +
                "${this.imagePath} ${this.active} " +
                "${this.weightGoal} ${this.regimen} " +
                "${this.bmr} ${this.bmi} "
    }
}

