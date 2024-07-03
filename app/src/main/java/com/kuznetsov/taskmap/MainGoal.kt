package com.kuznetsov.taskmap

import androidx.room.Entity

@Entity(tableName = "main_task_table")
data class MainGoal(
    var taskId: Long = 0,
    var taskName: String = ""
)