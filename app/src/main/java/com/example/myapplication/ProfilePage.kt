package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myapplication.Profile.Companion.write
import com.hbb20.CountryCodePicker

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
        outState.putParcelable("PROFILE", profile)
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
        if (savedInstanceState != null) {
            profile = savedInstanceState.getParcelable("PROFILE")!!
        }

        val genderSpinner = view.findViewById(R.id.genderSpinner) as Spinner
        ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.gender_array,
            R.layout.spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
            genderSpinner.adapter = adapter
        }
        genderSpinner.prompt = "Select gender:"
        genderSpinner.isEnabled = false

        val ageSpinner = view.findViewById(R.id.ageSpinner) as Spinner
        var ages : ArrayList<String> = arrayListOf()
        ages.add("Age:")
        for(i in 18..100){
            ages.add("$i years old")
        }
        var ageAdapter = ArrayAdapter(this.requireContext(), R.layout.spinner_item, ages)
        ageAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        ageSpinner.adapter = ageAdapter
        ageSpinner.prompt = "Select age:"
        ageSpinner.isEnabled = false

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
            else{
                heightTag.setSelection(0)
            }
        }
        heightTag.isEnabled = false



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

        val nameField = view.findViewById<EditText>(R.id.nameTextField)
        nameField.text = Editable.Factory.getInstance().newEditable(profile?.name)
        nameField.setTextIsSelectable(false)

        heightTag.setSelection(profile?.height!!.toInt())

        val weightField = view.findViewById<EditText>(R.id.weightTextField)
        weightField.text = Editable.Factory.getInstance().newEditable(profile?.weight)
        weightField.setTextIsSelectable(false)

        ageSpinner.setSelection(profile?.age!!.toInt())

        if (profile?.gender!!.toUpperCase() == "FEMALE")
        {
            genderSpinner.setSelection(1)
        }
        else if (profile?.gender!!.toUpperCase() == "MALE")
        {
            genderSpinner.setSelection(2)
        }
        else
        {
            genderSpinner.setSelection(3)
        }


        val cityField = view.findViewById<EditText>(R.id.cityTextField)
        cityField.text = Editable.Factory.getInstance().newEditable(profile?.city)
        cityField.setTextIsSelectable(false)

        val countrySpinner : CountryCodePicker = view.findViewById(R.id.ccp)
        countrySpinner.setCountryForNameCode(profile?.country)

        val imageGuy = view.findViewById<ImageView>(R.id.ProfilePicImageView)
        imageGuy.setImageBitmap(profile?.image)

        val updateProfileButton = view.findViewById<Button>(R.id.updateProfileButton)
        updateProfileButton.isEnabled = false

        val editProfileButton = view.findViewById<Button>(R.id.editProfileButton)
        editProfileButton.setOnClickListener{
            weightField.setTextIsSelectable(true)
            heightTag.isEnabled = true
            genderSpinner.isEnabled = true
            ageSpinner.isEnabled = true
            cityField.setTextIsSelectable(true)
            nameField.setTextIsSelectable(true)
            editProfileButton.isEnabled = false

            updateProfileButton.isEnabled = true
        }

        updateProfileButton.setOnClickListener{
            val heightText: Int =
                heightTag.selectedItemPosition
            val weightText: String =
                view.findViewById<EditText>(R.id.weightTextField).text.toString()
            val ageText : Int =
                ageSpinner.selectedItemPosition
            val cityText : String =
                view.findViewById<EditText>(R.id.cityTextField).text.toString()
            val nameText : String =
                view.findViewById<EditText>(R.id.nameTextField).text.toString()
            val genderText : String =
                genderSpinner.selectedItem as String
            weightField.setTextIsSelectable(false)
            heightTag.isEnabled = false
            genderSpinner.isEnabled = false
            ageSpinner.isEnabled = false
            cityField.setTextIsSelectable(false)
            nameField.setTextIsSelectable(false)
            editProfileButton.isEnabled = true
            updateProfileButton.isEnabled = false

            profile?.height = heightText.toString()
            profile?.weight = weightText
            profile?.age = ageText.toString()
            profile?.city = cityText
            profile?.name = nameText
            profile?.gender = genderText.toString()

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