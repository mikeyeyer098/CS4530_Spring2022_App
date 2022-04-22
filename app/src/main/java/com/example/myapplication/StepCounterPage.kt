package com.example.myapplication

import android.content.Context
import android.content.res.Configuration
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.GestureDetector.SimpleOnGestureListener
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.runBlocking


class StepCounterPage : Fragment(), SensorEventListener{

    private var stepData : List<StepCountData>? = null
    private var profile : Profile? = null

    private var running = true
    private var totalSteps = 0f
    private var previousTotalSteps = 0f
    private var sensorManager: SensorManager ?= null
    private var stepCounter: Sensor ?= null

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

    private fun calcAverageMiles(stepCounts: List<StepCountData>): Double {
        var steps = 0.0
        var miles = 0.0
        var dayCount = 0.0
        stepCounts.forEach {
            steps += it.stepCount?.toDouble()!!
            miles += steps / 2250
            dayCount++
        }
        return miles / dayCount;
    }
    private fun calcAverageSteps(stepCounts: List<StepCountData>): Double {
        var steps = 0.0
        var dayCount = 0.0
        stepCounts.forEach {
            steps += it.stepCount?.toDouble()!!
            dayCount++
        }
        return steps / dayCount;
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        stepCounter = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        super.onViewCreated(view, savedInstanceState)

        val avgStepsText = view.findViewById<TextView>(R.id.AverageStepsText)
        val avgMileageText = view.findViewById<TextView>(R.id.AverageMileageText)
        val todaysStepsText = view.findViewById<TextView>(R.id.TodaysStepsText)

        val stepModel: StepCounterPageViewModel by activityViewModels()
        val userModel: ProfileViewModel by activityViewModels()

        profile = runBlocking { userModel.getProfile() }
        var exists = runBlocking {profile?.let {
            stepModel.checkExists(it.name)
        }}
        if(exists == true){
            stepData = runBlocking { profile?.let {
                stepModel.getStepData(it.name)
            } }
            avgMileageText.text = stepData?.let { calcAverageMiles(it) }.toString()
            avgStepsText.text = stepData?.let { calcAverageSteps(it) }.toString()
        }else{
            avgMileageText.text = "N/A"
            avgStepsText.text = "N/A"
        }

        // TODO change this
        todaysStepsText.text = "100"

        val gesture = GestureDetector(
            activity,
            object : SimpleOnGestureListener() {
                // start
                override fun onDoubleTap(e: MotionEvent?): Boolean {

                    return super.onDoubleTap(e)
                }
                // stop
                override fun onLongPress(e: MotionEvent?) {
                    super.onLongPress(e)
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

    override fun onResume() {
        super.onResume()
        Log.d("test", "On Resume")

        sensorManager?.registerListener(this, stepCounter, SensorManager.SENSOR_DELAY_UI)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        Log.d("test", "step senor changed")
            totalSteps = event!!.values[0]
            val currentSteps = totalSteps.toInt() - previousTotalSteps.toInt()
            view?.findViewById<TextView>(R.id.TodaysStepsText)?.text = currentSteps.toString()
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        TODO("Not yet implemented")
    }

}