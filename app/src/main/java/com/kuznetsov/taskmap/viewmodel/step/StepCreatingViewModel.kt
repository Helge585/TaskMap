package com.kuznetsov.taskmap.viewmodel.step

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kuznetsov.taskmap.dao.StepDao
import com.kuznetsov.taskmap.entity.Step

class StepCreatingViewModel(val stepDao: StepDao, val subGoalId: Long): ViewModel() {

    var stepName = MutableLiveData<String>()

    var startResult = MutableLiveData<String>()

    var finishResult = MutableLiveData<String>()

    private val _isNavigatedToStepsShowing = MutableLiveData<Boolean>()
    val isNavigatedToStepsShowing: LiveData<Boolean> get() = _isNavigatedToStepsShowing

    init {
        stepName.value = ""
        _isNavigatedToStepsShowing.value = false
    }

    fun clearStepName() {
        stepName.value = ""
        startResult.value = ""
        finishResult.value = ""
    }

    fun navigateToStepsShowing() {
        _isNavigatedToStepsShowing.value = true
    }

    fun afterNavigateToStepsShowing() {
        _isNavigatedToStepsShowing.value = false
    }

    fun saveStep() {
        var stepNameString = stepName.value
        var startResultLong: Long? = null
        startResult.value?.let {
            startResultLong = it.toLongOrNull()
        }
        var finishResultLong: Long? = null
        finishResult.value?.let {
            finishResultLong = it.toLongOrNull()
        }
        if (stepNameString != null && stepNameString.length > 0
            && startResultLong != null && finishResultLong != null) {

            //val step = Step(0, subGoalId, stepNameString, startResultLong, finishResultLong)
        }
        //Log.i("ViewModel save method", "!!!!       $startResultInt")
    }
}