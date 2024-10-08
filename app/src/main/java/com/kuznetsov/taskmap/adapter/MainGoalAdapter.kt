package com.kuznetsov.taskmap.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kuznetsov.taskmap.databinding.MainGoalItemBinding
import com.kuznetsov.taskmap.entity.MainGoal

class MainGoalAdapter(val editClickListener: (MainGoal) -> Boolean,
                      val rootClickListener: (MainGoal) -> Boolean)
    : ListAdapter<MainGoal, MainGoalAdapter.MainGoalItemViewHolder>(MainGoalDiffItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainGoalItemViewHolder {
        return MainGoalItemViewHolder.inflateFrom(parent)
    }

    override fun onBindViewHolder(holder: MainGoalItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, editClickListener, rootClickListener)
    }

    class MainGoalItemViewHolder(val binding : MainGoalItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MainGoal, editClickListener: (MainGoal) -> Boolean,
                 rootClickListener: (MainGoal) -> Boolean) {

            binding.mainGoal = item
            binding.root.setOnClickListener { rootClickListener(item) }
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