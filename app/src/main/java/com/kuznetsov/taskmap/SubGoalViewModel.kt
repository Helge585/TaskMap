package com.kuznetsov.taskmap

import androidx.lifecycle.ViewModel

class SubGoalViewModel(private val mainGoalDao: MainGoalDao,
                       private val subGoalDao: SubGoalDao,
                       private val mainGoalId: Long): ViewModel() {

    val mainGoal = mainGoalDao.get(mainGoalId)

    val subGoals = subGoalDao.getByMainGoalId(mainGoalId)

    fun mainGoalInfo(): String {
        return mainGoal.value.toString()
    }
}