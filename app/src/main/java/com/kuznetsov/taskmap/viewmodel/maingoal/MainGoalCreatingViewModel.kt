package com.kuznetsov.taskmap.viewmodel.maingoal

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuznetsov.taskmap.entity.MainGoal
import com.kuznetsov.taskmap.dao.MainGoalDao
import kotlinx.coroutines.launch

class MainGoalCreatingViewModel(val dao: MainGoalDao): ViewModel() {
    val mainGoals = dao.getAll()

    private val _navigateToMainGoal = MutableLiveData<Boolean>()
    var mainGoalName = ""
    val navigateToMainGoal: LiveData<Boolean> get() = _navigateToMainGoal

    init {
        _navigateToMainGoal.value = false
    }

    fun navigateToMainGoal() {
        _navigateToMainGoal.value = true
    }

    fun afterNavigateToMainGoal() {
        _navigateToMainGoal.value = false
    }

    fun addMainGoal() {
        if (mainGoalName.length > 0) {
            viewModelScope.launch {
                val mainGoal = MainGoal()
                mainGoal.name = mainGoalName
                dao.insert(mainGoal)
            }
        }
    }

    fun printMainGoals() {
        if (mainGoals.value == null) {
            Log.i("CreateMainGoalViewModel", "NULL NULL NULL NULL NULL")
        }
        mainGoals.value?.let {
            for (m in it) {
                Log.i("CreateMainGoalViewModel", m.name)
            }
        }
    }
}