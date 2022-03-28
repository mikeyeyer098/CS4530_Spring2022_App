package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user : Profile)

    @Update
    suspend fun update(user: Profile)

    @Query("SELECT * FROM Users WHERE Users.name = :userName")
    suspend fun get(userName : String) : Profile?

    @Query("DELETE FROM Users")
    suspend fun clearAll()

    @Query("DELETE FROM Users WHERE Users.name= :userName")
    suspend fun deleteUser(userName : String)
}