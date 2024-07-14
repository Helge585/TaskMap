package com.kuznetsov.taskmap.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kuznetsov.taskmap.dao.MainGoalDao

class CreateMainGoalViewModelFactory(private val dao: MainGoalDao): ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateMainGoalViewModel::class.java)) {
            return CreateMainGoalViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}