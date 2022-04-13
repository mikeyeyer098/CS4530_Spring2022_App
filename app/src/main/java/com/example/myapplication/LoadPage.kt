package com.example.myapplication

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.hbb20.CountryCodePicker
import kotlinx.coroutines.runBlocking
import java.io.ByteArrayOutputStream


private val PERMISSIONCODE = 100
private val CAMERACODE = 200

/**
 * A simple [Fragment] subclass.
 * Use the [load_page.newInstance] factory method to
 * create an instance of this fragment.
 */
class load_page : Fragment() {
    var nav: FragmentManager ?= null
    private val PERMISSIONCODE = 100
    private val CAMERACODE = 200
    var profilePic: Bitmap? = null
    var profile: Profile ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_load_page, container, false)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("FRAGMENT", "load")
        super.onSaveInstanceState(outState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        nav = parentFragmentManager

        val model: ProfileViewModel by activityViewModels()

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

        val heightSpinner : Spinner = view.findViewById(R.id.heightSpinner)
        var heights : ArrayList<String> = arrayListOf()
        heights.add("Height:")
        for(i in 4..7) {
            for (j in 0..11) {
                heights.add("$i \' $j \"")
            }
        }
        var heightAdapter = ArrayAdapter(this.requireContext(), R.layout.spinner_item, heights)
        heightAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        heightSpinner.adapter = heightAdapter
        heightSpinner.prompt = "Select height:"

        val takePictureButton: Button = view.findViewById(R.id.TakePictureButton)

        val photoPath: String = ""

        takePictureButton.setOnClickListener{
            if (ContextCompat.checkSelfPermission(requireActivity().applicationContext, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, CAMERACODE)
            } else {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.CAMERA), PERMISSIONCODE)
            }
        }

        val countrySpinner : CountryCodePicker = view.findViewById(R.id.ccp)

        val createProfileButton: Button = view.findViewById(R.id.createProfileButton)

        createProfileButton.setOnClickListener {
            if (view.findViewById<EditText>(R.id.nameTextField).text.toString() != "") {
                val nameText: String = view.findViewById<EditText>(R.id.nameTextField).text.toString()
                val heightSelection: Int =
                    heightSpinner.selectedItemPosition
                Log.i("test", heightSelection.toString())
                val weightText: String =
                    view.findViewById<EditText>(R.id.weightTextField).text.toString()
                val ageSelection: Int = ageSpinner.selectedItemPosition
                val genderSelection: String = genderSpinner.selectedItem as String
                val cityText: String = view.findViewById<EditText>(R.id.cityTextField).text.toString()
                val countrySelection: String = countrySpinner.selectedCountryNameCode

                val bos = ByteArrayOutputStream()
                profilePic?.compress(Bitmap.CompressFormat.PNG, 100, bos)
                val bArray: ByteArray = bos.toByteArray()

                runBlocking { model.createProfile(nameText, heightSelection.toString(), weightText, ageSelection.toString(), genderSelection.toString(),
                    cityText, countrySelection.toString(), photoPath, "0", "", "", "", "0", bArray) }

                val fragmentTransaction = fragmentManager?.beginTransaction()
                fragmentTransaction?.replace(R.id.fragmentContainer, HomePage.newInstance())
                fragmentTransaction?.commit()
            } else
            {
                Log.i("test", "popup pre")
                popupMessage()
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }

    fun popupMessage() {
        Log.i("test", "popup in")
        val alertDialogBuilder: AlertDialog.Builder  = AlertDialog.Builder(view?.context)
        alertDialogBuilder.setMessage("Please enter a name before continuing")
        alertDialogBuilder.setTitle("Enter Name")
        val alertDialog: AlertDialog = alertDialogBuilder.create();
        alertDialog.show();
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
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment load_page.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            load_page().apply {
                arguments = Bundle().apply {

                }
            }
    }
}