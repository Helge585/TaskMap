package com.kuznetsov.taskmap.viewmodel.subgoal

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kuznetsov.taskmap.dao.MainGoalDao
import com.kuznetsov.taskmap.dao.SubGoalDao

class SubGoalsShowingViewModel(private val mainGoalDao: MainGoalDao,
                               private val subGoalDao: SubGoalDao,
                               private val mainGoalId: Long): ViewModel() {

    val NOT_CLICKED = -1L

    val mainGoal = mainGoalDao.get(mainGoalId)

    val subGoals = subGoalDao.getByMainGoalId(mainGoalId)

    private val _isNavigatedToSubGoalCreating = MutableLiveData<Boolean>()
    val isNavigatedToSubGoalCreating: LiveData<Boolean> get() = _isNavigatedToSubGoalCreating

    private val _isNavigatedToSubGoalEditing = MutableLiveData<Boolean>()
    val isNavigatedToSubGoalEditing: LiveData<Boolean> get() = _isNavigatedToSubGoalEditing

    private val _isNavigatedToStepFragment = MutableLiveData<Boolean>()
    val isNavigatedToStepFragment: LiveData<Boolean> get() = _isNavigatedToStepFragment

    var clickedSubGoalId = NOT_CLICKED

    init {
        _isNavigatedToSubGoalCreating.value = false
        _isNavigatedToSubGoalEditing.value = false
        _isNavigatedToStepFragment.value = false
    }

    fun navigateToSubGoalCreating() {
        _isNavigatedToSubGoalCreating.value = true
    }

    fun afterNavigateToSubGoalCreating() {
        _isNavigatedToSubGoalCreating.value = false
    }

    fun navigateToSubGoalEditing(subGoalId: Long) {
        //changing livedatavalue must be last
        clickedSubGoalId = subGoalId
        _isNavigatedToSubGoalEditing.value = true
    }

    fun afterNavigateToSubGoalEditing() {
        clickedSubGoalId = NOT_CLICKED
        _isNavigatedToSubGoalEditing.value = false
    }

    fun navigateToStepFragment(subGoalId: Long) {
        clickedSubGoalId = subGoalId
        _isNavigatedToStepFragment.value = true
    }

    fun afterNavigateToStepFragment() {
        clickedSubGoalId = NOT_CLICKED
        _isNavigatedToStepFragment.value = false
    }

    fun mainGoalInfo(): String {
        return mainGoal.value.toString()
    }
    fun printSubGoals() {
        subGoals.value?.forEach {
            Log.i("SubGoalViewwModel", "$it")
        }
    }
}