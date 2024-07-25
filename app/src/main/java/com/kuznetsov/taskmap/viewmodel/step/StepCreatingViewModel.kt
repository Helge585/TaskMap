package com.kuznetsov.taskmap.viewmodel.step

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kuznetsov.taskmap.dao.StepDao

class StepCreatingViewModel(val stepDao: StepDao, val sunGoalId: Long): ViewModel() {

    var stepName = MutableLiveData<String>()

    private val _isNavigatedToStepsShowing = MutableLiveData<Boolean>()
    val isNavigatedToStepsShowing: LiveData<Boolean> get() = _isNavigatedToStepsShowing

    init {
        stepName.value = ""
        _isNavigatedToStepsShowing.value = false
    }

    fun clearStepName() {
        stepName.value = ""
    }

    fun navigateToStepsShowing() {
        _isNavigatedToStepsShowing.value = true
    }

    fun afterNavigateToStepsShowing() {
        _isNavigatedToStepsShowing.value = false
    }
}