package com.example.myapplication

import android.content.Context

class StepCounterRepo {
    lateinit var stepCounterDao: StepCounterDao
    lateinit var db: StepCounterDatabase

    fun createDb(context: Context) {
        db = StepCounterDatabase.getInstance(context)
        stepCounterDao = db.stepCounterDao
    }

    suspend fun insertSteps(stepData: StepCountData) {
        stepCounterDao.insert(stepData)
    }

    suspend fun updateProfile(stepData: StepCountData) {
        stepCounterDao.update(stepData)
    }
}