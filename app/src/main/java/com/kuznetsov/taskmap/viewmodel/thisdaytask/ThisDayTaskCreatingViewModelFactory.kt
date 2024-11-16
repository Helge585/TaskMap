package com.kuznetsov.taskmap.viewmodel.thisdaytask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kuznetsov.taskmap.dao.SubGoalDao
import com.kuznetsov.taskmap.dao.ThisDayTaskDao
import com.kuznetsov.taskmap.viewmodel.subgoal.SubGoalCreatingViewModel

class ThisDayTaskCreatingViewModelFactory(private val dao: ThisDayTaskDao): ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThisDayTaskCreatingViewModel::class.java)) {
            return ThisDayTaskCreatingViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}