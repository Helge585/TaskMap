package com.kuznetsov.taskmap

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CreateMainGoalViewModelFactory(private val dao: MainGoalDao): ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateMainGoalViewModel::class.java)) {
            return CreateMainGoalViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}