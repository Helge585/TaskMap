package com.kuznetsov.taskmap

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CreateMainGoalViewModel(val dao: MainGoalDao): ViewModel() {
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
}