package com.example.myapplication

import android.content.Context
import android.content.res.Configuration
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import kotlinx.coroutines.runBlocking

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
        super.onSaveInstanceState(outState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val model: ProfileViewModel by activityViewModels()

        profile = runBlocking { model.getProfile() }

        var profileThumb = requireView().findViewById<ImageButton>(R.id.ProfilePicThumbnail)
        profileThumb.setImageBitmap(profile?.image?.let { BitmapFactory.decodeByteArray(profile?.image, 0, it.size) })

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
        if(profile?.regimen != "")
            regimenSpinner.setSelection(profile?.regimen!!.toInt())

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
        if(profile?.active != "")
             activitySpinner.setSelection(profile?.active!!.toInt())

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
        if(profile?.weightGoal != "")
            poundsSpinner.setSelection(profile?.weightGoal!!.toInt())


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
            fragmentTransaction?.replace(R.id.fragmentContainer, HomePage.newInstance())
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
                poundsSpinner.selectedItemPosition.toString()
            val regimenText : String =
                regimenSpinner.selectedItemPosition.toString()
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
            profile?.weightGoal = poundsText
            profile?.regimen = regimenText

            profile?.height = heightText.toString()
            profile?.weight = weightText

            profile?.bmr = HealthCalculator().calculateBMR(profile?.weight.toString(), (48 + (profile?.height?.toInt()!!)),
                profile?.age.toString(), profile?.active.toString(), profile?.gender.toString())
            bmrTag.text = profile?.bmr
            calorieTag.text = HealthCalculator().calculateDailyCalories(profile?.bmr!!, profile?.weightGoal!!, profile?.gender!!.toUpperCase())

            profile?.bmi = HealthCalculator().calculateBMI(profile?.weight.toString(), (48 + profile?.height?.toInt()!!).toString())
            bmiTag.text = profile?.bmi

            runBlocking { model.updateProfile(profile!!) }
        }
        val profileThumbnail = view.findViewById<ImageButton>(R.id.ProfilePicThumbnail)

        profileThumbnail.setOnClickListener {
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragmentContainer, ProfilePage.newInstance())
            fragmentTransaction?.setReorderingAllowed(true)
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }

        if(isTablet(this.requireContext())) {
            val arrayAdapter : ArrayAdapter<String>
            val modules = arrayOf(
                "Homepage", "My Profile", "BMI Calculator", "Step Counter"
            )
            var moduleListView = view.findViewById<ListView>(R.id.moduleListFitness)
            arrayAdapter = ArrayAdapter(this.requireContext(), R.layout.modules_side_bar, modules)

            moduleListView.adapter = arrayAdapter

            moduleListView.setOnItemClickListener { parent, _, position, _ ->
                val selectedItem = parent.getItemAtPosition(position) as String
                if (selectedItem == "Homepage") {
                    val fragmentTransaction = fragmentManager?.beginTransaction()
                    fragmentTransaction?.replace(R.id.fragmentContainer, HomePage.newInstance())
                    fragmentTransaction?.setReorderingAllowed(true)
                    fragmentTransaction?.addToBackStack(null)
                    fragmentTransaction?.commit()
                } else if (selectedItem == "BMI Calculator") {
                    val fragmentTransaction = fragmentManager?.beginTransaction()
                    fragmentTransaction?.replace(R.id.fragmentContainer, BMI_calculator.newInstance());
                    fragmentTransaction?.setReorderingAllowed(true)
                    fragmentTransaction?.addToBackStack(null)
                    fragmentTransaction?.commit()
                } else if (selectedItem == "My Profile") {
                    val fragmentTransaction = fragmentManager?.beginTransaction()
                    fragmentTransaction?.replace(R.id.fragmentContainer, ProfilePage.newInstance())
                    fragmentTransaction?.setReorderingAllowed(true)
                    fragmentTransaction?.addToBackStack(null)
                    fragmentTransaction?.commit()
                }else if (selectedItem == "Step Counter") {
                    val fragmentTransaction = fragmentManager?.beginTransaction()
                    fragmentTransaction?.replace(R.id.fragmentContainer,
                        StepCounterPage.newInstance()
                    )
                    fragmentTransaction?.setReorderingAllowed(true)
                    fragmentTransaction?.addToBackStack(null)
                    fragmentTransaction?.commit()
                }
            }
        }


        super.onViewCreated(view, savedInstanceState)
    }

    companion object {

        fun newInstance() =
            FitnessDataPage().apply {
                arguments = Bundle().apply {

                }
            }
    }
}