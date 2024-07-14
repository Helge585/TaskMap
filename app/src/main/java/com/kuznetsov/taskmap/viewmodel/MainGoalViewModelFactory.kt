package com.kuznetsov.taskmap.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kuznetsov.taskmap.dao.MainGoalDao

class MainGoalViewModelFactory(private val dao: MainGoalDao): ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainGoalViewModel::class.java)) {
            return MainGoalViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}