package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FitnessDataPage.newInstance] factory method to
 * create an instance of this fragment.
 */
class FitnessDataPage : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var profile: Profile? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fitness_data_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var profileThumb = requireView().findViewById<ImageButton>(R.id.ProfilePicThumbnail)

        val mapsButton = view.findViewById(R.id.HikesFinderButton) as ImageButton
        mapsButton.setOnClickListener {
            val gmmIntentUri = Uri.parse("geo:0,0?q=hikes")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
        profileThumb.setImageBitmap(profile?.image)

        var heightTag = requireView().findViewById<TextView>(R.id.heightTextField)
        heightTag.hint = "Height: ${profile?.height}"

        var weightTag = requireView().findViewById<TextView>(R.id.weightTextField)
        weightTag.hint = "Weight: ${profile?.weight}"

//        val regimens = resources.getStringArray(R.array.regimen_arr)
//        val regimenSpinner = view.findViewById(R.id.regimenSpinner) as Spinner
//        if (regimenSpinner != null) {
//            val adapter = ArrayAdapter.createFromResource(
//                getActivity(),
//                R.array.regimen_arr, resources.getStringArray(R.array.regimen_arr))
//            regimenSpinner.adapter = adapter
//        super.onViewCreated(view, savedInstanceState)
//    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FitnessDataPage.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FitnessDataPage().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}