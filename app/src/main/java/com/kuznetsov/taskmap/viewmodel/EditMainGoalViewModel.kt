package com.kuznetsov.taskmap.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuznetsov.taskmap.dao.MainGoalDao
import kotlinx.coroutines.launch

class EditMainGoalViewModel(val dao: MainGoalDao, val mainGoalId: Long): ViewModel() {

    var newName = ""

    val mainGoal = dao.get(mainGoalId)

    private val _isNavigateToMainGoal = MutableLiveData<Boolean>()
    val isNavigateToMainGoal get() = _isNavigateToMainGoal

    init {
        _isNavigateToMainGoal.value = false
    }

    fun navigateToMainGoal() {
        _isNavigateToMainGoal.value = true
    }

    fun afterNavigateToMainGoal() {
        _isNavigateToMainGoal.value = false
    }

    fun info(): String {
        return mainGoal.value.toString()
    }

    fun update() {
        mainGoal.value?.let {
            if (newName.length > 0) {
                it.name = newName
                viewModelScope.launch {
                    dao.update(it)
                }
            }
        }
    }

    fun delete() {
        viewModelScope.launch {
            mainGoal.value?.let {
                dao.delete(it)
                navigateToMainGoal()
            }
        }
    }
}