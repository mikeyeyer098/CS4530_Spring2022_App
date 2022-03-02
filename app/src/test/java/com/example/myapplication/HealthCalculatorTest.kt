package com.example.myapplication

import org.junit.Assert.*

class HealthCalculatorTest {

    @org.junit.Test
    fun testCalculateBMRSedentary() {
        val weight: String = "128";
        val height: Int = 61;
        // value 6 is 23 years old
        val age: String = "6";
        // 0 is sedentary
        val activityLevel: String = "0";
        // 2 is Male
        val sex: String = "Male"

        assertEquals(HealthCalculator().calculateBMR(weight, height, age, activityLevel, sex), "1917")
    }

    @org.junit.Test
    fun testCalculateBMRActive() {
        val weight: String = "128";
        val height: Int = 61;
        // value 6 is 23 years old
        val age: String = "6";
        // 1 is active
        val activityLevel: String = "1";
        // 2 is Male
        val sex: String = "Male"

        assertEquals(HealthCalculator().calculateBMR(weight, height, age, activityLevel, sex), "2715")
    }

    @org.junit.Test
    fun calculateDailyCalories() {
        val bmr: String = "1917";
        val weightGoal: String = "2";
        val sex: String = "MALE"

        assertEquals(HealthCalculator().calculateDailyCalories(bmr, weightGoal, sex), "2917")
    }

    @org.junit.Test
    fun calculateBMI() {
        val weight: String = "128";
        // 5'0" to inches is 61
        val height: String = "61";

        assertEquals(HealthCalculator().calculateBMI(weight, height), "24")
    }

    @org.junit.Test
    fun calculateBMIEdgeCaseZeroWeight() {
        val weight: String = "0";
        val height: String = "61";

        assertEquals(HealthCalculator().calculateBMI(weight, height), "0")
    }

    @org.junit.Test
    fun calculateBMIEdgeCaseZeroBoth() {
        val weight: String = "0";
        val height: String = "0";

        assertEquals(HealthCalculator().calculateBMI(weight, height), "0")
    }
}