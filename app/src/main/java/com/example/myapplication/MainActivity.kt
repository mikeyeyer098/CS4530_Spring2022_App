package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File

class MainActivity : AppCompatActivity() {

    private val PERMISSIONCODE = 100
    private val CAMERACODE = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.load_page)

        val takePictureButton = findViewById<ImageButton>(R.id.TakePictureButton)
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

        takePictureButton.setOnClickListener{
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, CAMERACODE)
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), PERMISSIONCODE)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode ==  PERMISSIONCODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, CAMERACODE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val profilePicImageView = findViewById<ImageView>(R.id.ProfilePicImageView)

        if (resultCode == Activity.RESULT_OK && requestCode == CAMERACODE) {
            val picDisplay: Bitmap = data!!.extras!!.get("data") as Bitmap
            profilePicImageView.setImageBitmap(picDisplay)
        }
    }
}