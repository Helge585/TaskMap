package com.kuznetsov.taskmap

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MainGoalDao {
    @Insert
    suspend fun insert(mainGoal : MainGoal)

    @Update
    suspend fun update(mainGoal : MainGoal)

    @Delete
    suspend fun delete(mainGoal : MainGoal)

    @Query("SELECT * FROM main_goal_table WHERE id = :id")
    fun get(id : Long): LiveData<MainGoal>

    @Query("SELECT * FROM main_goal_table ORDER BY id DESC")
    fun getAll() : LiveData<List<MainGoal>>
}