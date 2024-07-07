package com.kuznetsov.taskmap

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SubGoalDao {
    @Insert
    suspend fun insert(subGoal : SubGoal)

    @Update
    suspend fun update(subGoal : SubGoal)

    @Delete
    suspend fun delete(subGoal : SubGoal)

    @Query("SELECT * FROM sub_goal_table WHERE id = :id")
    fun get(id : Long): LiveData<SubGoal>

    @Query("SELECT * FROM sub_goal_table ORDER BY id DESC")
    fun getAll() : LiveData<List<SubGoal>>
}