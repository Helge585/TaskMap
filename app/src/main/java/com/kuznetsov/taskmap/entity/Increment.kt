package com.kuznetsov.taskmap.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "increment_table",
    foreignKeys = [
        ForeignKey(
            entity = Step::class,
            parentColumns = ["id"],
            childColumns = ["step_id"],
            onDelete = ForeignKey.CASCADE
        )
    ])

data class Increment(

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @ColumnInfo(name = "step_id")
    var stepId : Long = 0,

    @ColumnInfo(name = "increment_value", defaultValue = "0")
    var incrementValue: Long = 0,

    @ColumnInfo(name = "creating_date", defaultValue = "0")
    var creatingDate : Long = 0
)