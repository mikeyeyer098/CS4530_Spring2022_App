package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BMI_calculator.newInstance] factory method to
 * create an instance of this fragment.
 */
class BMI_calculator : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var profile: Profile? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
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

        var heightTag = requireView().findViewById<TextView>(R.id.heightTextField)
        heightTag.hint = "Height: ${profile?.height}"

        var weightTag = requireView().findViewById<TextView>(R.id.weightTextField)
        weightTag.hint = "Weight: ${profile?.weight}"

        var BMItag = requireView().findViewById<TextView>(R.id.BMIText)
        BMItag.text = HealthCalculator().calculateBMI(profile?.weight.toString(), profile?.height.toString())

        val BMiButton = view.findViewById(R.id.CalculateBMIButton) as Button
        BMiButton.setOnClickListener {
            BMItag.text = HealthCalculator().calculateBMI(profile?.weight.toString(), profile?.height.toString())
        }

        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BMI_calculator.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(profile: Profile) =
            BMI_calculator().apply {
                this.profile = profile
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}