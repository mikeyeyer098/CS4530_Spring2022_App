package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)
            replaceFragment(load_page())
        }
        else {
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)
            if(savedInstanceState.getString("FRAGMENT") == "bmi")
            {
                replaceFragment(BMI_calculator.newInstance(Profile.create(savedInstanceState.getParcelable("PROFILE")!!)))
            }
            if(savedInstanceState.getString("FRAGMENT") == "home")
            {
                replaceFragment(home_page.newInstance(Profile.create(savedInstanceState.getParcelable("PROFILE")!!)))
            }
            if(savedInstanceState.getString("FRAGMENT") == "prof")
            {
                replaceFragment(ProfilePage.newInstance(Profile.create(savedInstanceState.getParcelable("PROFILE")!!)))
            }
            if(savedInstanceState.getString("FRAGMENT") == "fitdat")
            {
                replaceFragment(FitnessDataPage.newInstance(Profile.create(savedInstanceState.getParcelable("PROFILE")!!)))
            }
            if(savedInstanceState.getString("FRAGMENT") == "load") {
                replaceFragment(load_page())
            }
        }
    }

    private fun replaceFragment(fragment : Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }
}