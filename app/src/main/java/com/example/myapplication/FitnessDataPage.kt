package com.example.myapplication

import android.content.Context
import android.content.res.Configuration
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

    fun isTablet(context: Context): Boolean {
        return ((context.getResources().getConfiguration().screenLayout
                and Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("FRAGMENT", "fitdat")
        outState.putParcelable("PROFILE", profile)
        super.onSaveInstanceState(outState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            profile = savedInstanceState.getParcelable("PROFILE")!!
        }

        var profileThumb = requireView().findViewById<ImageButton>(R.id.ProfilePicThumbnail)
        profileThumb.setImageBitmap(profile?.image)

        var heightTag = requireView().findViewById<Spinner>(R.id.heightSpinner)
        var heights : ArrayList<String> = arrayListOf()
        heights.add("Height:")
        for(i in 4..7) {
            for (j in 0..11) {
                heights.add("$i \' $j \"")
            }
        }
        var heightAdapter = ArrayAdapter(this.requireContext(), R.layout.spinner_item, heights)
        heightAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        heightTag.adapter = heightAdapter
        profile?.height?.toInt().let {
            if (it != null) {
                heightTag.setSelection(it)
            }
        }
        heightTag.isEnabled = false
        heightTag.alpha = 0.5f

        var weightTag = requireView().findViewById<TextView>(R.id.weightTextField)

        try{
            weightTag.text = profile?.weight!!.toString()
        } catch (e: Exception) {
            weightTag.hint = "Weight: ${profile?.weight}"
        }
        weightTag.setTextIsSelectable(false)
        weightTag.alpha = 0.5f


        val regimenSpinner = view.findViewById(R.id.regimenSpinner) as Spinner
        ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.regimen_arr,
            R.layout.spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
            regimenSpinner.adapter = adapter
        }
        regimenSpinner.prompt = "Select weight goal:"
        regimenSpinner.isEnabled = false
        regimenSpinner.alpha = 0.5f


        val activitySpinner = view.findViewById(R.id.activityLevelSpinner) as Spinner
        ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.activity_level_array,
            R.layout.spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
            activitySpinner.adapter = adapter
        }
        activitySpinner.prompt = "Select activity level:"
        activitySpinner.isEnabled = false
        activitySpinner.alpha = 0.5f

        val poundsSpinner : Spinner = view.findViewById(R.id.poundsGoalSpinner)
        ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.pounds_array,
            R.layout.spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
            poundsSpinner.adapter = adapter
        }
        poundsSpinner.isEnabled = false
        poundsSpinner.alpha = 0.5f

        profile?.bmr = HealthCalculator().calculateBMR(profile?.weight.toString(), (48 + (profile?.height?.toInt()!!)),
            profile?.age.toString(), profile?.active.toString(), profile?.gender.toString())


        var calorieTag = requireView().findViewById<TextView>(R.id.DailyCaloriesText)
        calorieTag.text = HealthCalculator().calculateDailyCalories(profile?.bmr!!, profile?.weightGoal.toString(), profile?.gender.toString())
        calorieTag.setTextIsSelectable(false)

        var bmrTag = requireView().findViewById<TextView>(R.id.BMRText)
        bmrTag.text = profile?.bmr

        var bmiTag = view.findViewById<TextView>(R.id.BMIText)
        bmiTag.text = HealthCalculator().calculateBMI(profile?.weight.toString(), (48 + (profile?.height?.toInt()!!)).toString())
        bmiTag.setTextIsSelectable(false)

        val backButton = view.findViewById<ImageButton>(R.id.backArrow)

        backButton.setOnClickListener {
            val fragmentTransaction = fragmentManager?.beginTransaction()

            profile?.let { it1 -> home_page.newInstance(it1) }?.let { it2 ->
                fragmentTransaction?.replace(R.id.fragmentContainer, it2)
            }

            fragmentTransaction?.setReorderingAllowed(true)
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
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

            weightTag.alpha = 1f
            heightTag.alpha = 1f
            poundsSpinner.alpha = 1f
            regimenSpinner.alpha = 1f
            activitySpinner.alpha = 1f

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
                activitySpinner.selectedItemPosition.toString()
            weightTag.setTextIsSelectable(false)
            heightTag.isEnabled = false
            poundsSpinner.isEnabled = false
            regimenSpinner.isEnabled = false
            activitySpinner.isEnabled = false
            updateProfileButton.isEnabled = false
            editProfileButton.isEnabled = true

            weightTag.alpha = 0.5f
            heightTag.alpha = 0.5f
            poundsSpinner.alpha = 0.5f
            regimenSpinner.alpha = 0.5f
            activitySpinner.alpha = 0.5f


            profile?.active = activityText

            if (regimenText == "Maintain Weight") {
                profile?.weightGoal = "0"
            }
            else if (regimenText == "Gain Weight") {
                profile?.weightGoal = poundsText.split(" ")[0]
            }
            else {
                profile?.weightGoal = (poundsText.split(" ")[0].toInt() * -1).toString()
            }

            profile?.height = heightText.toString()
            profile?.weight = weightText

            profile?.bmr = HealthCalculator().calculateBMR(profile?.weight.toString(), (48 + (profile?.height?.toInt()!!)),
                profile?.age.toString(), profile?.active.toString(), profile?.gender.toString())
            bmrTag.text = profile?.bmr
            calorieTag.text = HealthCalculator().calculateDailyCalories(profile?.bmr!!, profile?.weightGoal!!, profile?.gender!!.toUpperCase())

            profile?.bmi = HealthCalculator().calculateBMI(profile?.weight.toString(), (48 + profile?.height?.toInt()!!).toString())
            bmiTag.text = profile?.bmi
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

        if(isTablet(this.requireContext())) {
            val arrayAdapter : ArrayAdapter<String>
            val modules = arrayOf(
                "Homepage", "My Profile", "BMI Calculator"
            )
            var moduleListView = view.findViewById<ListView>(R.id.moduleListFitness)
            arrayAdapter = ArrayAdapter(this.requireContext(), R.layout.modules_side_bar, modules)

            moduleListView.adapter = arrayAdapter

            moduleListView.setOnItemClickListener { parent, _, position, _ ->
                val selectedItem = parent.getItemAtPosition(position) as String
                if (selectedItem == "Homepage") {
                    val fragmentTransaction = fragmentManager?.beginTransaction()

                    profile?.let { it1 -> home_page.newInstance(it1) }?.let { it2 ->
                        fragmentTransaction?.replace(R.id.fragmentContainer, it2)
                    }

                    fragmentTransaction?.setReorderingAllowed(true)
                    fragmentTransaction?.addToBackStack(null)
                    fragmentTransaction?.commit()
                } else if (selectedItem == "BMI Calculator") {
                    val fragmentTransaction = fragmentManager?.beginTransaction()

                    profile?.let { it1 -> BMI_calculator.newInstance(it1) }?.let { it2 ->
                        fragmentTransaction?.replace(R.id.fragmentContainer, it2)
                    }

                    fragmentTransaction?.setReorderingAllowed(true)
                    fragmentTransaction?.addToBackStack(null)
                    fragmentTransaction?.commit()
                } else if (selectedItem == "My Profile") {
                    val fragmentTransaction = fragmentManager?.beginTransaction()

                    profile?.let { it1 -> ProfilePage.newInstance(it1) }?.let { it2 ->
                        fragmentTransaction?.replace(R.id.fragmentContainer, it2)
                    }

                    fragmentTransaction?.setReorderingAllowed(true)
                    fragmentTransaction?.addToBackStack(null)
                    fragmentTransaction?.commit()
                }
            }
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