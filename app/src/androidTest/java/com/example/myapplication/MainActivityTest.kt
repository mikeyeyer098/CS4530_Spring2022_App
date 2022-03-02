package com.example.myapplication

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.hamcrest.Matchers.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
    }

    @Test
    fun testCreateProfileNameFilled() {
        onView(withId(R.id.nameTextField)).perform(typeText("Joe Rogan"))
        onView(withId(R.id.nameTextField)).check(matches(withText("Joe Rogan")))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.createProfileButton)).perform(click())
        onView(withId(R.id.nameTextField)).check(doesNotExist())
        onView(withId(R.id.MyFitnessRegimenButton)).check(matches(isDisplayed()))
    }

    @Test
    fun testCreateProfileNameNotFilled() {
        onView(withId(R.id.createProfileButton)).perform(click())
        onView(withId(R.id.MyFitnessRegimenButton)).check(doesNotExist())
        onView(withId(R.id.nameTextField)).check(matches(isDisplayed()))
    }

    @Test
    fun testCreateProfileOtherInfoNoName() {
        onView(withId(R.id.weightTextField)).perform(typeText("128"))
        onView(withId(R.id.weightTextField)).check(matches(withText("128")))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.createProfileButton)).perform(click())
        onView(withId(R.id.MyFitnessRegimenButton)).check(doesNotExist())
        onView(withId(R.id.nameTextField)).check(matches(isDisplayed()))
    }

    @Test
    fun testCreateProfileFullInfo() {
        onView(withId(R.id.nameTextField)).perform(typeText("Joe Rogan"))
        onView(withId(R.id.nameTextField)).check(matches(withText("Joe Rogan")))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.weightTextField)).perform(typeText("128"))
        onView(withId(R.id.weightTextField)).check(matches(withText("128")))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.heightSpinner)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("4 ' 6 \""))).perform(click())
        onView(withId(R.id.heightSpinner)).check(matches(withSpinnerText("4 ' 6 \"")))

        onView(withId(R.id.ageSpinner)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("23 years old"))).perform(click())
        onView(withId(R.id.ageSpinner)).check(matches(withSpinnerText("23 years old")))

        onView(withId(R.id.genderSpinner)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("Male"))).perform(click())
        onView(withId(R.id.genderSpinner)).check(matches(withSpinnerText("Male")))

        onView(withId(R.id.cityTextField)).perform(typeText("Mumbai"))
        onView(withId(R.id.cityTextField)).check(matches(withText("Mumbai")))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.createProfileButton)).perform(click())
        onView(withId(R.id.nameTextField)).check(doesNotExist())
        onView(withId(R.id.MyFitnessRegimenButton)).check(matches(isDisplayed()))
    }

    @Test
    fun testProfileInfoUpdateClicked() {
        onView(withId(R.id.nameTextField)).perform(typeText("Joe Rogan"))
        onView(withId(R.id.nameTextField)).check(matches(withText("Joe Rogan")))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.weightTextField)).perform(typeText("128"))
        onView(withId(R.id.weightTextField)).check(matches(withText("128")))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.heightSpinner)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("4 ' 6 \""))).perform(click())
        onView(withId(R.id.heightSpinner)).check(matches(withSpinnerText("4 ' 6 \"")))

        onView(withId(R.id.ageSpinner)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("23 years old"))).perform(click())
        onView(withId(R.id.ageSpinner)).check(matches(withSpinnerText("23 years old")))

        onView(withId(R.id.genderSpinner)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("Male"))).perform(click())
        onView(withId(R.id.genderSpinner)).check(matches(withSpinnerText("Male")))

        onView(withId(R.id.cityTextField)).perform(typeText("Mumbai"))
        onView(withId(R.id.cityTextField)).check(matches(withText("Mumbai")))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.createProfileButton)).perform(click())
        onView(withId(R.id.nameTextField)).check(doesNotExist())
        onView(withId(R.id.MyFitnessRegimenButton)).check(matches(isDisplayed()))


        onView(withId(R.id.MyProfileButton)).perform(click())

        onView(withId(R.id.nameTextField)).check(matches(withText("Joe Rogan")))
        onView(withId(R.id.weightTextField)).check(matches(withText("128")))
        onView(withId(R.id.heightSpinner)).check(matches(withSpinnerText("4 ' 6 \"")))
        onView(withId(R.id.ageSpinner)).check(matches(withSpinnerText("23 years old")))
        onView(withId(R.id.genderSpinner)).check(matches(withSpinnerText("Male")))
        onView(withId(R.id.cityTextField)).check(matches(withText("Mumbai")))

        onView(withId(R.id.editProfileButton)).perform(click())

        onView(withId(R.id.nameTextField)).perform(typeText("gini"))
        onView(withId(R.id.nameTextField)).check(matches(withText("Joe Rogangini")))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.updateProfileButton)).perform(click())
        onView(withId(R.id.nameTextField)).check(matches(withText("Joe Rogangini")))

        // Leave and come back to verify information was saved correctly

        onView(withId(R.id.backArrow)).perform(click())
        onView(withId(R.id.MyProfileButton)).perform(click())
        onView(withId(R.id.nameTextField)).check(matches(withText("Joe Rogangini")))

    }

    @Test
    fun testProfileInfoUpdateNotClicked() {
        onView(withId(R.id.nameTextField)).perform(typeText("Joe Rogan"))
        onView(withId(R.id.nameTextField)).check(matches(withText("Joe Rogan")))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.weightTextField)).perform(typeText("128"))
        onView(withId(R.id.weightTextField)).check(matches(withText("128")))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.heightSpinner)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("4 ' 6 \""))).perform(click())
        onView(withId(R.id.heightSpinner)).check(matches(withSpinnerText("4 ' 6 \"")))

        onView(withId(R.id.ageSpinner)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("23 years old"))).perform(click())
        onView(withId(R.id.ageSpinner)).check(matches(withSpinnerText("23 years old")))

        onView(withId(R.id.genderSpinner)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("Male"))).perform(click())
        onView(withId(R.id.genderSpinner)).check(matches(withSpinnerText("Male")))

        onView(withId(R.id.cityTextField)).perform(typeText("Mumbai"))
        onView(withId(R.id.cityTextField)).check(matches(withText("Mumbai")))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.createProfileButton)).perform(click())
        onView(withId(R.id.nameTextField)).check(doesNotExist())
        onView(withId(R.id.MyFitnessRegimenButton)).check(matches(isDisplayed()))

        onView(withId(R.id.MyProfileButton)).perform(click())

        onView(withId(R.id.nameTextField)).check(matches(withText("Joe Rogan")))
        onView(withId(R.id.weightTextField)).check(matches(withText("128")))
        onView(withId(R.id.heightSpinner)).check(matches(withSpinnerText("4 ' 6 \"")))
        onView(withId(R.id.ageSpinner)).check(matches(withSpinnerText("23 years old")))
        onView(withId(R.id.genderSpinner)).check(matches(withSpinnerText("Male")))
        onView(withId(R.id.cityTextField)).check(matches(withText("Mumbai")))

        onView(withId(R.id.editProfileButton)).perform(click())

        onView(withId(R.id.nameTextField)).perform(typeText("gini"))
        onView(withId(R.id.nameTextField)).check(matches(withText("Joe Rogangini")))
        Espresso.closeSoftKeyboard()

        // Leave and come back to verify information was saved correctly

        onView(withId(R.id.backArrow)).perform(click())
        onView(withId(R.id.MyProfileButton)).perform(click())
        onView(withId(R.id.nameTextField)).check(matches(withText("Joe Rogan")))
    }

    @Test
    fun testBMICalculator() {
        onView(withId(R.id.nameTextField)).perform(typeText("Joe Rogan"))
        onView(withId(R.id.nameTextField)).check(matches(withText("Joe Rogan")))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.weightTextField)).perform(typeText("128"))
        onView(withId(R.id.weightTextField)).check(matches(withText("128")))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.heightSpinner)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("4 ' 6 \""))).perform(click())
        onView(withId(R.id.heightSpinner)).check(matches(withSpinnerText("4 ' 6 \"")))

        onView(withId(R.id.ageSpinner)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("23 years old"))).perform(click())
        onView(withId(R.id.ageSpinner)).check(matches(withSpinnerText("23 years old")))

        onView(withId(R.id.genderSpinner)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("Male"))).perform(click())
        onView(withId(R.id.genderSpinner)).check(matches(withSpinnerText("Male")))

        onView(withId(R.id.cityTextField)).perform(typeText("Mumbai"))
        onView(withId(R.id.cityTextField)).check(matches(withText("Mumbai")))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.createProfileButton)).perform(click())
        onView(withId(R.id.nameTextField)).check(doesNotExist())
        onView(withId(R.id.MyFitnessRegimenButton)).check(matches(isDisplayed()))

        onView(withId(R.id.BMICalcButton)).perform(click())

        onView(withId(R.id.heightSpinner)).check(matches(withSpinnerText("4 ' 6 \"")))
        onView(withId(R.id.weightTextField)).check(matches(withText("128")))
        onView(withId(R.id.BMIText)).check(matches(withText("29")))

        onView(withId(R.id.heightSpinner)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("4 ' 7 \""))).perform(click())
        onView(withId(R.id.heightSpinner)).check(matches(withSpinnerText("4 ' 7 \"")))

        onView(withId(R.id.weightTextField)).perform(replaceText("140"))
        onView(withId(R.id.weightTextField)).check(matches(withText("140")))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.CalculateBMIButton)).perform(click())
        onView(withId(R.id.BMIText)).check(matches(withText("31")))

        // Leave and come back to verify information was saved correctly

        onView(withId(R.id.backArrow)).perform(click())

        onView(withId(R.id.BMICalcButton)).perform(click())

        onView(withId(R.id.heightSpinner)).check(matches(withSpinnerText("4 ' 6 \"")))
        onView(withId(R.id.weightTextField)).check(matches(withText("128")))
        onView(withId(R.id.BMIText)).check(matches(withText("29")))
    }

    @Test
    fun testFitnessPage() {
        onView(withId(R.id.nameTextField)).perform(typeText("Joe Rogan"))
        onView(withId(R.id.nameTextField)).check(matches(withText("Joe Rogan")))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.weightTextField)).perform(typeText("128"))
        onView(withId(R.id.weightTextField)).check(matches(withText("128")))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.heightSpinner)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("4 ' 6 \""))).perform(click())
        onView(withId(R.id.heightSpinner)).check(matches(withSpinnerText("4 ' 6 \"")))

        onView(withId(R.id.ageSpinner)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("23 years old"))).perform(click())
        onView(withId(R.id.ageSpinner)).check(matches(withSpinnerText("23 years old")))

        onView(withId(R.id.genderSpinner)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("Male"))).perform(click())
        onView(withId(R.id.genderSpinner)).check(matches(withSpinnerText("Male")))

        onView(withId(R.id.cityTextField)).perform(typeText("Mumbai"))
        onView(withId(R.id.cityTextField)).check(matches(withText("Mumbai")))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.createProfileButton)).perform(click())
        onView(withId(R.id.nameTextField)).check(doesNotExist())
        onView(withId(R.id.MyFitnessRegimenButton)).check(matches(isDisplayed()))

        onView(withId(R.id.MyFitnessRegimenButton)).perform(click())

        onView(withId(R.id.heightSpinner)).check(matches(withSpinnerText("4 ' 6 \"")))
        onView(withId(R.id.weightTextField)).check(matches(withText("128")))
        onView(withId(R.id.regimenSpinner)).check(matches(withSpinnerText("Gain Weight")))
        onView(withId(R.id.activityLevelSpinner)).check(matches(withSpinnerText("Sedentary")))
        onView(withId(R.id.poundsGoalSpinner)).check(matches(withSpinnerText("1 lb")))
        // TODO: is this supposed to have 1825 on startup of the fitness page? Should Fitness goal should start off at "Maintain weight"?
        onView(withId(R.id.DailyCaloriesText)).check(matches(withText("1825")))
        onView(withId(R.id.BMRText)).check(matches(withText("1825")))
        onView(withId(R.id.BMIText)).check(matches(withText("29")))

        // Edit Fitness Data
        onView(withId(R.id.editProfileButton)).perform(click())

        onView(withId(R.id.heightSpinner)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("4 ' 9 \""))).perform(click())
        onView(withId(R.id.heightSpinner)).check(matches(withSpinnerText("4 ' 9 \"")))

        onView(withId(R.id.weightTextField)).perform(replaceText("160"))
        onView(withId(R.id.weightTextField)).check(matches(withText("160")))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.regimenSpinner)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("Lose Weight"))).perform(click())
        onView(withId(R.id.regimenSpinner)).check(matches(withSpinnerText("Lose Weight")))

        onView(withId(R.id.activityLevelSpinner)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("Active"))).perform(click())
        onView(withId(R.id.activityLevelSpinner)).check(matches(withSpinnerText("Active")))

        onView(withId(R.id.poundsGoalSpinner)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("2 lbs"))).perform(click())
        onView(withId(R.id.poundsGoalSpinner)).check(matches(withSpinnerText("2 lbs")))

        onView(withId(R.id.updateProfileButton)).perform(click())

        onView(withId(R.id.DailyCaloriesText)).check(matches(withText("1990")))
        onView(withId(R.id.BMRText)).check(matches(withText("2990")))
        onView(withId(R.id.BMIText)).check(matches(withText("33")))

        // Leave and come back to verify information was saved correctly

        onView(withId(R.id.backArrow)).perform(click())

        onView(withId(R.id.MyFitnessRegimenButton)).perform(click())

        onView(withId(R.id.heightSpinner)).check(matches(withSpinnerText("4 ' 9 \"")))
        onView(withId(R.id.weightTextField)).check(matches(withText("160")))
        // TODO: BUG HERE not remembering correct fitness goal when changed
        //onView(withId(R.id.regimenSpinner)).check(matches(withSpinnerText("Lose Weight")))
        // TODO: BUG HERE not remembering correct activity level when changed
        //onView(withId(R.id.activityLevelSpinner)).check(matches(withSpinnerText("Active")))
        // TODO: BUG HERE not remembering correct weight goal level when changed
        //onView(withId(R.id.poundsGoalSpinner)).check(matches(withSpinnerText("2 lbs")))
        onView(withId(R.id.DailyCaloriesText)).check(matches(withText("1990")))
        onView(withId(R.id.BMRText)).check(matches(withText("2990")))
        onView(withId(R.id.BMIText)).check(matches(withText("33")))

        // Go to Profile and check if Height and Weight have been correctly saved

        onView(withId(R.id.backArrow)).perform(click())

        onView(withId(R.id.MyProfileButton)).perform(click())

        onView(withId(R.id.nameTextField)).check(matches(withText("Joe Rogan")))
        onView(withId(R.id.weightTextField)).check(matches(withText("160")))
        onView(withId(R.id.heightSpinner)).check(matches(withSpinnerText("4 ' 9 \"")))
        onView(withId(R.id.ageSpinner)).check(matches(withSpinnerText("23 years old")))
        onView(withId(R.id.genderSpinner)).check(matches(withSpinnerText("Male")))
        onView(withId(R.id.cityTextField)).check(matches(withText("Mumbai")))

    }

    @Test
    fun testWeatherRealCity() {
        onView(withId(R.id.nameTextField)).perform(typeText("Joe Rogan"))
        onView(withId(R.id.nameTextField)).check(matches(withText("Joe Rogan")))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.cityTextField)).perform(typeText("Mumbai"))
        onView(withId(R.id.cityTextField)).check(matches(withText("Mumbai")))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.createProfileButton)).perform(click())
        onView(withId(R.id.nameTextField)).check(doesNotExist())
        onView(withId(R.id.MyFitnessRegimenButton)).check(matches(isDisplayed()))

        onView(withId(R.id.TemperatureHighText)).check(matches(not("NA")))
    }

    @Test
    fun testWeatherFakeCity() {
        onView(withId(R.id.nameTextField)).perform(typeText("Joe Rogan"))
        onView(withId(R.id.nameTextField)).check(matches(withText("Joe Rogan")))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.cityTextField)).perform(typeText("Travis Scott Burger"))
        onView(withId(R.id.cityTextField)).check(matches(withText("Travis Scott Burger")))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.createProfileButton)).perform(click())
        onView(withId(R.id.nameTextField)).check(doesNotExist())
        onView(withId(R.id.MyFitnessRegimenButton)).check(matches(isDisplayed()))

        onView(withId(R.id.TemperatureHighText)).check(matches(withText("NA")))
    }

    @After
    fun tearDown() {
    }
}