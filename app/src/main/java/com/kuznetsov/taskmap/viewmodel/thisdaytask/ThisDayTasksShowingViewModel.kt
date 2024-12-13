package com.kuznetsov.taskmap.viewmodel.thisdaytask

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.kuznetsov.taskmap.dao.ThisDayTaskDao
import com.kuznetsov.taskmap.entity.ThisDayAddable
import com.kuznetsov.taskmap.entity.ThisDayTask
import kotlinx.coroutines.launch

const val TAGG = "ThisDayTasksShowingViewModel"
class ThisDayTasksShowingViewModel(val thisDayTaskDao: ThisDayTaskDao): ViewModel() {

    val NOT_CHOOSEN_FOR_NAVIGATING = -1L

    val thisDayTasks = thisDayTaskDao.getAllIndependentTasks()

    val thisDayGroups = thisDayTaskDao.getAllGroups()

    private val _thisDayMainPageItems = MutableLiveData<MutableList<ThisDayAddable>>()

//    val thisDayMainPageItems: LiveData<MutableList<ThisDayAddable>>
//        get() = _thisDayMainPageItems

    var thisDayMainPageItems = mutableListOf<ThisDayAddable>()

    private val _isNavigatedToThisDayTaskCreating = MutableLiveData<Boolean>()
    val isNavigatedToThisDayTaskCreating: LiveData<Boolean> get() = _isNavigatedToThisDayTaskCreating

    private val _isNavigatedToStepShowing = MutableLiveData<Long>()
    val isNavigatedToStepShowing: LiveData<Long> get() = _isNavigatedToStepShowing

    private val _isNavigatedToDetailsShowing = MutableLiveData<Long>()
    val isNavigatedToDetailsShowing: LiveData<Long> get() = _isNavigatedToDetailsShowing

    private val _isNavigatedToGroupDetailsShowing = MutableLiveData<Long>()
    val isNavigatedToGroupDetailsShowing: LiveData<Long> get() = _isNavigatedToGroupDetailsShowing

    init {
        _isNavigatedToStepShowing.value = NOT_CHOOSEN_FOR_NAVIGATING
        _isNavigatedToThisDayTaskCreating.value = false
        _isNavigatedToDetailsShowing.value = NOT_CHOOSEN_FOR_NAVIGATING
        _isNavigatedToGroupDetailsShowing.value = NOT_CHOOSEN_FOR_NAVIGATING

        _thisDayMainPageItems.value = mutableListOf()

//        thisDayTasks.observeForever { tasks ->
//            Log.i(TAGG, "Added taskssss")
//            _thisDayMainPageItems.value?.clear()
//            _thisDayMainPageItems.value?.addAll(tasks)
//            thisDayGroups.value?.let { groups ->
//                _thisDayMainPageItems.value?.addAll(groups)
//            }
//            Log.i(TAGG, "Added taskssss end")
//        }
//        thisDayGroups.observeForever { groups ->
//            Log.i(TAGG, "Added groups")
//            _thisDayMainPageItems.value?.clear()
//            _thisDayMainPageItems.value?.addAll(groups)
//            thisDayTasks.value?.let { tasks ->
//                _thisDayMainPageItems.value?.addAll(tasks)
//            }
//        }
    }

//    fun updateMainPageItems() {
//        Log.i("ssss", "updating main page items")
//        _thisDayMainPageItems.value?.clear()
//        thisDayTasks.value?.let {
//            _thisDayMainPageItems.value?.addAll(it)
//        }
//        thisDayGroups.value?.let {
//            _thisDayMainPageItems.value?.addAll(it)
//        }
//    }

    fun updateMainPageItems(): Boolean{
        Log.i("ssss", "updating main page items")
        thisDayMainPageItems = mutableListOf()
        thisDayTasks.value?.let {
            thisDayMainPageItems.addAll(it)
        }
        thisDayGroups.value?.let {
            thisDayMainPageItems.addAll(it)
        }
        return true
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

    fun navigateToGroupDetailsShowing(thisDayGroupId: Long) {
        _isNavigatedToGroupDetailsShowing.value = thisDayGroupId
    }

    fun afterNavigateToGroupDetailsShowing() {
        _isNavigatedToGroupDetailsShowing.value = NOT_CHOOSEN_FOR_NAVIGATING
    }

    fun deleteThisDayTask(thisDayAddable: ThisDayAddable) {
//        viewModelScope.launch {
//            thisDayTaskDao.deleteTask(thisDayTask)
//        }
    }
    fun updateThisDayTask(thisDayAddable: ThisDayAddable) {
        //Log.i("ThisDayTasksShowingViewModel", "Updating")
//        viewModelScope.launch {
//            thisDayTaskDao.updateTask(thisDayTask)
//        }
    }

}