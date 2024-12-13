package com.kuznetsov.taskmap.viewmodel.thisdaytask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuznetsov.taskmap.dao.GoalDatabase
import kotlinx.coroutines.launch

class ThisDayGroupDetailsShowingViewModel(val groupId: Long, val db : GoalDatabase): ViewModel() {

    val NOT_CHOOSEN_FOR_NAVIGATING = -1L

    val group = db.thisDayTaskDao.getGroup(groupId)

    val tasks = db.thisDayTaskDao.getTasksByGroupId(groupId)

    private val _isNavigatedToTaskDetailsShowing = MutableLiveData<Long>()
    val isNavigatedTiTaskDetailsShow: LiveData<Long> get() = _isNavigatedToTaskDetailsShowing

    init {
        _isNavigatedToTaskDetailsShowing.value = NOT_CHOOSEN_FOR_NAVIGATING
    }

    fun navigateToTaskDetailsShowing(taskId: Long) {
        _isNavigatedToTaskDetailsShowing.value = taskId
    }

    fun afterNavigateToTaskDetailsShowing() {
        _isNavigatedToTaskDetailsShowing.value = NOT_CHOOSEN_FOR_NAVIGATING
    }

    fun deleteGroup() {
        group.value?.let {
            viewModelScope.launch {
                db.thisDayTaskDao.deleteGroup(it)
            }
        }
    }

    fun updateGroup() {
        group.value?.let {
            viewModelScope.launch {
                db.thisDayTaskDao.updateGroup(it)
            }
        }
    }
}