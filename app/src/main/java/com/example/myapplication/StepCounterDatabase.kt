package com.example.myapplication

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [StepCountData::class], version = 1, exportSchema = false )
abstract class StepCounterDatabase : RoomDatabase(){
    abstract val stepCounterDao : StepCounterDao
    companion object {
        @Volatile
        private var INSTANCE: StepCounterDatabase? = null
        fun getInstance(context: Context): StepCounterDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        StepCounterDatabase::class.java,
                        "Step_Data"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}