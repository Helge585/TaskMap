package com.kuznetsov.taskmap.viewmodel.subgoal

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuznetsov.taskmap.dao.SubGoalDao
import com.kuznetsov.taskmap.entity.SubGoal
import com.kuznetsov.taskmap.utils.MyDateUtils
import kotlinx.coroutines.launch

class SubGoalCreatingViewModel(val mainGoalId: Long, val dao: SubGoalDao) : ViewModel() {

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
                val subGoal = SubGoal(
                        mainGoalId = mainGoalId,
                        name = subGoalName,
                        creatingDate = MyDateUtils.getCurrentDateInMillis())
                Log.i("CreateSubGoalViewModel", "insert ${subGoal.toString()}")
                dao.insert(subGoal)
            }
        } else {
            Log.i("CreateSubGoalViewModel", "subGoalName is $subGoalName}")
        }
    }
}