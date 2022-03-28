package com.example.myapplication

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.activityViewModels


class BMI_calculator : Fragment() {

    private var profile: Profile? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("FRAGMENT", "bmi")
        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {

        super.onDestroy()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_b_m_i_calculator, container, false)
    }

    private fun isTablet(context: Context): Boolean {
        return ((context.getResources().getConfiguration().screenLayout
                and Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val model: ProfileViewModel by activityViewModels()

        profile = model.getProfile()

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
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragmentContainer, home_page.newInstance())
            fragmentTransaction?.setReorderingAllowed(true)
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
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
                "Homepage", "My Profile", "My Fitness Regime"
            )
            var moduleListView = view.findViewById<ListView>(R.id.moduleListBMI)
            arrayAdapter = ArrayAdapter(this.requireContext(), R.layout.modules_side_bar, modules)

            moduleListView.adapter = arrayAdapter

            moduleListView.setOnItemClickListener { parent, _, position, _ ->
                val selectedItem = parent.getItemAtPosition(position) as String
                if (selectedItem == "My Fitness Regime") {
                    val fragmentTransaction = fragmentManager?.beginTransaction()
                    fragmentTransaction?.replace(R.id.fragmentContainer, FitnessDataPage.newInstance())
                    fragmentTransaction?.setReorderingAllowed(true)
                    fragmentTransaction?.addToBackStack(null)
                    fragmentTransaction?.commit()
                } else if (selectedItem == "Homepage") {
                    val fragmentTransaction = fragmentManager?.beginTransaction()
                    fragmentTransaction?.replace(R.id.fragmentContainer, home_page.newInstance())
                    fragmentTransaction?.setReorderingAllowed(true)
                    fragmentTransaction?.addToBackStack(null)
                    fragmentTransaction?.commit()
                } else if (selectedItem == "My Profile") {
                    val fragmentTransaction = fragmentManager?.beginTransaction()
                    fragmentTransaction?.replace(R.id.fragmentContainer, ProfilePage.newInstance())
                    fragmentTransaction?.setReorderingAllowed(true)
                    fragmentTransaction?.addToBackStack(null)
                    fragmentTransaction?.commit()
                }
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            BMI_calculator().apply {
                arguments = Bundle().apply {
                }
            }
    }
}