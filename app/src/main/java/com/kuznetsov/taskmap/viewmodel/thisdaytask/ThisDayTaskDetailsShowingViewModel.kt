package com.kuznetsov.taskmap.viewmodel.thisdaytask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.kuznetsov.taskmap.dao.GoalDatabase
import com.kuznetsov.taskmap.entity.Increment
import com.kuznetsov.taskmap.entity.Step
import com.kuznetsov.taskmap.entity.ThisDayTask
import com.kuznetsov.taskmap.utils.MyDateUtils
import kotlinx.coroutines.launch

class ThisDayTaskDetailsShowingViewModel(val taskId: Long, private val db: GoalDatabase): ViewModel() {

    val task = db.thisDayTaskDao.get(taskId)

    val step = task.switchMap {
        db.stepDao.get(it.stepId)
    }

    fun deleteThisDayTask() {
        task.value?.let {
            viewModelScope.launch {
                db.thisDayTaskDao.delete(it)
            }
        }
    }

    fun updateThisDayTask(name: String, description: String) {
        //Log.i("ThisDayTasksShowingViewModel", "Updating")
        task.value?.let {
            it.name = name
            it.description = description
            viewModelScope.launch {
                db.thisDayTaskDao.update(it)
            }
        }

    }

    fun updateStep(newCurrentResult: Long) {
        step.value?.let {
            viewModelScope.launch {
                //Log.i(TAG, "Difference = ${ newCurrentResult - step.currentResult }")
                val incrementValue = newCurrentResult - it.currentResult
                it.currentResult = newCurrentResult
                db.stepDao.update(it)
                val increment = Increment(
                    id = 0,
                    stepId = it.id,
                    incrementValue = incrementValue,
                    creatingDate = MyDateUtils.getCurrentDateInMillis()
                )
                db.stepDao.insertIncrement(increment)
            }
        }
    }
}