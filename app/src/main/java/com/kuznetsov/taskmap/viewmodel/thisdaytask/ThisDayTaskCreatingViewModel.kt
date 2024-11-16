package com.kuznetsov.taskmap.viewmodel.thisdaytask

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuznetsov.taskmap.dao.ThisDayTaskDao
import com.kuznetsov.taskmap.entity.ThisDayTask
import com.kuznetsov.taskmap.utils.MyDateUtils
import kotlinx.coroutines.launch

const val TAG = "ThisDayTaskCreatingViewModel"

class ThisDayTaskCreatingViewModel(val dao: ThisDayTaskDao): ViewModel() {

    var name: String = ""
    var description: String = ""


    fun saveThisDayTask() {
        if (name.isEmpty() || description.isEmpty()) {
            Log.i(TAG, "Task hasn't been saved")
            return
        }
        val thisDayTask = ThisDayTask(
            id = 0,
            name = name,
            description = description,
            stepId = -1,
            status = 0,
            creatingDate = MyDateUtils.getCurrentDateInMillis()
        )
        Log.i(TAG, "before inserting task")
        viewModelScope.launch {
            dao.insert(thisDayTask)
        }
    }

}