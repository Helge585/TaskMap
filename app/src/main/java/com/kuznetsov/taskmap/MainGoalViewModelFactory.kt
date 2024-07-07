package com.kuznetsov.taskmap

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainGoalViewModelFactory(private val dao: MainGoalDao): ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainGoalViewModel::class.java)) {
            return MainGoalViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}