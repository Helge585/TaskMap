package com.kuznetsov.taskmap

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainGoalViewModel(val dao: MainGoalDao): ViewModel() {

    private val _navigateToCreating = MutableLiveData<Boolean>()
    val navigateToCreating: LiveData<Boolean> get() = _navigateToCreating

    private val _navigateToEditing = MutableLiveData<Boolean>()
    val navigateToEditing: LiveData<Boolean> get() = _navigateToEditing

    private var _clickedMainGoal: MainGoal? = null
    val clickedMainGoal: MainGoal get() = _clickedMainGoal!!

    val mainGoals = dao.getAll()
    init {
        _navigateToCreating.value = false
        _navigateToEditing.value = false
    }

    fun navigateToCreating() {
        _navigateToCreating.value = true
    }

    fun afterNavigateToCreating() {
        _navigateToCreating.value = false
    }

    fun navigateToEditing(mainGoal: MainGoal) {
        _clickedMainGoal = mainGoal
        _navigateToEditing.value = true
    }

    fun afterNavigateToEditing() {
        _navigateToEditing.value = false
        _clickedMainGoal = null
    }
}