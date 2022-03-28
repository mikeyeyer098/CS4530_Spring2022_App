package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Insert
    fun insert(user : Profile)

    @Update
    fun update(user: Profile)

    @Query("SELECT * FROM Users WHERE Users.name = :userName")
    fun get(userName : String) : Profile?

    @Query("DELETE FROM Users")
    fun clearAll()

    @Query("DELETE FROM Users WHERE Users.name= :userName")
    fun deleteUser(userName : String)

    @Query("SELECT * FROM Users")
    fun getAllUsers(): LiveData<List<Profile>>

}