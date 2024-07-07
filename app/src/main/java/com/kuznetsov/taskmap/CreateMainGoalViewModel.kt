package com.kuznetsov.taskmap

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreateMainGoalViewModel(val dao: MainGoalDao): ViewModel() {
    private val _navigateToMainGoal = MutableLiveData<Boolean>()
    val navigateToMainGoal: LiveData<Boolean> get() = _navigateToMainGoal

    init {
        _navigateToMainGoal.value = false
    }

    fun afterNavigateToMainGoal() {
        _navigateToMainGoal.value = true
    }

    fun beforeNavigateToMainGoal() {
        _navigateToMainGoal.value = false
    }
}