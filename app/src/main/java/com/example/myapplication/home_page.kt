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
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.button.MaterialButton


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

    fun getWeatherDetails(view: View) {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(view.context)
        val city = profile?.city
        val id = "4dbd2ed7d890d4f83982194472e820f5"
        val url = "api.openweathermap.org/data/2.5/weather?q=Batimore&appid=4dbd2ed7d890d4f83982194472e820f5"

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(Request.Method.GET, url,
            Response.Listener<String> { response ->
                // Display the first 500 characters of the response string.
                Log.i("test", "Response is: ${response.substring(0, 500)}")
            },
            Response.ErrorListener { Log.i("test", "bad api request")})

        // Add the request to the RequestQueue.
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
