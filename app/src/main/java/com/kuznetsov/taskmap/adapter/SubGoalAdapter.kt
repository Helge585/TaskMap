package com.kuznetsov.taskmap.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kuznetsov.taskmap.databinding.SubGoalItemBinding
import com.kuznetsov.taskmap.entity.SubGoal

class SubGoalAdapter(val editClickListener: (subGoalId: Long) -> Boolean)
    : ListAdapter<SubGoal, SubGoalAdapter.SubGoalItemViewHolder>(SubGoalDiffItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubGoalItemViewHolder {
        return SubGoalItemViewHolder.inflateFrom(parent)
    }

    override fun onBindViewHolder(holder: SubGoalItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, editClickListener)
    }

    class SubGoalItemViewHolder(val binding : SubGoalItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SubGoal, editClickListener: (subGoalId: Long) -> Boolean) {
            binding.subGoal = item
            binding.subGoalEditButton.setOnClickListener {
                editClickListener(item.id)
            }
        }

        companion object {
            fun inflateFrom(parent: ViewGroup): SubGoalItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SubGoalItemBinding.inflate(layoutInflater, parent, false)
                return SubGoalItemViewHolder(binding)
            }
        }
    }
}