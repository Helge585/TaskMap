package com.kuznetsov.taskmap

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class EditMainGoalViewModel(val dao: MainGoalDao, val mainGoalId: Long): ViewModel() {

    var newName = ""
//    private var mainGoal: MainGoal? = null
//    init {
//         viewModelScope.launch {
//             mainGoal = dao.getNoLiveData(mainGoalId)
//         }
//    }

    val mainGoal = dao.get(mainGoalId)

    val mainGoals = dao.getAll()

    fun info(): String {
        Log.i("EditMainGoalViewModel", "dao is ${dao.toString()}")
        return mainGoal.value?.toString() + " ! " + dao.toString() + " ! "
    }

    fun printMainGoals() {
        if (mainGoals.value == null) {
            Log.i("EditMainGoalViewModel", "NULL NULL NULL NULL NULL")
        }
        mainGoals.value?.let {
            for (m in it) {
                Log.i("EditMainGoalViewModel", m.name)
            }
        }
    }

    fun update() {
        if (newName.length > 0) {
            val m = MainGoal(mainGoalId, newName)
            //Log.i("EditMainGoalViewModel", "UPDATE: ${m.toString()}")
            viewModelScope.launch {
                dao.update(m)
            }
        } else {
            //Log.i("EditMainGoalViewModel", "UPDATE ERROR UPDATE ERROR UPDATE ERROR")
        }
    }

    fun delete() {
        viewModelScope.launch {
            val m = dao.get(mainGoalId)
            Log.i("EditMainGoalViewModel", "Delete button's click result: ${m.value?.toString()}")
        }
    }
}