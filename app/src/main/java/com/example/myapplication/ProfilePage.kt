package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Bitmap
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
import androidx.fragment.app.activityViewModels
import com.hbb20.CountryCodePicker


/**
 * A simple [Fragment] subclass.
 * Use the [ProfilePage.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfilePage : Fragment() {

    private val PERMISSIONCODE = 100
    private val CAMERACODE = 200
    var profilePic: Bitmap? = null
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
        val model: ProfileViewModel by activityViewModels()

        profile = model.getProfile()

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
        genderSpinner.alpha = 0.5f


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
        ageSpinner.alpha = 0.5f

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
        heightTag.alpha = 0.5f



        val backButton = view.findViewById<ImageButton>(R.id.backArrow)

        backButton.setOnClickListener {
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragmentContainer, home_page.newInstance())
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
                    fragmentTransaction?.replace(R.id.fragmentContainer, home_page.newInstance())
                    fragmentTransaction?.setReorderingAllowed(true)
                    fragmentTransaction?.addToBackStack(null)
                    fragmentTransaction?.commit()

                } else if (selectedItem == "My Fitness Regime") {
                    val fragmentTransaction = fragmentManager?.beginTransaction()
                    fragmentTransaction?.replace(R.id.fragmentContainer, FitnessDataPage.newInstance())
                    fragmentTransaction?.setReorderingAllowed(true)
                    fragmentTransaction?.addToBackStack(null)
                    fragmentTransaction?.commit()
                } else if (selectedItem == "BMI Calculator") {
                    val fragmentTransaction = fragmentManager?.beginTransaction()
                    fragmentTransaction?.replace(R.id.fragmentContainer, BMI_calculator.newInstance())
                    fragmentTransaction?.setReorderingAllowed(true)
                    fragmentTransaction?.addToBackStack(null)
                    fragmentTransaction?.commit()
                }
            }
        }

        val nameField = view.findViewById<EditText>(R.id.nameTextField)
        nameField.text = Editable.Factory.getInstance().newEditable(profile?.name)
        nameField.setTextIsSelectable(false)
        nameField.alpha = 0.5f

        heightTag.setSelection(profile?.height!!.toInt())

        val weightField = view.findViewById<EditText>(R.id.weightTextField)
        weightField.text = Editable.Factory.getInstance().newEditable(profile?.weight)
        weightField.setTextIsSelectable(false)
        weightField.alpha = 0.5f

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
        cityField.alpha = 0.5f

        val countrySpinner : CountryCodePicker = view.findViewById(R.id.ccp)
        countrySpinner.setCountryForNameCode(profile?.country)
        countrySpinner.isEnabled = false
        countrySpinner.alpha = 0.5f

        val imageGuy = view.findViewById<ImageView>(R.id.ProfilePicImageView)
        imageGuy.setImageBitmap(profile?.image)

        val takePictureButton: Button = view.findViewById(R.id.TakePictureButton)
        takePictureButton.isEnabled = false
        takePictureButton.setOnClickListener{
            if (ContextCompat.checkSelfPermission(requireActivity().applicationContext, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, CAMERACODE)
            } else {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.CAMERA), PERMISSIONCODE)
            }
        }

        val updateProfileButton = view.findViewById<Button>(R.id.updateProfileButton)
        updateProfileButton.isEnabled = false

        val editProfileButton = view.findViewById<Button>(R.id.editProfileButton)
        editProfileButton.setOnClickListener{
            weightField.setTextIsSelectable(true)
            heightTag.isEnabled = true
            genderSpinner.isEnabled = true
            ageSpinner.isEnabled = true
            cityField.setTextIsSelectable(true)
            countrySpinner.isEnabled = true
            nameField.setTextIsSelectable(true)
            editProfileButton.isEnabled = false
            takePictureButton.isEnabled = true

            weightField.alpha = 1f
            heightTag.alpha = 1f
            genderSpinner.alpha = 1f
            ageSpinner.alpha = 1f
            cityField.alpha = 1f
            countrySpinner.alpha = 1f
            nameField.alpha = 1f


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
            countrySpinner.isEnabled = false
            cityField.setTextIsSelectable(false)
            nameField.setTextIsSelectable(false)
            editProfileButton.isEnabled = true
            takePictureButton.isEnabled = false

            weightField.alpha = 0.5f
            heightTag.alpha = 0.5f
            genderSpinner.alpha = 0.5f
            ageSpinner.alpha = 0.5f
            cityField.alpha = 0.5f
            countrySpinner.alpha = 0.5f
            nameField.alpha = 0.5f

            updateProfileButton.isEnabled = false

            profile?.height = heightText.toString()
            profile?.weight = weightText
            profile?.age = ageText.toString()
            profile?.city = cityText
            profile?.country = countrySpinner.selectedCountryCode
            profile?.name = nameText
            profile?.gender = genderText.toString()
            profile?.image = profilePic

        }
        super.onViewCreated(view, savedInstanceState)
    }

    fun isTablet(context: Context): Boolean {
        return ((context.getResources().getConfiguration().screenLayout
                and Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode ==  PERMISSIONCODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, CAMERACODE)
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val profilePicImageView = view?.findViewById<ImageView>(R.id.ProfilePicImageView)

        if (resultCode == Activity.RESULT_OK && requestCode == CAMERACODE) {
            val picDisplay: Bitmap = data!!.extras!!.get("data") as Bitmap
            profilePicImageView?.setImageBitmap(picDisplay)
            profilePic = picDisplay
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ProfilePage().apply {
                arguments = Bundle().apply {
                }
            }
    }
}