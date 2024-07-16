package com.kuznetsov.taskmap.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kuznetsov.taskmap.dao.SubGoalDao

class EditSubGoalViewModel(val subGoalId: Long, val dao: SubGoalDao): ViewModel() {

    val subGoal = dao.get(subGoalId)

    fun info(): String {
        return "SSSSSSS"
    }
}