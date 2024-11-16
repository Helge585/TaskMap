package com.kuznetsov.taskmap.viewmodel.thisdaytask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kuznetsov.taskmap.dao.ThisDayTaskDao

class ThisDayTasksShowingViewModel(val thisDayTaskDao: ThisDayTaskDao): ViewModel() {

    val thisDayTasks = thisDayTaskDao.getAll()

    private val _isNavigatedToThisDayTaskCreating = MutableLiveData<Boolean>()
    val isNavigatedToThisDayTaskCreating: LiveData<Boolean> get() = _isNavigatedToThisDayTaskCreating

    fun navigateToThisDayTaskCreating() {
        _isNavigatedToThisDayTaskCreating.value = true
    }

    fun afterNavigateToThisDayTaskCreating() {
        _isNavigatedToThisDayTaskCreating.value = false
    }
}