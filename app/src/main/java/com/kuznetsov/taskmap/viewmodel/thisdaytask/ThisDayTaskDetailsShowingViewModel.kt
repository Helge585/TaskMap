package com.kuznetsov.taskmap.viewmodel.thisdaytask

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.kuznetsov.taskmap.dao.GoalDatabase
import com.kuznetsov.taskmap.entity.Increment
import com.kuznetsov.taskmap.entity.Step
import com.kuznetsov.taskmap.utils.MyDateUtils
import kotlinx.coroutines.launch

class ThisDayTaskDetailsShowingViewModel(val taskId: Long, private val db: GoalDatabase): ViewModel() {

    val task = db.thisDayTaskDao.getTask(taskId)

    val groups = db.thisDayTaskDao.getAllGroups()

    private var _step: LiveData<Step>? = null

    //val step = MutableLiveData<Step>(null)

    var step = task?.switchMap {
        Log.i("WTF", it.toString())
        if (it != null) {
            return@switchMap db.stepDao.get(it.stepId)
        }
        MutableLiveData<Step>()
    }

//    val step = MutableLiveData<Step>()
//    init {
//        task.observeForever {
//            it?.let {
//                Log.i("WTsssssF", it.toString())
//                step = db.stepDao.get(it.stepId)
//            }
//        }
//    }

    fun updateStep() {
        task.value?.let {
            _step = db.stepDao.get(it.stepId)
        }
    }
    fun deleteThisDayTask() {
        task.value?.let {
            viewModelScope.launch {
                db.thisDayTaskDao.deleteTask(it)
            }
        }
    }

    fun updateThisDayTask(name: String, description: String, startValue: Long,
                          currentValue: Long, finishValue: Long, groupName: String = "") {
        //Log.i("ThisDayTasksShowingViewModel", "Updating")
        var groupId = -2L;
        if (groupName == "" || groupName == "None") {
            groupId = -1L
        } else {
            groups.value?.let { groups ->
                groups.find { it.name == groupName }?.let { group -> groupId = group.id }
            }
        }
        task.value?.let {
            it.name = name
            it.description = description
            it.startResult = startValue
            it.currentResult = currentValue
            it.finishResult = finishValue
            it.groupId = groupId
            viewModelScope.launch {
                db.thisDayTaskDao.updateTask(it)
            }
        }

    }

    fun updateStep(newCurrentResult: Long) {
        step?.value?.let {
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