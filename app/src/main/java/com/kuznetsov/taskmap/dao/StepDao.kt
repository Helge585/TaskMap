package com.kuznetsov.taskmap.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.kuznetsov.taskmap.entity.Step

@Dao
interface StepDao {
    @Insert
    suspend fun insert(step : Step)

    @Update
    suspend fun update(step : Step)

    @Delete
    suspend fun delete(step : Step)

    @Query("SELECT * FROM step_table WHERE id = :id")
    fun get(id : Long): LiveData<Step>

    @Query("SELECT * FROM step_table ORDER BY id DESC")
    fun getAll(): LiveData<List<Step>>

    @Query("SELECT * FROM step_table WHERE sub_goal_id = :subGoalId")
    fun getAllByStepId(subGoalId: Long): LiveData<List<Step>>
}