package com.kuznetsov.taskmap

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainGoalViewModel(val dao: MainGoalDao): ViewModel() {

    private val _navigateToCreating = MutableLiveData<Boolean>()
    val navigateToCreating: LiveData<Boolean> get() = _navigateToCreating

    init {
        _navigateToCreating.value = false
    }

    fun afterNavigateToCreating() {
        _navigateToCreating.value = true
    }

    fun beforeNavigateToCreating() {
        _navigateToCreating.value = false
    }
}