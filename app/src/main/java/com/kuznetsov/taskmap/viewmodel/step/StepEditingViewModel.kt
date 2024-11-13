package com.kuznetsov.taskmap.viewmodel.step

import android.support.v4.os.IResultReceiver._Parcel
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuznetsov.taskmap.dao.StepDao
import kotlinx.coroutines.launch


class StepEditingViewModel(val stepDao: StepDao, val stepId: Long): ViewModel() {
    val ERROR_UPDATING = "Step hasn't been updated!"

    val step = stepDao.get(stepId)
    var newName = MutableLiveData("")
    var newStartResult = MutableLiveData("")
    var newCurrentResult = MutableLiveData("")
    var newFinishResult = MutableLiveData("")

    private var _isNavigatedToStepShowingFragment = MutableLiveData<Boolean>()
    val isNavigatedToStepShowingFragment: LiveData<Boolean> get()
        = _isNavigatedToStepShowingFragment

    private var _isMadeToast = MutableLiveData<Boolean>()
    val isMadeToast: LiveData<Boolean> get() = _isMadeToast
    init {
        _isNavigatedToStepShowingFragment.value = false
        _isMadeToast.value = false
    }
    fun navigateToStepShowingFragment() {
        _isNavigatedToStepShowingFragment.value = true
    }

    fun afterNavigateToStepShowingFragment() {
        _isNavigatedToStepShowingFragment.value = false
    }

    fun afterMadeToast() {
        _isMadeToast.value = false
    }

    fun cleanFields() {
        newName.value = ""
        newStartResult.value = ""
        newCurrentResult.value = ""
        newFinishResult.value = ""
    }

    fun delete() {
        step.value?.let {
            viewModelScope.launch {
                stepDao.delete(it)
            }
            navigateToStepShowingFragment()
        }
    }

    fun update() {
        val name = newName.value ?: ""
        val start = newStartResult.value?.toLongOrNull() ?: -1
        val current = newCurrentResult.value?.toLongOrNull() ?: -1
        val finish = newFinishResult.value?.toLongOrNull() ?: -1
        if (start < 0 || current < 0 || finish < 0 || name.length == 0
            || start > current || start >= finish || current > finish) {
            _isMadeToast.value = true
        } else {
            step.value?.let {
                it.name = name
                it.startResult = start
                it.currentResult = current
                it.finishResult = finish
                viewModelScope.launch {
                    stepDao.update(it)
                }
                cleanFields()
            }
        }
    }
}