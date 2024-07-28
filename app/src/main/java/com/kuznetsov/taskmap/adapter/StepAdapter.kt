package com.kuznetsov.taskmap.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kuznetsov.taskmap.databinding.StepItemBinding
import com.kuznetsov.taskmap.entity.Step


class StepAdapter(val editClickListener: (stepId: Long) -> Boolean)
    : ListAdapter<Step, StepAdapter.StepItemViewHolder>(StepDiffItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepItemViewHolder {
        return StepItemViewHolder.inflateFrom(parent)
    }

    override fun onBindViewHolder(holder: StepItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, editClickListener)
    }

    class StepItemViewHolder(val binding : StepItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Step, editClickListener: (stepId: Long) -> Boolean) {
            binding.step = item
            binding.stepEditButton.setOnClickListener {
                editClickListener(item.id)
            }
        }

        companion object {
            fun inflateFrom(parent: ViewGroup): StepItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = StepItemBinding.inflate(layoutInflater, parent, false)
                return StepItemViewHolder(binding)
            }
        }
    }
}