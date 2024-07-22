package com.kuznetsov.taskmap.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kuznetsov.taskmap.dao.MainGoalDao
import com.kuznetsov.taskmap.dao.StepDao
import com.kuznetsov.taskmap.dao.SubGoalDao

class StepViewModelFactory(private val stepDao: StepDao,
                           private val subGoalDao: SubGoalDao,
                           private val subGoalId: Long): ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StepViewModel::class.java)) {
            return StepViewModel(stepDao, subGoalDao, subGoalId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}