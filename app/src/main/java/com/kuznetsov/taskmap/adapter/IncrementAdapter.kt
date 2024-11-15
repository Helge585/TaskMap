package com.kuznetsov.taskmap.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kuznetsov.taskmap.databinding.IncrementItemBinding
import com.kuznetsov.taskmap.databinding.MainGoalItemBinding
import com.kuznetsov.taskmap.entity.Increment

class IncrementAdapter(val undoButtonClickListener: (Increment) -> Unit)
    : ListAdapter<Increment, IncrementAdapter.IncrementItemViewHolder>(IncrementDiffItemCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IncrementAdapter.IncrementItemViewHolder {
        return IncrementItemViewHolder.inflateFrom(parent)
    }

    override fun onBindViewHolder(holder: IncrementItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, undoButtonClickListener)
    }

    class IncrementItemViewHolder(val binding: IncrementItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Increment, undoButtonClickListener: (Increment) -> Unit) {
            binding.welcomeTextview.text = item.getDescription()
            binding.incrementUndoButton.setOnClickListener {
                undoButtonClickListener(item)
            }
        }

        companion object {
            fun inflateFrom(parent: ViewGroup): IncrementItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = IncrementItemBinding.inflate(layoutInflater, parent, false)
                return IncrementItemViewHolder(binding)
            }
        }
    }
}