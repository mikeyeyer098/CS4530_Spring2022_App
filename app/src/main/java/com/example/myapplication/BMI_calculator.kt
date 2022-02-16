package com.example.myapplication

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*


class BMI_calculator : Fragment() {

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
        return inflater.inflate(R.layout.fragment_b_m_i_calculator, container, false)
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
            else{
                heightTag.setSelection(0)
            }
        }

        var weightTag = requireView().findViewById<EditText>(R.id.weightTextField)
        weightTag.setText(profile?.weight)

        var BMItag = requireView().findViewById<EditText>(R.id.BMIText)
        Log.i("test", "bmi: " + HealthCalculator().calculateBMI(profile?.weight.toString(), (profile?.height?.toInt()
            ?.plus(48)).toString()))
        BMItag.text = Editable.Factory.getInstance().newEditable(HealthCalculator().calculateBMI(profile?.weight.toString(), (profile?.height?.toInt()
            ?.plus(48)).toString()))

        val BMiButton = view.findViewById<Button>(R.id.CalculateBMIButton)

        BMiButton.setOnClickListener {
            var checkWeight = weightTag.text.toString()
            var checkHeight = (heightTag.selectedItemPosition + 48).toString()
            if (checkHeight == null || checkWeight == "")
            {
                checkHeight = (profile?.height!!.toInt() + 48).toString()
                checkWeight = profile?.weight!!.toInt().toString()
            }
            try {
                Log.i("test", "${checkHeight}, $checkWeight")
                BMItag.text = Editable.Factory.getInstance().newEditable(HealthCalculator().calculateBMI(checkWeight, checkHeight))
            }
            catch (e: Exception) {
                BMItag.text = Editable.Factory.getInstance().newEditable("Invalid")
            }

        }

        val backButton = view.findViewById<ImageButton>(R.id.backArrow)

        backButton.setOnClickListener {
            Log.i("test", "back button pressed")
            fragmentManager?.popBackStack()
        }

        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        @JvmStatic
        fun newInstance(profile: Profile) =
            BMI_calculator().apply {
                this.profile = profile
                arguments = Bundle().apply {

                }
            }
    }
}