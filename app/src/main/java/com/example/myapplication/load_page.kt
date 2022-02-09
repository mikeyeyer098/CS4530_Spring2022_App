package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.Image
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import java.io.File

private val PERMISSIONCODE = 100
private val CAMERACODE = 200

/**
 * A simple [Fragment] subclass.
 * Use the [load_page.newInstance] factory method to
 * create an instance of this fragment.
 */
class load_page : Fragment() {
    // TODO: Rename and change types of parameters
    var nav: FragmentManager ?= null
    private val PERMISSIONCODE = 100
    private val CAMERACODE = 200

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        nav = parentFragmentManager

        val createProfileButton: Button = view.findViewById(R.id.createProfileButton)

        createProfileButton.setOnClickListener {
            val nameText: String = view.findViewById<EditText>(R.id.nameTextField).text.toString()
            val heightText: String = view.findViewById<EditText>(R.id.heightTextField).text.toString()
            val weightText: String = view.findViewById<EditText>(R.id.weightTextField).text.toString()
            val ageText: String = view.findViewById<EditText>(R.id.ageTextField).text.toString()
            val genderText: String = view.findViewById<EditText>(R.id.genderTextField).text.toString()
            val cityText: String = view.findViewById<EditText>(R.id.cityTextField).text.toString()

            val profile = Profile(nameText, heightText, weightText, ageText, genderText,
                cityText, "", false, "", "")
            Log.i ("test", profile.printForStoring())
            Log.i("test", requireActivity().application.cacheDir.absolutePath)
            File.createTempFile("filename", profile.printForStoring(), requireActivity().application.cacheDir)

            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragmentContainer, home_page())
            fragmentTransaction?.commit()
        }

        val takePictureButton: Button = view.findViewById(R.id.TakePictureButton)

        takePictureButton.setOnClickListener{
            if (ContextCompat.checkSelfPermission(requireActivity().applicationContext, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, CAMERACODE)
            } else {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.CAMERA), PERMISSIONCODE)
            }
        }
        super.onViewCreated(view, savedInstanceState)
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
        //val profilePicHomePage = view?.findViewById<ImageButton>(R.id.ProfilePicThumbnail)

        if (resultCode == Activity.RESULT_OK && requestCode == CAMERACODE) {
            val picDisplay: Bitmap = data!!.extras!!.get("data") as Bitmap
            profilePicImageView?.setImageBitmap(picDisplay)
            //profilePicHomePage?.setImageBitmap(picDisplay)
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