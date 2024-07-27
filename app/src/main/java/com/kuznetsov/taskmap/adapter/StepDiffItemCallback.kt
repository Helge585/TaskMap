package com.kuznetsov.taskmap.adapter

import androidx.recyclerview.widget.DiffUtil
import com.kuznetsov.taskmap.entity.Step

class StepDiffItemCallback: DiffUtil.ItemCallback<Step>() {

    override fun areItemsTheSame(oldItem: Step, newItem: Step): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Step, newItem: Step): Boolean {
        return oldItem == newItem
    }
}