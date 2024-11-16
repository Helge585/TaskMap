package com.kuznetsov.taskmap.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kuznetsov.taskmap.databinding.ThisDayTaskItemBinding
import com.kuznetsov.taskmap.entity.ThisDayTask

class ThisDayTaskAdapter(
        val deleteButtonClickListener: (ThisDayTask) -> Unit,
        val openButtonClickListener: (Long) -> Unit,
        val updateThisDayTask: (ThisDayTask) -> Unit)
    : ListAdapter<ThisDayTask, ThisDayTaskAdapter.ThisDayTaskItemViewHolder>(ThisDayTaskDiffItemCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ThisDayTaskItemViewHolder {
        return ThisDayTaskItemViewHolder.inflateFrom(parent)
    }

    override fun onBindViewHolder(holder: ThisDayTaskItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, deleteButtonClickListener, openButtonClickListener,
            updateThisDayTask)
    }

    class ThisDayTaskItemViewHolder(val binding: ThisDayTaskItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ThisDayTask,
                 deleteButtonClickListener: (ThisDayTask) -> Unit,
                 openButtonClickListener: (Long) -> Unit,
                 updateThisDayTask: (ThisDayTask) -> Unit) {

            binding.thisDayTaskName.setText(item.name)
            binding.thisDayTaskDescription.setText(item.description)

            binding.thisDayTaskSolved.isChecked = item.status == 1L
            binding.thisDayTaskDeleteButton.visibility = if (item.status == 1L) View.VISIBLE else View.INVISIBLE
            binding.thisDayTaskSolved.setOnClickListener {
                if (binding.thisDayTaskSolved.isChecked) {
                    item.status = 1
                    binding.thisDayTaskDeleteButton.visibility = View.VISIBLE
                    updateThisDayTask(item)
                    //binding.thisDayTaskCardView.setCardBackgroundColor()
                } else {
                    binding.thisDayTaskDeleteButton.visibility = View.INVISIBLE
                    item.status = 0
                    updateThisDayTask(item)
                    //binding.thisDayTaskCardView.setCardBackgroundColor(Color.WHITE)
                }
            }
            if (item.stepId >= 0) {
                binding.thisDayTaskOpenButton.visibility = View.VISIBLE
                binding.thisDayTaskOpenButton.setOnClickListener {
                    openButtonClickListener(item.stepId)
                }
            } else {
                binding.thisDayTaskOpenButton.visibility = View.INVISIBLE
            }

            binding.thisDayTaskDeleteButton.setOnClickListener {
                deleteButtonClickListener(item)
            }
            binding.thisDayTaskEdit.setOnClickListener {
                if (binding.thisDayTaskEdit.text == "Edit") {
                    binding.thisDayTaskEdit.text = "Save"
                    binding.thisDayTaskName.isEnabled = true
                    binding.thisDayTaskDescription.isEnabled = true
                } else {
                    binding.thisDayTaskEdit.text = "Edit"
                    binding.thisDayTaskName.isEnabled = false
                    binding.thisDayTaskDescription.isEnabled = false
                    item.name = binding.thisDayTaskName.text.toString()
                    item.description = binding.thisDayTaskDescription.text.toString()
                    updateThisDayTask(item)
                }
            }
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