package com.kuznetsov.taskmap.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kuznetsov.taskmap.databinding.ThisDayTaskItemBinding
import com.kuznetsov.taskmap.entity.ThisDayTask

class ThisDayTaskAdapter()
    : ListAdapter<ThisDayTask, ThisDayTaskAdapter.ThisDayTaskItemViewHolder>(ThisDayTaskDiffItemCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ThisDayTaskItemViewHolder {
        return ThisDayTaskItemViewHolder.inflateFrom(parent)
    }

    override fun onBindViewHolder(holder: ThisDayTaskItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ThisDayTaskItemViewHolder(val binding: ThisDayTaskItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ThisDayTask) {

        }

        companion object {
            fun inflateFrom(parent: ViewGroup): ThisDayTaskItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ThisDayTaskItemBinding.inflate(layoutInflater, parent, false)
                return ThisDayTaskItemViewHolder(binding)
            }
        }
    }
}