package com.kuznetsov.taskmap.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuznetsov.taskmap.dao.SubGoalDao
import com.kuznetsov.taskmap.entity.SubGoal
import kotlinx.coroutines.launch

class CreateSubGoalViewModel(val mainGoalId: Long, val dao: SubGoalDao) : ViewModel() {

    private val _isNavigatedToSubGoal = MutableLiveData<Boolean>()
    val isNavigatedToSubGoal: LiveData<Boolean> get() = _isNavigatedToSubGoal

    var subGoalName = ""

    init {
        _isNavigatedToSubGoal.value = false
    }

    fun navigateToSubGoal() {
        Log.i("CreateSubGoalViewModel", "navigateToSubGoal method has been launched")
        _isNavigatedToSubGoal.value = true
    }

    fun afterNavigateToSubGoal() {
        _isNavigatedToSubGoal.value = false
    }

    fun addSubGoal() {
        if (subGoalName.length > 0) {
            viewModelScope.launch {
                val subGoal = SubGoal(mainGoalId = mainGoalId, name = subGoalName)
                Log.i("CreateSubGoalViewModel", "insert ${subGoal.toString()}")
                dao.insert(subGoal)
            }
        } else {
            Log.i("CreateSubGoalViewModel", "subGoalName is $subGoalName}")
        }
    }
}