package com.kuznetsov.taskmap.viewmodel.step

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuznetsov.taskmap.dao.StepDao
import com.kuznetsov.taskmap.entity.Step
import com.kuznetsov.taskmap.utils.MyDateUtils
import kotlinx.coroutines.launch

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

        val startResultLong: Long? = startResult.value?.toLongOrNull()

        val finishResultLong: Long? = finishResult.value?.toLongOrNull()

        if (stepNameString != null && stepNameString.length > 0
            && startResultLong != null && finishResultLong != null) {

            val step = Step(0, subGoalId, stepNameString, startResultLong,
                startResultLong, finishResultLong, MyDateUtils.getCurrentDateInMillis())
            //Log.i("ViewModel save method", "!!!!       $step")
            viewModelScope.launch {
                stepDao.insert(step)
            }
        }
    }
}