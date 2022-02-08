package com.example.myapplication

import java.io.File

class Profile (
    var name: String,
    var height: String,
    var weight: String,
    var age: String,
    var gender: String,
    var city: String,
    var imagePath: String,
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
    }

    fun printForStoring(): String {
        return "${this.name} ${this.height} ${this.weight} ${this.age} ${this.gender} ${this.city} ${this.imagePath}"
    }
}

