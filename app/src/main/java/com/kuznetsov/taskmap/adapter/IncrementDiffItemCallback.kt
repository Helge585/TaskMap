package com.kuznetsov.taskmap.adapter

import androidx.recyclerview.widget.DiffUtil
import com.kuznetsov.taskmap.entity.Increment

class IncrementDiffItemCallback: DiffUtil.ItemCallback<Increment>() {
    override fun areItemsTheSame(oldItem: Increment, newItem: Increment): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Increment, newItem: Increment): Boolean {
        return oldItem == newItem
    }

}