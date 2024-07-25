package com.kuznetsov.taskmap.viewmodel.subgoal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kuznetsov.taskmap.dao.MainGoalDao
import com.kuznetsov.taskmap.dao.SubGoalDao

class SubGoalsShowingViewModelFactory(private val mainGoalDao: MainGoalDao,
                                      private val subGoalDao: SubGoalDao,
                                      private val mainGoalId: Long): ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SubGoalsShowingViewModel::class.java)) {
            return SubGoalsShowingViewModel(mainGoalDao, subGoalDao, mainGoalId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}