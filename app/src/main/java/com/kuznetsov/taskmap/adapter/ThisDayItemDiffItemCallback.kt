package com.kuznetsov.taskmap.adapter

import androidx.recyclerview.widget.DiffUtil
import com.kuznetsov.taskmap.entity.ThisDayAddable
import com.kuznetsov.taskmap.entity.ThisDayGroup
import com.kuznetsov.taskmap.entity.ThisDayTask

class ThisDayItemDiffItemCallback: DiffUtil.ItemCallback<ThisDayAddable>() {
    override fun areItemsTheSame(oldItem: ThisDayAddable, newItem: ThisDayAddable): Boolean {
        if (oldItem is ThisDayGroup && newItem is ThisDayGroup) {
            return oldItem.id == newItem.id
        }
        if (oldItem is ThisDayTask && newItem is ThisDayTask) {
            return oldItem.id == newItem.id
        }
        return false
    }

    override fun areContentsTheSame(oldItem: ThisDayAddable, newItem: ThisDayAddable): Boolean {
        if (oldItem is ThisDayGroup && newItem is ThisDayGroup) {
            val old = oldItem as ThisDayGroup
            val new = newItem as ThisDayGroup
            return old == new
        }
        if (oldItem is ThisDayTask && newItem is ThisDayTask) {
            val old = oldItem as ThisDayTask
            val new = newItem as ThisDayTask
            return old == new
        }
        return false
    }
}