package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.load_page)

        val createProfileButton = findViewById<ImageButton>(R.id.createProfile)
        createProfileButton.setOnClickListener {
            Log.d("test", "create profile button pressed")
        }
    }
}