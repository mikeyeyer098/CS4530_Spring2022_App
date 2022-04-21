package com.example.myapplication

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.GestureDetector.SimpleOnGestureListener
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider


class StepCounterPage : Fragment(){

    companion object {
        fun newInstance() = StepCounterPage()
    }

    private lateinit var viewModel: StepCounterPageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.step_counter_page_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gesture = GestureDetector(
            activity,
            object : SimpleOnGestureListener() {
                override fun onDoubleTap(e: MotionEvent?): Boolean {

                    return super.onDoubleTap(e)
                }
            })
        view.setOnTouchListener { v, event ->
            gesture.onTouchEvent(event)
            true
        }
        val backButton = view.findViewById<ImageButton>(R.id.backArrow)

        backButton.setOnClickListener {
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.fragmentContainer, HomePage.newInstance())
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

        if (isTablet(this.requireContext())) {
            val arrayAdapter: ArrayAdapter<String>
            val modules = arrayOf(
                "Homepage", "My Profile", "My Fitness Regime", "BMI Calculator"
            )

            var moduleListView = view.findViewById<ListView>(R.id.moduleListStepCounter)
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
                }else if (selectedItem == "My Profile") {
                    val fragmentTransaction = fragmentManager?.beginTransaction()
                    fragmentTransaction?.replace(R.id.fragmentContainer, ProfilePage.newInstance())
                    fragmentTransaction?.setReorderingAllowed(true)
                    fragmentTransaction?.addToBackStack(null)
                    fragmentTransaction?.commit()}

            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(StepCounterPageViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun isTablet(context: Context): Boolean {
        return ((context.getResources().getConfiguration().screenLayout
                and Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE)
    }

}