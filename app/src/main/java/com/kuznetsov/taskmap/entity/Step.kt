package com.kuznetsov.taskmap.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "step_table",
    foreignKeys = [
        ForeignKey(
            entity = SubGoal::class,
            parentColumns = ["id"],
            childColumns = ["sub_goal_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Step(

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @ColumnInfo(name = "sub_goal_id")
    var subGoalId: Long = 0,

    @ColumnInfo(name = "step_name")
    var name: String = "",

    @ColumnInfo(name = "current_result")
    var currentResult: Long = 0,

    @ColumnInfo(name = "finish_result")
    var finishResult: Long = 0
)