package com.example.myapplication

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView

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