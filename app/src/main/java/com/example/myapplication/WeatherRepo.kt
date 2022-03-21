package com.example.myapplication

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

class WeatherRepo {

    fun makeWeatherRequest(loc : String){

    }

}