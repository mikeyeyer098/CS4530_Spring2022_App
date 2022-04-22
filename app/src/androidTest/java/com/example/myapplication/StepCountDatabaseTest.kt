package com.example.myapplication

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.Assert
import junit.framework.Assert.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class StepCountDatabaseTest {

    private lateinit var stepCountDao: StepCounterDao
    private lateinit var db: StepCounterDatabase

    @Before
    fun createDb() {
        val context : Context = ApplicationProvider.getApplicationContext()
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, StepCounterDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        stepCountDao = db.stepCounterDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetStepData() = runBlocking {
        val stepData = StepCountData("test", "10-04-2020", "10000")
        stepCountDao.insert(stepData)
        val gotData = stepCountDao.get("test")
        Assert.assertEquals(gotData!!.first().name, "test")
        Assert.assertEquals(gotData!!.first().date, "10-04-2020")
        Assert.assertEquals(gotData!!.first().stepCount, "10000")

    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetStepDatas(): Unit = runBlocking {
        val stepData1 = StepCountData("test1", "10-04-2020", "10000")
        val stepData2 = StepCountData("test1", "10-05-2020", "10000")
        val stepData3 = StepCountData("test1", "10-06-2020", "10000")

        stepCountDao.insert(stepData1)
        stepCountDao.insert(stepData2)
        stepCountDao.insert(stepData3)

        val gotData = stepCountDao.get("test1")
        var count = 1;
        gotData?.forEach{
            Assert.assertEquals(it.name, "test1")
            Assert.assertEquals(it.stepCount, "10000")
            count++
        }
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetMultipleUsersData(): Unit = runBlocking {
        val stepData1 = StepCountData("test1", "10-04-2020", "10000")
        val stepData2 = StepCountData("test2", "10-04-2020", "10000")
        val stepData3 = StepCountData("test3", "10-04-2020", "10000")

        stepCountDao.insert(stepData1)
        stepCountDao.insert(stepData2)
        stepCountDao.insert(stepData3)

        val gotData1 = stepCountDao.get("test1")
        Assert.assertEquals(gotData1?.first()!!.name, "test1")
        Assert.assertEquals(gotData1?.first().stepCount, "10000")

        val gotData2 = stepCountDao.get("test2")
        Assert.assertEquals(gotData2?.first()!!.name, "test2")
        Assert.assertEquals(gotData2?.first()!!.stepCount, "10000")

        val gotData3 = stepCountDao.get("test3")
        Assert.assertEquals(gotData3?.first()!!.name, "test3")
        Assert.assertEquals(gotData3?.first().stepCount, "10000")

    }

    @Test
    @Throws(Exception::class)
    fun deleteStepData(): Unit = runBlocking {
        val stepData1 = StepCountData("test1", "10-04-2020", "10000")
        val stepData2 = StepCountData("test1", "10-05-2020", "10000")
        val stepData3 = StepCountData("test1", "10-06-2020", "10000")

        stepCountDao.insert(stepData1)
        stepCountDao.insert(stepData2)
        stepCountDao.insert(stepData3)

        stepCountDao.deleteStepData("test1")

        assertTrue(stepCountDao.get("test1").isNullOrEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun updateStepData(): Unit = runBlocking {
        val stepData1 = StepCountData("test1", "10-04-2020", "10000")

        stepCountDao.insert(stepData1)

        val stepData2 = StepCountData("test1", "10-04-2020", "15000")

        stepCountDao.update(stepData2)
        val gotData1 = stepCountDao.get("test1")
        Assert.assertEquals(gotData1?.first()!!.name, "test1")
        Assert.assertEquals(gotData1?.first().stepCount, "15000")

        val stepData3 = StepCountData("test1", "10-04-2020", "20000")

        stepCountDao.update(stepData3)

        val gotData2 = stepCountDao.get("test1")
        Assert.assertEquals(gotData2?.first()!!.name, "test1")
        Assert.assertEquals(gotData2?.first().stepCount, "20000")
    }

    @Test
    @Throws(Exception::class)
    fun deleteAllStepData(): Unit = runBlocking {
        val stepData1 = StepCountData("test1", "10-04-2020", "10000")
        val stepData2 = StepCountData("test2", "10-04-2020", "10000")
        val stepData3 = StepCountData("test3", "10-04-2020", "10000")

        stepCountDao.insert(stepData1)
        stepCountDao.insert(stepData2)
        stepCountDao.insert(stepData3)

        stepCountDao.clearAll()

        assertTrue(stepCountDao.get("test1").isNullOrEmpty())
        assertTrue(stepCountDao.get("test2").isNullOrEmpty())
        assertTrue(stepCountDao.get("test3").isNullOrEmpty())

    }

    @Test
    @Throws(Exception::class)
    fun existsStepData(): Unit = runBlocking {
        val stepData1 = StepCountData("test1", "10-04-2020", "10000")

        stepCountDao.insert(stepData1)

        assertTrue(stepCountDao.exists("test1"))
        assertFalse(stepCountDao.exists("test2"))

    }

    @Test
    @Throws(Exception::class)
    fun insert10000Users() = runBlocking {

        for(i in 1..10000){
            val userName = "testUser$i"
            val stepData = StepCountData(userName, "10-04-2020","1000")
            stepCountDao.insert(stepData)
        }
        for(i in 1..10000){
            val userName = "testUser$i"
            val gotUser = stepCountDao.get(userName)
            assertEquals(gotUser!!.first().name, userName)
        }
    }

    @Test
    @Throws(Exception::class)
    fun delete10000Users() = runBlocking {

        for(i in 1..10000){
            val userName = "testUser$i"
            val stepData = StepCountData(userName, "10-04-2020","1000")
            stepCountDao.insert(stepData)
        }
        for(i in 1..10000){
            val userName = "testUser$i"
            stepCountDao.deleteStepData(userName)
            assertTrue(stepCountDao.get(userName).isNullOrEmpty())
        }
    }
}