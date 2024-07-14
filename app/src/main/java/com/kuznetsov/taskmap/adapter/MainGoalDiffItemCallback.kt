package com.kuznetsov.taskmap.adapter

import androidx.recyclerview.widget.DiffUtil
import com.kuznetsov.taskmap.entity.MainGoal

class MainGoalDiffItemCallback: DiffUtil.ItemCallback<MainGoal>() {

    override fun areItemsTheSame(oldItem: MainGoal, newItem: MainGoal): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MainGoal, newItem: MainGoal): Boolean {
        return oldItem == newItem
    }
}