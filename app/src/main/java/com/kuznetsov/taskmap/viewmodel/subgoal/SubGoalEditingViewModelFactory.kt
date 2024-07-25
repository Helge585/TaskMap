package com.kuznetsov.taskmap.viewmodel.subgoal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kuznetsov.taskmap.dao.SubGoalDao

class SubGoalEditingViewModelFactory(private val subGoalId: Long,
                                     private val dao: SubGoalDao
): ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SubGoalEditingViewModel::class.java)) {
            return SubGoalEditingViewModel(subGoalId, dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}