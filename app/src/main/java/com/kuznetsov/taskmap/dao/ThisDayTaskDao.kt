package com.kuznetsov.taskmap.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.kuznetsov.taskmap.entity.SubGoal
import com.kuznetsov.taskmap.entity.ThisDayTask

@Dao
interface ThisDayTaskDao {
    @Insert
    suspend fun insert(thisDayTask: ThisDayTask)

    @Update
    suspend fun update(thisDayTask: ThisDayTask)

    @Delete
    suspend fun delete(thisDayTask: ThisDayTask)

    @Query("SELECT * FROM this_day_task_table_2 WHERE id = :id")
    fun get(id : Long): LiveData<ThisDayTask>

    @Query("SELECT * FROM this_day_task_table_2 ORDER BY id DESC")
    fun getAll() : LiveData<List<ThisDayTask>>

}