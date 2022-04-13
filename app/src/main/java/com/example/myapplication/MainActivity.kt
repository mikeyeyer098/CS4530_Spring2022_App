package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        profileViewModel.repo.createDb(this)
        var weatherViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)

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
                replaceFragment(BMI_calculator.newInstance())
            }
            if(savedInstanceState.getString("FRAGMENT") == "home")
            {
                replaceFragment(HomePage.newInstance())
            }
            if(savedInstanceState.getString("FRAGMENT") == "prof")
            {
                replaceFragment(ProfilePage.newInstance())
            }
            if(savedInstanceState.getString("FRAGMENT") == "fitdat")
            {
                replaceFragment(FitnessDataPage.newInstance())
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