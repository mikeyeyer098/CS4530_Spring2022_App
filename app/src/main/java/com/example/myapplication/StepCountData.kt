package com.example.myapplication
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Step_Data", primaryKeys = ["name", "steps_date"])
data class StepCountData (
    @ColumnInfo (name= "name")
    var name: String,
    @ColumnInfo(name= "steps_date")
    var date: String,
    @ColumnInfo(name= "step_count")
    var stepCount: String?,
)