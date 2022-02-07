package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import java.io.File

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.load_page)

        val createProfileButton = findViewById<ImageButton>(R.id.createProfileButton)
        createProfileButton.setOnClickListener {
            val nameText: String = findViewById<EditText>(R.id.nameTextField).text.toString()
            val heightText: String = findViewById<EditText>(R.id.heightTextField).text.toString()
            val weightText: String = findViewById<EditText>(R.id.weightTextField).text.toString()
            val ageText: String = findViewById<EditText>(R.id.ageTextField).text.toString()
            val genderText: String = findViewById<EditText>(R.id.genderTextField).text.toString()
            val cityText: String = findViewById<EditText>(R.id.cityTextField).text.toString()

            val profile = Profile(nameText, heightText, weightText, ageText, genderText, cityText, "")
            Log.i ("test", profile.printForStoring())
            Log.i("test", applicationContext.cacheDir.absolutePath)
            File.createTempFile("filename", profile.printForStoring(), applicationContext.cacheDir)
        }
    }
}