package com.example.myapplication

import java.lang.Exception

class HealthCalculator {
    fun calculateBMR(weight: Int, height: Int, age: Int, activity: Int, sex: String): Int {
        return -1
    }

    fun calculateBMI(weight: String, height: String): String {
        try {
            return (703 * (weight.toDouble() / Math.pow(height.toDouble(), 2.0))).toInt().toString()
        }
        catch (e: Exception){
            return "enter valid height and weight"
        }

    }
}