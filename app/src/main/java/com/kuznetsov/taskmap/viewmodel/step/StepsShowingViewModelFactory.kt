package com.kuznetsov.taskmap.viewmodel.step

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kuznetsov.taskmap.dao.StepDao
import com.kuznetsov.taskmap.dao.SubGoalDao
import com.kuznetsov.taskmap.dao.ThisDayTaskDao

class StepsShowingViewModelFactory(private val stepDao: StepDao,
                                   private val subGoalDao: SubGoalDao,
                                   private val thisDayTaskDao: ThisDayTaskDao,
                                   private val subGoalId: Long): ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StepsShowingViewModel::class.java)) {
            return StepsShowingViewModel(stepDao, subGoalDao, thisDayTaskDao, subGoalId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}