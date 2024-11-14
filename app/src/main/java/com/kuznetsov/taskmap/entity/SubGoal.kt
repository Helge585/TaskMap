package com.kuznetsov.taskmap.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import com.kuznetsov.taskmap.entity.MainGoal
import com.kuznetsov.taskmap.utils.MyDateUtils

@Entity(
    tableName = "sub_goal_table",
    foreignKeys = [
        ForeignKey(
            entity = MainGoal::class,
            parentColumns = ["id"],
            childColumns = ["main_goal_id"],
            onDelete = CASCADE
        )
    ]
)
data class SubGoal(

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @ColumnInfo(name = "main_goal_id")
    var mainGoalId: Long = 0,

    @ColumnInfo(name = "goal_name")
    var name: String = "",

    @ColumnInfo(name = "creating_date", defaultValue = "0")
    var creatingDate : Long = 0
) {
    val creatingDateInString: String get() = MyDateUtils.millisToFormatDate(creatingDate)
}