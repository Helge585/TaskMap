package com.kuznetsov.taskmap.viewmodel.step

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuznetsov.taskmap.dao.StepDao
import com.kuznetsov.taskmap.dao.SubGoalDao
import com.kuznetsov.taskmap.entity.Step
import kotlinx.coroutines.launch

class StepsShowingViewModel(private val stepDao: StepDao,
                            private val subGoalDao: SubGoalDao,
                            private val subGoalId: Long): ViewModel() {
    val NOT_CHOOSEN_FOR_EDITING = -1L

    val steps = stepDao.getAllByStepId(subGoalId)
    val subGoal = subGoalDao.get(subGoalId)

    private val _isNavigatedToStepCreating = MutableLiveData<Boolean>()
    val isNavigatedToStepCreating: LiveData<Boolean> get() = _isNavigatedToStepCreating

    private val _isNavigatedToStepEditing = MutableLiveData<Long>()
    val isNavigatedToStepEditing: LiveData<Long> get() = _isNavigatedToStepEditing

    init {
        _isNavigatedToStepEditing.value = NOT_CHOOSEN_FOR_EDITING
        _isNavigatedToStepCreating.value = false
    }

    fun navigateToStepCreating() {
        _isNavigatedToStepCreating.value = true
    }

    fun afterNavigateToStepCreating() {
        _isNavigatedToStepCreating.value = false
    }

    fun navigateToStepEditing(stepId: Long) {
        _isNavigatedToStepEditing.value = stepId
    }

    fun afterNavigateToStepEditing() {
        _isNavigatedToStepEditing.value = NOT_CHOOSEN_FOR_EDITING
    }

    fun getPercentText(currentResult: Long, finishResult: Long): String {
        return String.format("%.2f", currentResult.toDouble() / finishResult * 100) +
                "%\n($currentResult from $finishResult)"
    }

    fun updateStep(step: Step) {
        viewModelScope.launch {
            stepDao.update(step)
        }
    }
}