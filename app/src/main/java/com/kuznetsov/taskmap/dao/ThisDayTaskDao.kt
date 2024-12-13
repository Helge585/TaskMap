package com.kuznetsov.taskmap.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.kuznetsov.taskmap.entity.ThisDayGroup
import com.kuznetsov.taskmap.entity.ThisDayTask

@Dao
interface ThisDayTaskDao {
    @Insert
    suspend fun insertTask(thisDayTask: ThisDayTask)

    @Update
    suspend fun updateTask(thisDayTask: ThisDayTask)

    @Delete
    suspend fun deleteTask(thisDayTask: ThisDayTask)

    @Insert
    suspend fun insertGroup(thisDayGroup: ThisDayGroup)

    @Update
    suspend fun updateGroup(thisDayGroup: ThisDayGroup)

    @Delete
    suspend fun deleteGroup(thisDayGroup: ThisDayGroup)

    @Query("SELECT * FROM this_day_task_table_2 WHERE id = :id")
    fun getTask(id : Long): LiveData<ThisDayTask>

    @Query("SELECT * FROM this_day_task_table_2 ORDER BY id DESC")
    fun getAllTasks() : LiveData<List<ThisDayTask>>

    @Query("SELECT * FROM this_day_task_table_2 WHERE group_id <= 0")
    fun getAllIndependentTasks() : LiveData<List<ThisDayTask>>

    @Query("SELECT * FROM this_day_task_table_2 WHERE group_id = :id")
    fun getTasksByGroupId(id : Long) : LiveData<List<ThisDayTask>>

    @Query("SELECT * FROM this_day_group_table WHERE id = :id")
    fun getGroup(id : Long): LiveData<ThisDayGroup>

    @Query("SELECT * FROM this_day_group_table ORDER BY id DESC")
    fun getAllGroups() : LiveData<List<ThisDayGroup>>
}