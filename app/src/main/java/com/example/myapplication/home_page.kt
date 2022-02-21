package com.example.myapplication

import android.graphics.Bitmap
import android.media.Image
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Intent
import android.util.Log
import android.widget.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.button.MaterialButton
import org.json.JSONException
import org.json.JSONObject


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [home_page.newInstance] factory method to
 * create an instance of this fragment.
 */
class home_page : Fragment() {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var profileThumb = requireView().findViewById<ImageButton>(R.id.ProfilePicThumbnail)

        val mapsButton = view.findViewById<ImageButton>(R.id.HikesFinderButton)
        mapsButton.setOnClickListener {
            val gmmIntentUri = Uri.parse("geo:0,0?q=${profile?.city} hikes")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
        profileThumb.setImageBitmap(profile?.image)

        val profileButton = view.findViewById<Button>(R.id.MyProfileButton)

        profileButton.setOnClickListener {
            val fragmentTransaction = fragmentManager?.beginTransaction()
            profile?.let { it1 -> ProfilePage.newInstance(it1) }?.let { it2 ->
                fragmentTransaction?.replace(
                    R.id.fragmentContainer,
                    it2
                )
            }
            fragmentTransaction?.setReorderingAllowed(true)
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }

        val profileThumbnail = view.findViewById<ImageButton>(R.id.ProfilePicThumbnail)

        profileThumbnail.setOnClickListener {
            val fragmentTransaction = fragmentManager?.beginTransaction()
            profile?.let { it1 -> ProfilePage.newInstance(it1) }?.let { it2 ->
                fragmentTransaction?.replace(
                    R.id.fragmentContainer,
                    it2
                )
            }
            fragmentTransaction?.setReorderingAllowed(true)
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }

        val bmiButton = view.findViewById<Button>(R.id.BMICalcButton)
        bmiButton.setOnClickListener {
            val fragmentTransaction = fragmentManager?.beginTransaction()
            profile?.let { it1 -> BMI_calculator.newInstance(it1) }?.let { it2 ->
                fragmentTransaction?.replace(
                    R.id.fragmentContainer,
                    it2
                )
            }
            fragmentTransaction?.setReorderingAllowed(true)
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }

        val myFitRegButt = view.findViewById<Button>(R.id.MyFitnessRegimenButton)
        myFitRegButt.setOnClickListener {
            val fragmentTransaction = fragmentManager?.beginTransaction()

            profile?.let { it1 -> FitnessDataPage.newInstance(it1) }?.let { it2 ->
                fragmentTransaction?.replace(R.id.fragmentContainer, it2)
            }

            fragmentTransaction?.setReorderingAllowed(true)
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }

        getWeatherDetails(view)

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("FRAGMENT", "home")
        super.onSaveInstanceState(outState)
    }

    fun getWeatherDetails(view: View){
        // Instantiate the RequestQueue.
        //val queue = Volley.newRequestQueue(view.context)
        val city = profile?.city
        val id = "4dbd2ed7d890d4f83982194472e820f5"
        val url = " https://api.openweathermap.org/data/2.5/weather?q=${city?.replace("\\s".toRegex(), "")}&appid=4dbd2ed7d890d4f83982194472e820f5"
        val queue = Volley.newRequestQueue(view.context)

        //Request a string response from the provided URL.
        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                // Display the first 500 characters of the response string.
                Log.i("test", "Response is: ${response.substring(0, 500)}")
                var jsonresponse = JSONObject(response)
                var temp = jsonresponse.getJSONObject("main").getDouble("temp")
                temp -= 273.15
                temp *= (9 / 5)
                temp += 32

                view.findViewById<TextView>(R.id.TemperatureHighText).text = temp.toInt().toString() + " \u2109"

                when(jsonresponse.getJSONArray("weather").getJSONObject(0).getString("icon")) {
                    "01d" -> view.findViewById<ImageView>(R.id.WeatherImageView).setBackgroundResource(R.drawable.a01d)
                    "01n" -> view.findViewById<ImageView>(R.id.WeatherImageView).setBackgroundResource(R.drawable.a01n)
                    "02d" -> view.findViewById<ImageView>(R.id.WeatherImageView).setBackgroundResource(R.drawable.a02d)
                    "02n" -> view.findViewById<ImageView>(R.id.WeatherImageView).setBackgroundResource(R.drawable.a02n)
                    "03d" -> view.findViewById<ImageView>(R.id.WeatherImageView).setBackgroundResource(R.drawable.a03n)
                    "03n" -> view.findViewById<ImageView>(R.id.WeatherImageView).setBackgroundResource(R.drawable.a03n)
                    "04d" -> view.findViewById<ImageView>(R.id.WeatherImageView).setBackgroundResource(R.drawable.a04d)
                    "04n" -> view.findViewById<ImageView>(R.id.WeatherImageView).setBackgroundResource(R.drawable.a04n)
                    "09d" -> view.findViewById<ImageView>(R.id.WeatherImageView).setBackgroundResource(R.drawable.a09d)
                    "09n" -> view.findViewById<ImageView>(R.id.WeatherImageView).setBackgroundResource(R.drawable.a09n)
                    "10d" -> view.findViewById<ImageView>(R.id.WeatherImageView).setBackgroundResource(R.drawable.a10d)
                    "10n" -> view.findViewById<ImageView>(R.id.WeatherImageView).setBackgroundResource(R.drawable.a10n)
                    "11d" -> view.findViewById<ImageView>(R.id.WeatherImageView).setBackgroundResource(R.drawable.a11d)
                    "11n" -> view.findViewById<ImageView>(R.id.WeatherImageView).setBackgroundResource(R.drawable.a11n)
                    "13d" -> view.findViewById<ImageView>(R.id.WeatherImageView).setBackgroundResource(R.drawable.a13d)
                    "13n" -> view.findViewById<ImageView>(R.id.WeatherImageView).setBackgroundResource(R.drawable.a13n)
                    "50d" -> view.findViewById<ImageView>(R.id.WeatherImageView).setBackgroundResource(R.drawable.a50d)
                    "50n" -> view.findViewById<ImageView>(R.id.WeatherImageView).setBackgroundResource(R.drawable.a50n)
                }

            },
            { Log.i("test", "That didn't work!") })

        //Add the request to the RequestQueue.
        queue.add(stringRequest)
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
        fun newInstance(profile: Profile) =
            home_page().apply {
                this.profile = profile
                arguments = Bundle().apply {
                }
            }
    }
}
