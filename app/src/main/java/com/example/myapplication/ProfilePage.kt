package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfilePage.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfilePage : Fragment() {

    private var profile: Profile? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("FRAGMENT", "prof")
        super.onSaveInstanceState(outState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val backButton = view.findViewById<ImageButton>(R.id.backArrow)

        backButton.setOnClickListener {
            Log.i("test", "back button pressed")
            fragmentManager?.popBackStack()
        }

        val genderSpinner = view.findViewById(R.id.genderSpinner) as Spinner
        ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.gender_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            genderSpinner.adapter = adapter
        }
        genderSpinner.prompt = "Select gender:"
        profile?.gender?.toInt().let {
            if (it != null) {
                genderSpinner.setSelection(it)
            }
            else{
                genderSpinner.setSelection(0)
            }
        }

        val ageSpinner = view.findViewById(R.id.ageSpinner) as Spinner
        var ages : ArrayList<String> = arrayListOf()
        for(i in 18..100){
            ages.add("$i years old")
        }
        var ageAdapter = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, ages)
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        ageSpinner.adapter = ageAdapter
        ageSpinner.prompt = "Select age:"
        profile?.age?.toInt().let {
            if (it != null) {
                ageSpinner.setSelection(it)
            }
            else{
                ageSpinner.setSelection(0)
            }
        }
        val heightSpinner : Spinner = view.findViewById(R.id.heightSpinner)
        var heights : ArrayList<String> = arrayListOf()
        for(i in 4..7) {
            for (j in 0..11) {
                heights.add("$i \' $j \"")
            }
        }
        var heightAdapter = ArrayAdapter(this.requireContext(), android.R.layout.simple_spinner_item, heights)
        heightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        heightSpinner.adapter = heightAdapter
        heightSpinner.prompt = "Select height:"

        profile?.height?.toInt().let {
            if (it != null) {
                heightSpinner.setSelection(it)
            }
            else{
                heightSpinner.setSelection(0)
            }
        }

        val weightTag = view.findViewById<EditText>(R.id.weightTextField)
        weightTag.setText(profile?.weight)

        val cityTag = view.findViewById<EditText>(R.id.cityTextField)
        cityTag.setText(profile?.city)

        val nameTag = view.findViewById<EditText>(R.id.nameTextField)
        nameTag.setText(profile?.name)

        val updateProfileButton = view.findViewById<Button>(R.id.updateProfileButton)
        updateProfileButton.isEnabled = false

        val editProfileButton = view.findViewById<Button>(R.id.editProfileButton)
        editProfileButton.setOnClickListener{
            weightTag.setTextIsSelectable(true)
            heightSpinner.isEnabled = true
            nameTag.setTextIsSelectable(true)
            cityTag.setTextIsSelectable(true)
            genderSpinner.isEnabled = true
            ageSpinner.isEnabled = true
            editProfileButton.isEnabled = false

            updateProfileButton.isEnabled = true
        }

        updateProfileButton.setOnClickListener{
            val heightText: Int =
                heightSpinner.selectedItemPosition
            val weightText: String =
                view.findViewById<EditText>(R.id.weightTextField).text.toString()
            val nameText : String =
                nameTag.text.toString()
            val ageText : Int =
                ageSpinner.selectedItemPosition
            val genderText : Int =
                genderSpinner.selectedItemPosition
            val cityText : String =
                cityTag.text.toString()
            weightTag.setTextIsSelectable(false)
            heightSpinner.isEnabled = false
            nameTag.setTextIsSelectable(false)
            cityTag.setTextIsSelectable(false)
            genderSpinner.isEnabled = false
            ageSpinner.isEnabled = false
            updateProfileButton.isEnabled = false
            editProfileButton.isEnabled = true

            profile?.city = cityText
            profile?.weight = weightText
            profile?.height = heightText.toString()
            profile?.age = ageText.toString()
            profile?.city = cityText
            profile?.gender = genderText.toString()
            profile?.name = nameText

            //  profile?:regimen = regimenText
        }
        if (isTablet(this.requireContext())) {
            val arrayAdapter: ArrayAdapter<String>
            val modules = arrayOf(
                "Homepage", "My Fitness Regime", "BMI Calculator"
            )
            var moduleListView = view.findViewById<ListView>(R.id.moduleListProfile)
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
                } else if (selectedItem == "My Fitness Regime") {
                    val fragmentTransaction = fragmentManager?.beginTransaction()

                    profile?.let { it1 -> FitnessDataPage.newInstance(it1) }?.let { it2 ->
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
                }
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }

    fun isTablet(context: Context): Boolean {
        return ((context.getResources().getConfiguration().screenLayout
                and Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE)
    }

    companion object {
        @JvmStatic
        fun newInstance(profile: Profile) =
            ProfilePage().apply {
                this.profile = profile
                arguments = Bundle().apply {
                }
            }
    }
}