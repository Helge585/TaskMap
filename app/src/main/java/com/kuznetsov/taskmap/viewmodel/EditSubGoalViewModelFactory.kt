package com.kuznetsov.taskmap.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kuznetsov.taskmap.dao.SubGoalDao

class EditSubGoalViewModelFactory(private val subGoalId: Long,
                                  private val dao: SubGoalDao
): ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditSubGoalViewModel::class.java)) {
            return EditSubGoalViewModel(subGoalId, dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}