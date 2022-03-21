package com.example.myapplication

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel

class Repository : ViewModel() {
    lateinit var curProfile: Profile;

    fun createProfile (name: String?,
                       height: String?, // saved as the index selection in the spinner (0 = 4' tall)
                       weight: String?,
                       age: String?,
                       gender: String?,
                       city: String?,
                       country: String?,
                       imagePath: String?,
                       active: String?,
                       bmr: String?,
                       bmi: String?,
                       regimen: String?,
                       weightGoal: String?,
                       image: Bitmap?) {
        curProfile = Profile(name, height, weight, age, gender, city, country, imagePath, active, bmr, bmi, regimen, weightGoal, image)
    }
}