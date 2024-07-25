package com.kuznetsov.taskmap.viewmodel.step

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kuznetsov.taskmap.dao.StepDao
import com.kuznetsov.taskmap.dao.SubGoalDao

class StepsShowingViewModel(private val stepDao: StepDao,
                            private val subGoalDao: SubGoalDao,
                            private val subGoalId: Long): ViewModel() {

    val steps = stepDao.getAllByStepId(subGoalId)
    val subGoal = subGoalDao.get(subGoalId)

    private val _isNavigatedToStepCreating = MutableLiveData<Boolean>()
    val isNavigatedToStepCreating: LiveData<Boolean> get() = _isNavigatedToStepCreating

    fun navigateToStepCreating() {
        _isNavigatedToStepCreating.value = true
    }

    fun afterNavigateToStepCreating() {
        _isNavigatedToStepCreating.value = false
    }
}