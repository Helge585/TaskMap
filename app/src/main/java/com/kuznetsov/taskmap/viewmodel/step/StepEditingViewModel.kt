package com.kuznetsov.taskmap.viewmodel.step

import androidx.lifecycle.ViewModel
import com.kuznetsov.taskmap.dao.StepDao

class StepEditingViewModel(val stepDao: StepDao, val stepId: Long): ViewModel() {

    val step = stepDao.get(stepId)

    var newName = step.value?.name ?: "Error: not found"
    var newStartResult = step.value?.startResult.toString()
    var newCurrentResult = step.value?.currentResult.toString()
    var newFinishResult = step.value?.finishResult.toString()

}