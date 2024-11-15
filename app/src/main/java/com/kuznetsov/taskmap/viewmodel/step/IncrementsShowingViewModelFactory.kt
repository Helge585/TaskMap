package com.kuznetsov.taskmap.viewmodel.step

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kuznetsov.taskmap.dao.StepDao
import com.kuznetsov.taskmap.dao.SubGoalDao

class IncrementsShowingViewModelFactory(private val stepDao: StepDao,
                                        private val stepId: Long): ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IncrementsShowingViewModel::class.java)) {
            return IncrementsShowingViewModel(stepDao, stepId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}