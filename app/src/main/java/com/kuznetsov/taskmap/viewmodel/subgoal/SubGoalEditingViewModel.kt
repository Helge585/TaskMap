package com.kuznetsov.taskmap.viewmodel.subgoal

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuznetsov.taskmap.dao.SubGoalDao
import kotlinx.coroutines.launch

class SubGoalEditingViewModel(val subGoalId: Long, val dao: SubGoalDao): ViewModel() {

    val subGoal = dao.get(subGoalId)

    var newSubGoalName = ""

    private val _isNavigatedToSubGoal = MutableLiveData<Boolean>()
    val isNavigatedToSubGoal: LiveData<Boolean> get() = _isNavigatedToSubGoal

    init {
        _isNavigatedToSubGoal.value = false
    }

    fun navigateToSubGoal() {
        _isNavigatedToSubGoal.value = true
    }

    fun afterNavigateToSubGoal() {
        _isNavigatedToSubGoal.value = false
    }

    fun update() {
        if (newSubGoalName.length > 0) {
            subGoal.value?.let {
                it.name = newSubGoalName
                viewModelScope.launch {
                    dao.update(it)
                }
            }
        }
    }

    fun delete() {
        subGoal.value?.let {
            viewModelScope.launch {
                dao.delete(it)
            }
            navigateToSubGoal()
        }
    }

    fun info(): String {
        return "SSSSSSS"
    }

    fun logNewName() {
        Log.i("EditSubGoal", "!!!!!             $newSubGoalName")
    }
}