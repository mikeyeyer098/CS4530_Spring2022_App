package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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

        var heightTag = requireView().findViewById<Spinner>(R.id.heightSpinner)
        var heights : ArrayList<String> = arrayListOf()
        for(i in 4..7) {
            for (j in 0..11) {
                heights.add("$i \' $j \"")
            }
        }
        var heightAdapter = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, heights)
        heightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        heightTag.adapter = heightAdapter

        profile?.height?.toInt().let {
            if (it != null) {
                heightTag.setSelection(it)
            }
        }

        var weightTag = requireView().findViewById<TextView>(R.id.weightTextField)
        weightTag.hint = "Weight: ${profile?.weight}"

        weightTag.setTextIsSelectable(false)

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
        regimenSpinner.isEnabled = false

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
        activitySpinner.isEnabled = false

        val poundsSpinner : Spinner = view.findViewById(R.id.poundsGoalSpinner)
        ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.pounds_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            poundsSpinner.adapter = adapter
        }
        poundsSpinner.isEnabled = false


        var calorieTag = requireView().findViewById<TextView>(R.id.DailyCaloriesText)
        calorieTag.text = HealthCalculator().calculateDailyCalories(
            HealthCalculator().calculateBMR(profile?.weight.toString(), (48 + (profile?.height?.toInt()!!)),
                profile?.age.toString(), profile?.active.toString(), profile?.sex.toString()),
            profile?.weightGoal.toString(), profile?.sex.toString())

        var bmrTag = requireView().findViewById<TextView>(R.id.BMRText)
        bmrTag.text = HealthCalculator().calculateBMR(profile?.weight.toString(), (48 + (profile?.height?.toInt()!!)),
                profile?.age.toString(), profile?.active.toString(), profile?.sex.toString())

        var bmiTag = view.findViewById<TextView>(R.id.BMRText)
        bmiTag.text = HealthCalculator().calculateBMI(profile?.weight.toString(), (48 + (profile?.height?.toInt()!!)).toString())

        val backButton = view.findViewById<ImageButton>(R.id.backArrow)

        backButton.setOnClickListener {
            Log.i("test", "back button pressed")
            fragmentManager?.popBackStack()
        }

        val updateProfileButton = view.findViewById<Button>(R.id.updateProfileButton)
        updateProfileButton.isEnabled = false

        val editProfileButton = view.findViewById<Button>(R.id.editProfileButton)
        editProfileButton.setOnClickListener{
            weightTag.setTextIsSelectable(true)
            heightTag.isEnabled = true
            poundsSpinner.isEnabled = true
            regimenSpinner.isEnabled = true
            activitySpinner.isEnabled = true
            editProfileButton.isEnabled = false

            updateProfileButton.isEnabled = true
        }

        updateProfileButton.setOnClickListener{
            val heightText: Int =
                heightTag.selectedItemPosition
            val weightText: String =
                view.findViewById<EditText>(R.id.weightTextField).text.toString()
            val poundsText : String =
                poundsSpinner.selectedItem.toString()
            val regimenText : String =
                regimenSpinner.selectedItem.toString()
            val activityText : String =
                activitySpinner.selectedItem.toString()
            weightTag.setTextIsSelectable(false)
            heightTag.isEnabled = false
            poundsSpinner.isEnabled = false
            regimenSpinner.isEnabled = false
            activitySpinner.isEnabled = false
            updateProfileButton.isEnabled = false
            editProfileButton.isEnabled = true

            profile?.active = activityText
            profile?.weightGoal = poundsText
            profile?.height = heightText.toString()
            profile?.weight = weightText
          //  profile?:regimen = regimenText
        }



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