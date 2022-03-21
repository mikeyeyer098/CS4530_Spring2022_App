package com.example.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val weatherRepo: WeatherRepo
): ViewModel() {
    fun getWeatherData(loc : String){
        viewModelScope.launch {

            val result = try {
                weatherRepo.makeWeatherRequest(loc)
            } catch(e: Exception) {
                Result.Error(Exception("Network request failed"))
            }
        }
    }
}