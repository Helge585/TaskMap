package com.kuznetsov.taskmap.viewmodel

import androidx.lifecycle.ViewModel
import com.kuznetsov.taskmap.dao.MainGoalDao
import com.kuznetsov.taskmap.dao.SubGoalDao

class SubGoalViewModel(private val mainGoalDao: MainGoalDao,
                       private val subGoalDao: SubGoalDao,
                       private val mainGoalId: Long): ViewModel() {

    val mainGoal = mainGoalDao.get(mainGoalId)

    val subGoals = subGoalDao.getByMainGoalId(mainGoalId)

    fun mainGoalInfo(): String {
        return mainGoal.value.toString()
    }
}