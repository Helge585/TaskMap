package com.kuznetsov.taskmap

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

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
    fun getAll() : LiveData<List<Step>>
}