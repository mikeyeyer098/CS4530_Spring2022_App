package com.example.myapplication

import androidx.lifecycle.ViewModel

class StepCounterPageViewModel : ViewModel() {
    var repo: StepCounterRepo = StepCounterRepo()

    suspend fun updateSteps (stepData: StepCountData) {
        repo.stepCounterDao.update(stepData)
    }

    suspend fun insertSteps (stepData: StepCountData){
        repo.stepCounterDao.insert(stepData)
    }


    suspend fun checkExists (curUserName: String) : Boolean{
        return repo.stepCounterDao.exists(curUserName)
    }


    suspend fun getStepData (curUserName : String): List<StepCountData>? {
        return repo.stepCounterDao.get(curUserName)
    }
}