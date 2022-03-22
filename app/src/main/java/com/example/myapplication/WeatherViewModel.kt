package com.example.myapplication

import android.content.Context
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.json.JSONObject

class WeatherViewModel: ViewModel() {
    var weatherRepo: WeatherRepo = WeatherRepo()
    var weatherStr : MutableLiveData<JSONObject> = MutableLiveData<JSONObject>()


    fun getWeatherData(loc: String, context: Context, serverCallback: ServerCallback) {
        viewModelScope.launch {
            var result = weatherRepo.makeWeatherRequest(loc, context, serverCallback)
        }
    }
}