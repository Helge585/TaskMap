package com.kuznetsov.taskmap.adapter

import androidx.recyclerview.widget.DiffUtil
import com.kuznetsov.taskmap.entity.SubGoal

class SubGoalDiffItemCallback: DiffUtil.ItemCallback<SubGoal>() {

    override fun areItemsTheSame(oldItem: SubGoal, newItem: SubGoal): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SubGoal, newItem: SubGoal): Boolean {
        return oldItem == newItem
    }
}