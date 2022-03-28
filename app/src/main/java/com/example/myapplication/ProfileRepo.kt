package com.example.myapplication

import android.content.Context

class ProfileRepo (){
    lateinit var userDao: UserDao
    lateinit var db: UserDatabase

    fun createDb(context: Context) {
        db = UserDatabase.getInstance(context)
        userDao = db.userDao
    }

    suspend fun createProfile(profile: Profile) {
        userDao.insert(profile)
    }

    suspend fun updateProfile(profile: Profile) {
        userDao.update(profile)
    }
}