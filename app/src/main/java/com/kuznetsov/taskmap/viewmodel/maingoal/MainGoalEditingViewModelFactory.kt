package com.kuznetsov.taskmap.viewmodel.maingoal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kuznetsov.taskmap.dao.MainGoalDao

class MainGoalEditingViewModelFactory(private val dao: MainGoalDao, private val mainGoalId: Long)
    : ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainGoalEditingViewModel::class.java)) {
            return MainGoalEditingViewModel(dao, mainGoalId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}