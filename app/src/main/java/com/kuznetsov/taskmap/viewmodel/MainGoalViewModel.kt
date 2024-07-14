package com.kuznetsov.taskmap.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kuznetsov.taskmap.dao.MainGoalDao
import com.kuznetsov.taskmap.entity.MainGoal

class MainGoalViewModel(val dao: MainGoalDao): ViewModel() {

    private val _isNavigatedToSubGoals = MutableLiveData<Boolean>()
    val isNavigatedToSubGoals get() = _isNavigatedToSubGoals

    private val _isNavigatedToCreating = MutableLiveData<Boolean>()
    val isNavigatedToCreating: LiveData<Boolean> get() = _isNavigatedToCreating

    private val _isNavigatedToEditing = MutableLiveData<Boolean>()
    val isNavigatedToEditing: LiveData<Boolean> get() = _isNavigatedToEditing

    private var _clickedMainGoal: MainGoal? = null
    val clickedMainGoal: MainGoal get() = _clickedMainGoal!!

    val mainGoals = dao.getAll()
    init {
        _isNavigatedToCreating.value = false
        _isNavigatedToEditing.value = false
        _isNavigatedToSubGoals.value = false
    }

    fun navigateToCreating() {
        _isNavigatedToCreating.value = true
    }

    fun afterNavigateToCreating() {
        _isNavigatedToCreating.value = false
    }

    fun navigateToEditing(mainGoal: MainGoal) {
        _clickedMainGoal = mainGoal
        _isNavigatedToEditing.value = true
    }

    fun navigateToSubGoals(mainGoal: MainGoal) {
        _clickedMainGoal = mainGoal
        _isNavigatedToSubGoals.value = true
    }

    fun afterNavigateToSubGoals() {
        _isNavigatedToSubGoals.value = false
    }

    fun afterNavigateToEditing() {
        _isNavigatedToEditing.value = false
        _clickedMainGoal = null
    }
}