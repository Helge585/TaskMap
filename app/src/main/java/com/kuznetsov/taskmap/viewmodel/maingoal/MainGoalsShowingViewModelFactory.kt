package com.kuznetsov.taskmap.viewmodel.maingoal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kuznetsov.taskmap.dao.MainGoalDao

class MainGoalsShowingViewModelFactory(private val dao: MainGoalDao): ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainGoalsShowingViewModel::class.java)) {
            return MainGoalsShowingViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}