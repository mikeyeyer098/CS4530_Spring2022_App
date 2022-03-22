package com.example.myapplication

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.launch
import org.json.JSONObject

class WeatherViewModel(
    private val weatherRepo: WeatherRepo,
    private var weatherStr : MutableLiveData<JSONObject>
): ViewModel() {
    fun getWeatherData(loc : String, context : Context): JSONObject? {
        viewModelScope.launch {
            var result = weatherRepo.makeWeatherRequest(loc, context)
            weatherStr.value = result.value
        }
        return weatherStr.value
    }
}