package com.kuznetsov.taskmap.viewmodel.maingoal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kuznetsov.taskmap.dao.MainGoalDao

class MainGoalCreatingViewModelFactory(private val dao: MainGoalDao): ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainGoalCreatingViewModel::class.java)) {
            return MainGoalCreatingViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}