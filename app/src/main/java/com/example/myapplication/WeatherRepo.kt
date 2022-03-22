package com.example.myapplication

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

class WeatherRepo {

    private var jsonResponse = MutableLiveData<JSONObject>()

    fun makeWeatherRequest(loc : String, context : Context): MutableLiveData<JSONObject> {
        val id = "4dbd2ed7d890d4f83982194472e820f5"
        val url = " https://api.openweathermap.org/data/2.5/weather?q=${loc?.replace("\\s".toRegex(), "")}&appid=4dbd2ed7d890d4f83982194472e820f5"
        val queue = Volley.newRequestQueue(context)

        //Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                // Display the first 500 characters of the response string.
                jsonResponse.value = JSONObject(response)
            },

            {})
            queue.add(stringRequest)
            return jsonResponse
    }

}