package com.example.myapplication

import android.util.Log
import java.lang.Exception
import kotlin.math.pow

class HealthCalculator {
    fun calculateBMR(weight: String, height: String, age: String, activity: String, sex: String): String {
        try {
            var activityLevel: Double
            when (activity.toInt()) {
                1 -> activityLevel = 1.7
                else -> activityLevel = 1.2
            }
            when (sex.uppercase()) {
                "MALE" -> return (activityLevel * (66 + (6.23 * weight.toDouble()) + (12.7 * height.toDouble()) - (6.8 * age.toDouble()))).toString()
                "FEMALE" -> return (activityLevel * (66 + (4.35 * weight.toDouble()) + (4.7 * height.toDouble()) - (4.7 * age.toDouble()))).toString()
                else -> return (activityLevel * (66 + (8.4 * weight.toDouble()) + (8.7 * height.toDouble()) - (5.8 * age.toDouble()))).toString()
            }
        }
        catch (e: Exception) {
            Log.i("test", e.toString())
            return "Invalid"
        }
    }

    fun calculateDailyCalories (bmr: String, weightGoal: String, sex: String): String {
        try {
            var dailyCalories: Int = (bmr.toDouble() + (weightGoal.toDouble() * 500)).toInt()

            when(sex.uppercase()) {
                "MALE" -> {
                    if (dailyCalories < 1200)
                    {
                        //TODO: implement warning
                    }
                }
                "FEMALE" -> {
                    if (dailyCalories < 1000)
                    {
                        //TODO: implement warning
                    }
                }
                else -> {
                    if (dailyCalories < 1100)
                    {
                        //TODO: implement warning
                    }
                }
            }

            return dailyCalories.toString()
        } catch (e: Exception) {
            Log.i("test", e.toString())
            return "Invalid"
        }

    }

    fun calculateBMI(weight: String, height: String): String {
        return try {
            (703 * (weight.toDouble() / height.toDouble().pow(2.0))).toInt().toString()
        } catch (e: Exception){
            "Invalid Values"
        }

    }
}