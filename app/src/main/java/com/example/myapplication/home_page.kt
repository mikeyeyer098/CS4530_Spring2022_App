package com.example.myapplication

import android.graphics.Bitmap
import android.media.Image
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.content.Intent
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [home_page.newInstance] factory method to
 * create an instance of this fragment.
 */
class home_page : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var profile: Profile? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
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

        val mapsButton = view.findViewById(R.id.HikesFinderButton) as ImageButton
        mapsButton.setOnClickListener {
            val gmmIntentUri = Uri.parse("geo:0,0?q=${profile?.city} hikes")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
        profileThumb.setImageBitmap(profile?.image)

//        var calorieTag = requireView().findViewById<TextView>(R.id.DailyCaloriesText)
//        calorieTag.text = HealthCalculator().calculateDailyCalories(
//            HealthCalculator().calculateBMR(profile?.weight.toString(), profile?.height.toString(),
//                profile?.age.toString(), profile?.active.toString(), profile?.sex.toString()),
//                profile?.weightGoal.toString(), profile?.sex.toString())

        getWeatherDetails(view)

        super.onViewCreated(view, savedInstanceState)
    }

    fun getWeatherDetails(view: View) {
        Log.i("test", "temp")
        var tempUrl = "api.openweathermap.org/data/2.5/weather?q=${profile?.city}&appid=44dbd2ed7d890d4f83982194472e820f5"
        Log.i("test", "temp2")
        var request: StringRequest = StringRequest(Request.Method.POST, tempUrl, Response.Listener<String>() {
            fun onResponse(response: String) {
                Log.i("test", response)
            }
        }, Response.ErrorListener() {
            fun onErrorResponse(error: VolleyError) {
                Log.i("test", "bad api response")
        }
        })
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

//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }
}