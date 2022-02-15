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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fitness_data_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var profileThumb = requireView().findViewById<ImageButton>(R.id.ProfilePicThumbnail)
        profileThumb.setImageBitmap(profile?.image)

        var heightTag = requireView().findViewById<TextView>(R.id.heightTextField)
        heightTag.hint = "Height: ${profile?.height}"

        var weightTag = requireView().findViewById<TextView>(R.id.weightTextField)
        weightTag.hint = "Weight: ${profile?.weight}"

        val regimenSpinner = view.findViewById(R.id.regimenSpinner) as Spinner
        ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.regimen_arr,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            regimenSpinner.adapter = adapter
        }
        regimenSpinner.prompt = "Select weight goal:"

        val activitySpinner = view.findViewById(R.id.activityLevelSpinner) as Spinner
        ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.activity_level_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            activitySpinner.adapter = adapter
        }
        activitySpinner.prompt = "Select activity level:"

        val poundsSpinner : Spinner = view.findViewById(R.id.poundsGoalSpinner)
        ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.pounds_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            poundsSpinner.adapter = adapter
        }

        var calorieTag = requireView().findViewById<TextView>(R.id.DailyCaloriesText)
        calorieTag.text = HealthCalculator().calculateDailyCalories(
            HealthCalculator().calculateBMR(profile?.weight.toString(), profile?.height.toString(),
                profile?.age.toString(), profile?.active.toString(), profile?.sex.toString()),
            profile?.weightGoal.toString(), profile?.sex.toString())

        var bmrTag = requireView().findViewById<TextView>(R.id.BMRText)
        bmrTag.text = HealthCalculator().calculateBMR(profile?.weight.toString(), profile?.height.toString(),
                profile?.age.toString(), profile?.active.toString(), profile?.sex.toString())

        var bmiTag = view.findViewById<TextView>(R.id.BMRText)
        bmiTag.text = HealthCalculator().calculateBMI(profile?.weight.toString(), profile?.height.toString())

        super.onViewCreated(view, savedInstanceState)
    }

    companion object {

        fun newInstance(profile: Profile) =
            FitnessDataPage().apply {
                this.profile = profile
                arguments = Bundle().apply {

                }
            }
    }
}