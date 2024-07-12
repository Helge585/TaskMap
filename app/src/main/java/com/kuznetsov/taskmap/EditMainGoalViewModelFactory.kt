package com.kuznetsov.taskmap

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EditMainGoalViewModelFactory(private val dao: MainGoalDao, private val mainGoalId: Long)
    : ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditMainGoalViewModel::class.java)) {
            return EditMainGoalViewModel(dao, mainGoalId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}