package com.kuznetsov.taskmap.viewmodel.step

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuznetsov.taskmap.dao.StepDao
import com.kuznetsov.taskmap.dao.SubGoalDao
import com.kuznetsov.taskmap.entity.Increment
import com.kuznetsov.taskmap.entity.Step
import com.kuznetsov.taskmap.utils.MyDateUtils
import kotlinx.coroutines.launch

const val TAG = "StepsShowingViewModel"

class StepsShowingViewModel(private val stepDao: StepDao,
                            private val subGoalDao: SubGoalDao,
                            private val subGoalId: Long): ViewModel() {
    val NOT_CHOOSEN_FOR_NAVIGATING = -1L

    val steps = stepDao.getAllByStepId(subGoalId)
    val subGoal = subGoalDao.get(subGoalId)

    private val _isNavigatedToStepCreating = MutableLiveData<Boolean>()
    val isNavigatedToStepCreating: LiveData<Boolean> get() = _isNavigatedToStepCreating

    private val _isNavigatedToStepEditing = MutableLiveData<Long>()
    val isNavigatedToStepEditing: LiveData<Long> get() = _isNavigatedToStepEditing

    private val _isNavigatedToIncrementsShowing = MutableLiveData<Long>()
    val isNavigatedToIncrementsShowing: LiveData<Long> get() = _isNavigatedToIncrementsShowing

    init {
        _isNavigatedToStepEditing.value = NOT_CHOOSEN_FOR_NAVIGATING
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
        _isNavigatedToStepEditing.value = NOT_CHOOSEN_FOR_NAVIGATING
    }

    fun navigateToIncrementsShowing(stepId: Long) {
        _isNavigatedToIncrementsShowing.value = stepId
    }

    fun afterNavigateToIncrementsShowing() {
        _isNavigatedToIncrementsShowing.value = NOT_CHOOSEN_FOR_NAVIGATING
    }

    fun getPercentText(currentResult: Long, finishResult: Long): String {
        return String.format("%.2f", currentResult.toDouble() / finishResult * 100) +
                "%\n($currentResult from $finishResult)"
    }

    fun updateStep(step: Step, newCurrentResult: Long) {
        viewModelScope.launch {
            //Log.i(TAG, "Difference = ${ newCurrentResult - step.currentResult }")
            val incrementValue = newCurrentResult - step.currentResult
            step.currentResult = newCurrentResult
            stepDao.update(step)
            val increment = Increment(
                id = 0,
                stepId = step.id,
                incrementValue = incrementValue,
                creatingDate = MyDateUtils.getCurrentDateInMillis()
            )
            stepDao.insertIncrement(increment)
        }
    }

    fun subGoalInfo(): String {
        return subGoal.value?.name.toString()
    }
}