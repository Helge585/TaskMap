package com.kuznetsov.taskmap.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "main_goal_table")
data class MainGoal(

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @ColumnInfo(name = "goal_name")
    var name: String = "",

    @ColumnInfo(name = "creating_date", defaultValue = "0")
    var creatingDate : Long = 0
)