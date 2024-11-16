package com.kuznetsov.taskmap.viewmodel.step

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kuznetsov.taskmap.dao.GoalDatabase
import com.kuznetsov.taskmap.dao.StepDao
import com.kuznetsov.taskmap.dao.SubGoalDao

class OneStepShowingViewModelFactory(private val db: GoalDatabase,
                                        private val stepId: Long): ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OneStepShowingViewModel::class.java)) {
            return OneStepShowingViewModel(db, stepId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}