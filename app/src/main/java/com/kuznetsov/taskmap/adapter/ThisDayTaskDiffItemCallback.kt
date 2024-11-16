package com.kuznetsov.taskmap.adapter

import androidx.recyclerview.widget.DiffUtil
import com.kuznetsov.taskmap.entity.ThisDayTask

class ThisDayTaskDiffItemCallback: DiffUtil.ItemCallback<ThisDayTask>() {
    override fun areItemsTheSame(oldItem: ThisDayTask, newItem: ThisDayTask): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ThisDayTask, newItem: ThisDayTask): Boolean {
        return oldItem == newItem
    }
}