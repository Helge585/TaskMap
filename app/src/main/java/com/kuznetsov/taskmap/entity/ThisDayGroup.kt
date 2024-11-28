package com.kuznetsov.taskmap.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "this_day_group_table")
data class ThisDayGroup(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @ColumnInfo(name = "this_day_group_name", defaultValue = "-")
    var name: String = ""
) {

}