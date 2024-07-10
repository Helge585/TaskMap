package com.kuznetsov.taskmap

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kuznetsov.taskmap.databinding.MainGoalItemBinding

class MainGoalAdapter(val editClickListener: (MainGoal) -> Boolean)
    : ListAdapter<MainGoal, MainGoalAdapter.MainGoalItemViewHolder>(MainGoalDiffItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainGoalItemViewHolder {
        return MainGoalItemViewHolder.inflateFrom(parent)
    }

    override fun onBindViewHolder(holder: MainGoalItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, editClickListener)
    }

    class MainGoalItemViewHolder(val binding : MainGoalItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MainGoal, editClickListener: (MainGoal) -> Boolean) {
            binding.mainGoal = item
            binding.mainGoalEditButton.setOnClickListener { editClickListener(item) }
        }

        companion object {
            fun inflateFrom(parent: ViewGroup): MainGoalItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MainGoalItemBinding.inflate(layoutInflater, parent, false)
                return MainGoalItemViewHolder(binding)
            }
        }
    }
}