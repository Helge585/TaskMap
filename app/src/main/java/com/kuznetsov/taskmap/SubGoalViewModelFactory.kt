package com.kuznetsov.taskmap

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SubGoalViewModelFactory(private val mainGoalDao: MainGoalDao,
                              private val subGoalDao: SubGoalDao,
                              private val mainGoalId: Long): ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SubGoalViewModel::class.java)) {
            return SubGoalViewModel(mainGoalDao, subGoalDao, mainGoalId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}