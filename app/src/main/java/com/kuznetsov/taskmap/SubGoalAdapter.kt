package com.kuznetsov.taskmap

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kuznetsov.taskmap.databinding.MainGoalItemBinding
import com.kuznetsov.taskmap.databinding.SubGoalItemBinding

class SubGoalAdapter()
    : ListAdapter<SubGoal, SubGoalAdapter.SubGoalItemViewHolder>(SubGoalDiffItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubGoalItemViewHolder {
        return SubGoalItemViewHolder.inflateFrom(parent)
    }

    override fun onBindViewHolder(holder: SubGoalItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class SubGoalItemViewHolder(val binding : SubGoalItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SubGoal) {
            binding.subGoal = item
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