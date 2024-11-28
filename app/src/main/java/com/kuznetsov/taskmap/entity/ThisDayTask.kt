package com.kuznetsov.taskmap.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "this_day_task_table_2")
data class ThisDayTask(

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @ColumnInfo(name = "this_day_task_name", defaultValue = "-")
    var name: String = "",

    @ColumnInfo(name = "description", defaultValue = "-")
    var description: String = "",

    @ColumnInfo(name = "step_id")
    var stepId : Long = 0,

    @ColumnInfo(name = "status", defaultValue = "0")
    var status : Long = 0,

    @ColumnInfo(name = "creating_date", defaultValue = "0")
    var creatingDate : Long = 0,

    @ColumnInfo(name = "start_result", defaultValue = "0")
    var startResult: Long = 0,

    @ColumnInfo(name = "current_result", defaultValue = "0")
    var currentResult: Long = 0,

    @ColumnInfo(name = "finish_result", defaultValue = "0")
    var finishResult: Long = 0
) {
}