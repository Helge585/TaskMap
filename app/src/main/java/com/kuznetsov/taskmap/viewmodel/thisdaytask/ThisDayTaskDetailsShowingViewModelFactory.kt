package com.kuznetsov.taskmap.viewmodel.thisdaytask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kuznetsov.taskmap.dao.GoalDatabase
import com.kuznetsov.taskmap.dao.ThisDayTaskDao


class ThisDayTaskDetailsShowingViewModelFactory(private val taskId: Long,
                                                private val db: GoalDatabase): ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThisDayTaskDetailsShowingViewModel::class.java)) {
            return ThisDayTaskDetailsShowingViewModel(taskId, db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}