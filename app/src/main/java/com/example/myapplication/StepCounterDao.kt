package com.example.myapplication

import androidx.room.*

@Dao
interface StepCounterDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(stepData : StepCountData)

    @Update
    suspend fun update(stepData: StepCountData)

    @Query("SELECT * FROM Step_Data WHERE Step_Data.name = :userName")
    suspend fun get(userName : String) : StepCountData?

    @Query("DELETE FROM Step_Data")
    suspend fun clearAll()

    @Query("DELETE FROM Step_Data WHERE Step_Data.name= :userName")
    suspend fun deleteUser(userName : String)

}