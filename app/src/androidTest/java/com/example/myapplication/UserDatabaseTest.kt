package com.example.myapplication

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNull
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
    class UserDatabaseTest {

        private lateinit var userDao: UserDao
        private lateinit var db: UserDatabase

        @Before
        fun createDb() {
            val context : Context = ApplicationProvider.getApplicationContext()
            // Using an in-memory database because the information stored here disappears when the
            // process is killed.
            db = Room.inMemoryDatabaseBuilder(context, UserDatabase::class.java)
                // Allowing main thread queries, just for testing.
                .allowMainThreadQueries()
                .build()
            userDao = db.userDao
        }

        @After
        @Throws(IOException::class)
        fun closeDb() {
            db.close()
        }

        @Test
        @Throws(Exception::class)
        fun insertAndGetUser() = runBlocking {
            val user = Profile("testUser", null, null, null, null, null, null, null, null, null,null,null, null, byteArrayOf())
            userDao.insert(user)
            val gotUser = userDao.get("testUser")
            assertEquals(gotUser!!.name, "testUser")
        }
    @Test
    @Throws(Exception::class)
    fun insertAndGetUsers() = runBlocking {
        val user = Profile("testUser", null, null, null, null, null, null, null, null, null,null,null, null, byteArrayOf())
        userDao.insert(user)
        val user1 = Profile("testUser1", null, null, null, null, null, null, null, null, null,null,null, null, byteArrayOf())
        userDao.insert(user1)
        val user2 = Profile("testUser2", null, null, null, null, null, null, null, null, null,null,null, null, byteArrayOf())
        userDao.insert(user2)
        val gotUser1 = userDao.get("testUser")
        assertEquals(gotUser1!!.name, "testUser")
        val gotUser2 = userDao.get("testUser1")
        assertEquals(gotUser2!!.name, "testUser1")
        val gotUser3 = userDao.get("testUser2")
        assertEquals(gotUser3!!.name, "testUser2")
    }


    @Test
    @Throws(Exception::class)
    fun insertAndGetUserWithData() = runBlocking {
        val user = Profile("testUser", "testHeight", "testWeight", "testAge", "testGender", "testCity", "testCountry", "testPath", "testActive", "testBMR","testBMI","testRegimen", "testWeightGoal", byteArrayOf())
        userDao.insert(user)

        val gotUser1 = userDao.get("testUser")
        assertEquals(gotUser1!!.name, "testUser")
        assertEquals(gotUser1!!.height, "testHeight")
        assertEquals(gotUser1!!.weight, "testWeight")
        assertEquals(gotUser1!!.age, "testAge")
        assertEquals(gotUser1!!.gender, "testGender")
        assertEquals(gotUser1!!.city, "testCity")
        assertEquals(gotUser1!!.country, "testCountry")
        assertEquals(gotUser1!!.imagePath, "testPath")
        assertEquals(gotUser1!!.active, "testActive")
        assertEquals(gotUser1!!.bmr, "testBMR")
        assertEquals(gotUser1!!.bmi, "testBMI")
        assertEquals(gotUser1!!.regimen, "testRegimen")
        assertEquals(gotUser1!!.weightGoal, "testWeightGoal")

    }

    @Test
    @Throws(Exception::class)
    fun updateUser() = runBlocking {
        val user = Profile("testUser", "testHeight", "testWeight", "testAge", "testGender", "testCity", "testCountry", "testPath", "testActive", "testBMR","tesBMI","testRegimen", "testWeightGoal", byteArrayOf())
        userDao.insert(user)
        user.name = "testUser"
        user.height = "updateHeight"
        user.weight = "updateWeight"
        user.age = "updateAge"
        user.gender = "updateGender"
        user.city = "updateCity"
        user.country = "updateCountry"
        user.imagePath = "updatePath"
        user.active = "updateActive"
        user.bmr = "updateBMR"
        user.bmi = "updateBMI"
        user.regimen = "updateRegimen"
        user.weightGoal = "updateWeightGoal"



        userDao.update(user)
        val gotUser1 = userDao.get("testUser")
        assertEquals(gotUser1!!.name, "testUser")
        assertEquals(gotUser1!!.height, "updateHeight")
        assertEquals(gotUser1!!.weight, "updateWeight")
        assertEquals(gotUser1!!.age, "updateAge")
        assertEquals(gotUser1!!.gender, "updateGender")
        assertEquals(gotUser1!!.city, "updateCity")
        assertEquals(gotUser1!!.country, "updateCountry")
        assertEquals(gotUser1!!.imagePath, "updatePath")
        assertEquals(gotUser1!!.active, "updateActive")
        assertEquals(gotUser1!!.bmr, "updateBMR")
        assertEquals(gotUser1!!.bmi, "updateBMI")
        assertEquals(gotUser1!!.regimen, "updateRegimen")
        assertEquals(gotUser1!!.weightGoal, "updateWeightGoal")

    }

    @Test
    @Throws(Exception::class)
    fun clearAllUsers() = runBlocking {
        val user = Profile("testUser", "testHeight", "testWeight", "testAge", "testGender", "testCity", "testCountry", "testPath", "testActive", "testBMR","tesBMI","testRegimen", "testWeightGoal", byteArrayOf())
        userDao.insert(user)
        val user1 = Profile("testUser1", null, null, null, null, null, null, null, null, null,null,null, null, byteArrayOf())
        userDao.insert(user1)
        val user2 = Profile("testUser2", null, null, null, null, null, null, null, null, null,null,null, null, byteArrayOf())
        userDao.insert(user2)

        userDao.clearAll()
        assertNull(userDao.get("testUser"))
        assertNull(userDao.get("testUser1"))
        assertNull(userDao.get("testUser2"))


    }

    @Test
    @Throws(Exception::class)
    fun deleteUsers() = runBlocking {
        val user = Profile("testUser", "testHeight", "testWeight", "testAge", "testGender", "testCity", "testCountry", "testPath", "testActive", "testBMR","tesBMI","testRegimen", "testWeightGoal", byteArrayOf())
        userDao.insert(user)
        val user1 = Profile("testUser1", null, null, null, null, null, null, null, null, null,null,null, null, byteArrayOf())
        userDao.insert(user1)
        val user2 = Profile("testUser2", null, null, null, null, null, null, null, null, null,null,null, null, byteArrayOf())
        userDao.insert(user2)

        userDao.deleteUser("testUser")
        assertNull(userDao.get("testUser"))

        userDao.deleteUser("testUser1")
        assertNull(userDao.get("testUser1"))

        userDao.deleteUser("testUser2")
        assertNull(userDao.get("testUser2"))

    }

    @Test
    @Throws(Exception::class)
    fun insertSameUser() = runBlocking {
        val user = Profile("testUser", "testHeight", "testWeight", "testAge", "testGender", "testCity", "testCountry", "testPath", "testActive", "testBMR","testBMI","testRegimen", "testWeightGoal", byteArrayOf())
        userDao.insert(user)
        val user2 = Profile("testUser", "testHeight2", "testWeight2", "testAge2", "testGender2", "testCity2", "testCountry2", "testPath2", "testActive2", "testBMR2","tesBMI2","testRegimen2", "testWeightGoal2", byteArrayOf())
        userDao.insert(user2)

        val gotUser1 = userDao.get("testUser")
        assertEquals(gotUser1!!.name, "testUser")
        assertEquals(gotUser1!!.height, "testHeight")
        assertEquals(gotUser1!!.weight, "testWeight")
        assertEquals(gotUser1!!.age, "testAge")
        assertEquals(gotUser1!!.gender, "testGender")
        assertEquals(gotUser1!!.city, "testCity")
        assertEquals(gotUser1!!.country, "testCountry")
        assertEquals(gotUser1!!.imagePath, "testPath")
        assertEquals(gotUser1!!.active, "testActive")
        assertEquals(gotUser1!!.bmr, "testBMR")
        assertEquals(gotUser1!!.bmi, "testBMI")
        assertEquals(gotUser1!!.regimen, "testRegimen")
        assertEquals(gotUser1!!.weightGoal, "testWeightGoal")

    }

    @Test
    @Throws(Exception::class)
    fun insertAndDeleteSameUser() = runBlocking {
        val user = Profile("testUser", "testHeight", "testWeight", "testAge", "testGender", "testCity", "testCountry", "testPath", "testActive", "testBMR","testBMI","testRegimen", "testWeightGoal", byteArrayOf())
        userDao.insert(user)
        userDao.deleteUser("testUser")
        userDao.insert(user)
        val gotUser1 = userDao.get("testUser")
        assertEquals(gotUser1!!.name, "testUser")
        assertEquals(gotUser1!!.height, "testHeight")
        assertEquals(gotUser1!!.weight, "testWeight")
        assertEquals(gotUser1!!.age, "testAge")
        assertEquals(gotUser1!!.gender, "testGender")
        assertEquals(gotUser1!!.city, "testCity")
        assertEquals(gotUser1!!.country, "testCountry")
        assertEquals(gotUser1!!.imagePath, "testPath")
        assertEquals(gotUser1!!.active, "testActive")
        assertEquals(gotUser1!!.bmr, "testBMR")
        assertEquals(gotUser1!!.bmi, "testBMI")
        assertEquals(gotUser1!!.regimen, "testRegimen")
        assertEquals(gotUser1!!.weightGoal, "testWeightGoal")

    }

    @Test
    @Throws(Exception::class)
    fun insert10000Users() = runBlocking {

        for(i in 1..10000){
            val userName = "testUser$i"
            val user = Profile(userName, null, null, null, null, null, null, null, null, null,null,null, null, byteArrayOf())
            userDao.insert(user)
        }
        for(i in 1..10000){
            val userName = "testUser$i"
            val gotUser = userDao.get(userName)
            assertEquals(gotUser!!.name, userName)
        }
    }

    @Test
    @Throws(Exception::class)
    fun delete10000Users() = runBlocking {

        for(i in 1..10000){
            val userName = "testUser$i"
            val user = Profile(userName, null, null, null, null, null, null, null, null, null,null,null, null, byteArrayOf())
            userDao.insert(user)
        }
        for(i in 1..10000){
            val userName = "testUser$i"
            userDao.deleteUser(userName)
            assertNull(userDao.get(userName))
        }
    }

    @Test
    @Throws(Exception::class)
    fun clear10000Users() = runBlocking {

        for(i in 1..10000){
            val userName = "testUser$i"
            val user = Profile(userName, null, null, null, null, null, null, null, null, null,null,null, null, byteArrayOf())
            userDao.insert(user)
        }
        userDao.clearAll()

        for(i in 1..10000){
            val userName = "testUser$i"
            assertNull(userDao.get(userName))
        }
    }

    @Test
    @Throws(Exception::class)
    fun update10000Users() = runBlocking {

        for(i in 1..10000){
            val userName = "testUser$i"
            val user = Profile(userName, null, null, null, null, null, null, null, null, null,null,null, null, byteArrayOf())
            userDao.insert(user)
        }
        for(i in 1..10000){
            val userName = "testUser$i"
            val updatedHeight = i.toString()
            val user = userDao.get(userName)
            user!!.height = updatedHeight
            userDao.update(user)
            val userUp = userDao.get(userName)
            assertEquals(updatedHeight, userUp!!.height)
        }
    }
    }

