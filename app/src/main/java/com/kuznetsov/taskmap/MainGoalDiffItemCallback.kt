package com.kuznetsov.taskmap

import androidx.recyclerview.widget.DiffUtil

class MainGoalDiffItemCallback: DiffUtil.ItemCallback<MainGoal>() {

    override fun areItemsTheSame(oldItem: MainGoal, newItem: MainGoal): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MainGoal, newItem: MainGoal): Boolean {
        return oldItem == newItem
    }
}