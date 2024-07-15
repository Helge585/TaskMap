package com.kuznetsov.taskmap.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kuznetsov.taskmap.dao.MainGoalDao
import com.kuznetsov.taskmap.dao.SubGoalDao

class SubGoalViewModel(private val mainGoalDao: MainGoalDao,
                       private val subGoalDao: SubGoalDao,
                       private val mainGoalId: Long): ViewModel() {

    val mainGoal = mainGoalDao.get(mainGoalId)

    val subGoals = subGoalDao.getByMainGoalId(mainGoalId)

    private val _isNavigatedToSubGoalCreating = MutableLiveData<Boolean>()
    val isNavigatedToSubGoalCreating: LiveData<Boolean> get() = _isNavigatedToSubGoalCreating

    fun navigateToSubGoalCreating() {
        _isNavigatedToSubGoalCreating.value = true
    }

    fun afterNavigateToSubGoalCreating() {
        _isNavigatedToSubGoalCreating.value = false
    }
    fun mainGoalInfo(): String {
        return mainGoal.value.toString()
    }
}