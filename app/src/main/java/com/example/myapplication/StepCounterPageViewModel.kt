package com.example.myapplication

import androidx.lifecycle.ViewModel

class StepCounterPageViewModel : ViewModel() {
    var repo: StepCounterRepo = StepCounterRepo()
    lateinit var curUserName: String

    suspend fun updateSteps (stepData: StepCountData) {
        repo.updateProfile(stepData)
    }

    suspend fun getStepData (): StepCountData? {
        // TODO need to get the user name here
        return repo.stepCounterDao.get(curUserName)
    }
}