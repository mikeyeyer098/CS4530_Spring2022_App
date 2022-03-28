package com.example.myapplication

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.Assert.assertEquals
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
            val user = Profile("testUser", null, null, null, null, null, null, null, null, null,null,null, null, null)
            userDao.insert(user)
            val gotUser = userDao.get("testUser")
            assertEquals(gotUser!!.name, "testUser")
        }
    }

