package com.kuznetsov.taskmap

import androidx.recyclerview.widget.DiffUtil

class SubGoalDiffItemCallback: DiffUtil.ItemCallback<SubGoal>() {

    override fun areItemsTheSame(oldItem: SubGoal, newItem: SubGoal): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SubGoal, newItem: SubGoal): Boolean {
        return oldItem == newItem
    }
}