package com.example.myapplication

import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
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
import com.amplifyframework.core.Amplify
import kotlinx.coroutines.runBlocking
import java.io.File


class StepCounterPage : Fragment(), SensorEventListener{

    private var stepData : List<StepCountData>? = null
    private var profile : Profile? = null

    private var running = false
    private var totalSteps = 0f
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

        todaysStepsText.text = "0"

        val gesture = GestureDetector(
            activity,
            object : SimpleOnGestureListener() {
                // start
                override fun onDoubleTap(e: MotionEvent?): Boolean {
                    running = true
                    view?.findViewById<TextView>(R.id.TodaysStepsText)?.setBackgroundColor(Color.parseColor("#b6d7a8"))
                    return super.onDoubleTap(e)
                }
                // stop
                override fun onLongPress(e: MotionEvent?) {
                    view?.findViewById<TextView>(R.id.TodaysStepsText)?.setBackgroundColor(Color.parseColor("#434343"))

                    if(exists == true){
                        var lastDate = Integer.parseInt(stepData!!.last().date)
                        lastDate++;
                        var newStepData = StepCountData(profile!!.name, lastDate.toString(), totalSteps.toString())
                        runBlocking { stepModel.insertSteps(newStepData) }
                    }else{
                        var lastDate = 1
                        var newStepData = StepCountData(profile!!.name, lastDate.toString(), totalSteps.toString())
                        runBlocking { stepModel.insertSteps(newStepData) }
                    }
                    running = false
                    uploadFileToS3();

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

    private fun uploadFileToS3() {

        val dbFile: File = File(
            context?.getDatabasePath("Step_Data.db")
                ?.getPath()
        )

        val dbFileSH: File = File(
            context?.getDatabasePath("Step_Data.db-shm")
                ?.getPath()
        )

        val dbFileWAL: File = File(
            context?.getDatabasePath("Step_Data.db-wal")
                ?.getPath()
        )


        Amplify.Storage.uploadFile(profile?.name + "Step_Data.db", dbFile,
            { Log.i("MyAmplifyApp", "Successfully uploaded: ${it.key}") },
            { Log.e("MyAmplifyApp", "Upload failed", it) })

        Amplify.Storage.uploadFile(profile?.name + "Step_Data.db-shm", dbFileSH,
            { Log.i("MyAmplifyApp", "Successfully uploaded: ${it.key}") },
            { Log.e("MyAmplifyApp", "Upload failed", it) })

        Amplify.Storage.uploadFile(profile?.name + "Step_Data.db-wal", dbFileWAL,
            { Log.i("MyAmplifyApp", "Successfully uploaded: ${it.key}") },
            { Log.e("MyAmplifyApp", "Upload failed", it) })
    }

    override fun onResume() {
        super.onResume()
        sensorManager?.registerListener(this, stepCounter, SensorManager.SENSOR_DELAY_UI)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(running) {
            totalSteps = event!!.values[0]
            view?.findViewById<TextView>(R.id.TodaysStepsText)?.text = totalSteps.toString()

            if(totalSteps % 100 == 0f) {
                uploadFileToS3();
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

}