package com.kuznetsov.taskmap.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kuznetsov.taskmap.dao.SubGoalDao

class CreateSubGoalViewModel(mainGoalId: Long, dao: SubGoalDao) : ViewModel() {

    private val _isNavigatedToSubGoal = MutableLiveData<Boolean>()
    val isNavigatedToSubGoal: LiveData<Boolean> get() = _isNavigatedToSubGoal

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
}