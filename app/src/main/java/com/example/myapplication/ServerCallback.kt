package com.example.myapplication

import org.json.JSONObject




interface ServerCallback {
    fun onSuccess(result: JSONObject?)
}