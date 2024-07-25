package com.kuznetsov.taskmap.viewmodel.subgoal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kuznetsov.taskmap.dao.SubGoalDao

class SubGoalCreatingViewModelFactory(private val mainGoal: Long,
                                      private val dao: SubGoalDao): ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SubGoalCreatingViewModel::class.java)) {
            return SubGoalCreatingViewModel(mainGoal, dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}