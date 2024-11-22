package com.kuznetsov.taskmap.viewmodel.thisdaytask

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuznetsov.taskmap.dao.ThisDayTaskDao
import com.kuznetsov.taskmap.entity.ThisDayTask
import kotlinx.coroutines.launch
import java.math.BigInteger

const val TAGG = "ThisDayTasksShowingViewModel"
class ThisDayTasksShowingViewModel(val thisDayTaskDao: ThisDayTaskDao): ViewModel() {

    val NOT_CHOOSEN_FOR_NAVIGATING = -1L

    val thisDayTasks = thisDayTaskDao.getAll()

    private val _isNavigatedToThisDayTaskCreating = MutableLiveData<Boolean>()
    val isNavigatedToThisDayTaskCreating: LiveData<Boolean> get() = _isNavigatedToThisDayTaskCreating

    private val _isNavigatedToStepShowing = MutableLiveData<Long>()
    val isNavigatedToStepShowing: LiveData<Long> get() = _isNavigatedToStepShowing

    private val _isNavigatedToDetailsShowing = MutableLiveData<Long>()
    val isNavigatedToDetailsShowing: LiveData<Long> get() = _isNavigatedToDetailsShowing

    init {
        _isNavigatedToStepShowing.value = NOT_CHOOSEN_FOR_NAVIGATING
        _isNavigatedToThisDayTaskCreating.value = false
        _isNavigatedToDetailsShowing.value = NOT_CHOOSEN_FOR_NAVIGATING
    }

    fun navigateToThisDayTaskCreating() {
        _isNavigatedToThisDayTaskCreating.value = true
    }

    fun afterNavigateToThisDayTaskCreating() {
        _isNavigatedToThisDayTaskCreating.value = false
    }

    fun navigateToStepShowing(stepId : Long) {
        _isNavigatedToStepShowing.value = stepId
    }

    fun afterNavigateToStepShowing() {
        _isNavigatedToStepShowing.value = NOT_CHOOSEN_FOR_NAVIGATING
    }

    fun navigateToDetailsShowing(thisDayTaskId: Long) {
        Log.i(TAGG, "Navigating to details showing")
        _isNavigatedToDetailsShowing.value = thisDayTaskId
    }

    fun afterNavigateToDetailsShowing() {
        _isNavigatedToDetailsShowing.value = NOT_CHOOSEN_FOR_NAVIGATING
    }

    fun deleteThisDayTask(thisDayTask: ThisDayTask) {
        viewModelScope.launch {
            thisDayTaskDao.delete(thisDayTask)
        }
    }
    fun updateThisDayTask(thisDayTask: ThisDayTask) {
        //Log.i("ThisDayTasksShowingViewModel", "Updating")
        viewModelScope.launch {
            thisDayTaskDao.update(thisDayTask)
        }
    }

}