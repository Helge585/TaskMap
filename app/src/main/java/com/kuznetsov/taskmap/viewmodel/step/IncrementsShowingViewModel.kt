package com.kuznetsov.taskmap.viewmodel.step

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuznetsov.taskmap.dao.StepDao
import com.kuznetsov.taskmap.entity.Increment
import kotlinx.coroutines.launch

class IncrementsShowingViewModel(val stepDao: StepDao, val stepId: Long): ViewModel() {

    val increments = stepDao.getAllIncrementsByStepId(stepId)
    val step = stepDao.get(stepId)


    fun getStepInfo(): String {
        return step.value?.name.toString() + " - " + step.value?.currentResult
    }

    fun deleteIncrement(increment: Increment) {
        step.value?.let {
            viewModelScope.launch {
                it.currentResult -= increment.incrementValue
                stepDao.deleteIncrement(increment)
                stepDao.update(it)
            }
        }
    }
}