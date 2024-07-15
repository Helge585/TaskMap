package com.kuznetsov.taskmap.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kuznetsov.taskmap.dao.SubGoalDao

class CreateSubGoalViewModelFactory(private val mainGoal: Long,
                                    private val dao: SubGoalDao): ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateSubGoalViewModel::class.java)) {
            return CreateSubGoalViewModel(mainGoal, dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}