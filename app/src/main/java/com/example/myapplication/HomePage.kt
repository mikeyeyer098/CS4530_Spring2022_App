package com.example.myapplication

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Intent
import android.content.res.Configuration
import android.graphics.BitmapFactory
import android.text.Editable
import android.util.Log
import android.widget.*
import androidx.fragment.app.activityViewModels
import com.amplifyframework.core.Amplify
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import java.io.File


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [HomePage.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomePage : Fragment() {
    private var profile: Profile? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_home_page, container, false)
        return view
    }

    private fun uploadFileToS3(profile: Profile?) {
        val profileFile = File(context?.filesDir, profile?.name + " Profile Backup Data")
        profileFile.writeText(profile?.name + "\n" + profile?.height + "\n" + profile?.weight + "\n"
                                + profile?.age + "\n" + profile?.gender + "\n" + profile?.city + "\n"
                                + profile?.country + "\n" + profile?.imagePath + "\n" + profile?.active + "\n"
                                + profile?.bmr + "\n" + profile?.bmi + "\n" + profile?.regimen + "\n"
                                + profile?.weightGoal + "\n" + profile?.image + "\n")

        Amplify.Storage.uploadFile(profile?.name + " Profile Backup Data", profileFile,
            { Log.i("MyAmplifyApp", "Successfully uploaded: ${it.key}") },
            { Log.e("MyAmplifyApp", "Upload failed", it) }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val model: ProfileViewModel by activityViewModels()

        profile = runBlocking { model.getProfile() }

        uploadFileToS3(profile);

        var profileThumb = requireView().findViewById<ImageButton>(R.id.ProfilePicThumbnail)
        profileThumb.setImageBitmap(profile?.image?.let { BitmapFactory.decodeByteArray(profile?.image, 0, it.size) })

        val mapsButton = view.findViewById<ImageButton>(R.id.HikesFinderButton)
        mapsButton.setOnClickListener {
            val gmmIntentUri = Uri.parse("geo:0,0?q=${profile?.city} hikes")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }

        val profileButton = view.findViewById<Button>(R.id.MyProfileButton)

        profileButton.setOnClickListener {
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragmentContainer, ProfilePage.newInstance())
            fragmentTransaction?.setReorderingAllowed(true)
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }

        val profileThumbnail = view.findViewById<ImageButton>(R.id.ProfilePicThumbnail)

        profileThumbnail.setOnClickListener {
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragmentContainer, ProfilePage.newInstance())
            fragmentTransaction?.setReorderingAllowed(true)
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }

        val bmiButton = view.findViewById<Button>(R.id.BMICalcButton)
        bmiButton.setOnClickListener {
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragmentContainer, BMI_calculator.newInstance())
            fragmentTransaction?.setReorderingAllowed(true)
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }

        val myFitRegButt = view.findViewById<Button>(R.id.MyFitnessRegimenButton)
        myFitRegButt.setOnClickListener {
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragmentContainer, FitnessDataPage.newInstance())
            fragmentTransaction?.setReorderingAllowed(true)
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }
        val stepCountButt = view.findViewById<Button>(R.id.StepCounterButton)
        stepCountButt.setOnClickListener {
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragmentContainer, StepCounterPage.newInstance())
            fragmentTransaction?.setReorderingAllowed(true)
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }

        getWeatherDetails(view)

        if(isTablet(this.requireContext())) {

        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("FRAGMENT", "home")
        super.onSaveInstanceState(outState)
    }

    fun getWeatherDetails(view: View) {
        val weatherModel: WeatherViewModel by activityViewModels()
        val cityText = view.findViewById<TextView>(R.id.cityTextField)
        cityText.text = Editable.Factory.getInstance().newEditable(profile?.city)
        cityText.setTextIsSelectable(false)

        var result: JSONObject? = null

        context?.let {
            profile?.city?.let { it1 ->
                weatherModel.getWeatherData(it1, it, object : ServerCallback {
                    override fun onSuccess(receiveMessage: JSONObject?) {
                        result = receiveMessage
                        var temp = result!!.getJSONObject("main").getDouble("temp")
                        temp -= 273.15
                        temp *= (9 / 5)
                        temp += 32

                        view.findViewById<TextView>(R.id.TemperatureHighText).text =
                            temp.toInt().toString() + " \u2109"

                        var icon = result!!.getJSONArray("weather").getJSONObject(0).getString("icon")
                        when (icon) {
                            "01d" -> view.findViewById<ImageView>(R.id.WeatherImageView)
                                .setBackgroundResource(R.drawable.a01d)
                            "01n" -> view.findViewById<ImageView>(R.id.WeatherImageView)
                                .setBackgroundResource(R.drawable.a01n)
                            "02d" -> view.findViewById<ImageView>(R.id.WeatherImageView)
                                .setBackgroundResource(R.drawable.a02d)
                            "02n" -> view.findViewById<ImageView>(R.id.WeatherImageView)
                                .setBackgroundResource(R.drawable.a02n)
                            "03d" -> view.findViewById<ImageView>(R.id.WeatherImageView)
                                .setBackgroundResource(R.drawable.a03n)
                            "03n" -> view.findViewById<ImageView>(R.id.WeatherImageView)
                                .setBackgroundResource(R.drawable.a03n)
                            "04d" -> view.findViewById<ImageView>(R.id.WeatherImageView)
                                .setBackgroundResource(R.drawable.a04d)
                            "04n" -> view.findViewById<ImageView>(R.id.WeatherImageView)
                                .setBackgroundResource(R.drawable.a04n)
                            "09d" -> view.findViewById<ImageView>(R.id.WeatherImageView)
                                .setBackgroundResource(R.drawable.a09d)
                            "09n" -> view.findViewById<ImageView>(R.id.WeatherImageView)
                                .setBackgroundResource(R.drawable.a09n)
                            "10d" -> view.findViewById<ImageView>(R.id.WeatherImageView)
                                .setBackgroundResource(R.drawable.a10d)
                            "10n" -> view.findViewById<ImageView>(R.id.WeatherImageView)
                                .setBackgroundResource(R.drawable.a10n)
                            "11d" -> view.findViewById<ImageView>(R.id.WeatherImageView)
                                .setBackgroundResource(R.drawable.a11d)
                            "11n" -> view.findViewById<ImageView>(R.id.WeatherImageView)
                                .setBackgroundResource(R.drawable.a11n)
                            "13d" -> view.findViewById<ImageView>(R.id.WeatherImageView)
                                .setBackgroundResource(R.drawable.a13d)
                            "13n" -> view.findViewById<ImageView>(R.id.WeatherImageView)
                                .setBackgroundResource(R.drawable.a13n)
                            "50d" -> view.findViewById<ImageView>(R.id.WeatherImageView)
                                .setBackgroundResource(R.drawable.a50d)
                            "50n" -> view.findViewById<ImageView>(R.id.WeatherImageView)
                                .setBackgroundResource(R.drawable.a50n)
                        }
                    }
                })
            }
        }
    }

    fun isTablet(context: Context): Boolean {
        return ((context.getResources().getConfiguration().screenLayout
                and Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment home_page.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            HomePage().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
