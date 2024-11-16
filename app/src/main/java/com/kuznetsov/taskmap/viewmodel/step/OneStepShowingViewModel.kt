package com.kuznetsov.taskmap.viewmodel.step

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuznetsov.taskmap.dao.GoalDatabase
import com.kuznetsov.taskmap.dao.StepDao
import com.kuznetsov.taskmap.entity.Increment
import com.kuznetsov.taskmap.entity.Step
import com.kuznetsov.taskmap.utils.MyDateUtils
import kotlinx.coroutines.launch

class OneStepShowingViewModel(val db: GoalDatabase, stepId: Long): ViewModel() {

    val step = db.stepDao.get(stepId)

    val increments = db.stepDao.getAllIncrementsByStepId(stepId)

    fun getPercentText(currentResult: Long, finishResult: Long): String {
        return String.format("%.2f", currentResult.toDouble() / finishResult * 100) +
                "%\n($currentResult from $finishResult)"
    }

    fun updateStep(step: Step, newCurrentResult: Long) {
        viewModelScope.launch {
            //Log.i(TAG, "Difference = ${ newCurrentResult - step.currentResult }")
            val incrementValue = newCurrentResult - step.currentResult
            step.currentResult = newCurrentResult
            db.stepDao.update(step)
            val increment = Increment(
                id = 0,
                stepId = step.id,
                incrementValue = incrementValue,
                creatingDate = MyDateUtils.getCurrentDateInMillis()
            )
            db.stepDao.insertIncrement(increment)
        }
    }

    fun deleteIncrement(increment: Increment) {
        step.value?.let {
            viewModelScope.launch {
                it.currentResult -= increment.incrementValue
                db.stepDao.deleteIncrement(increment)
                db.stepDao.update(it)
            }
        }
    }
}