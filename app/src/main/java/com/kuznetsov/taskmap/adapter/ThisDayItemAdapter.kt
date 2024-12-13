package com.kuznetsov.taskmap.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kuznetsov.taskmap.R
import com.kuznetsov.taskmap.databinding.ThisDayTaskItemBinding
import com.kuznetsov.taskmap.entity.ThisDayAddable
import com.kuznetsov.taskmap.entity.ThisDayGroup
import com.kuznetsov.taskmap.entity.ThisDayTask

private const val TAG = "ThisDayTaskAdapter"
class ThisDayItemAdapter(
        val deleteButtonClickListener: (ThisDayAddable) -> Unit,
        val openButtonClickListener: (Long) -> Unit,
        val updateThisDayTask: (ThisDayAddable) -> Unit,
        val detailsShowClickListener: (Long) -> Unit,
        val groupDetailsShowClickListener: (Long) -> Unit)
    : ListAdapter<ThisDayAddable, ThisDayItemAdapter.ThisDayTaskItemViewHolder>(ThisDayItemDiffItemCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ThisDayTaskItemViewHolder {
        return ThisDayTaskItemViewHolder.inflateFrom(parent)
    }

    override fun onBindViewHolder(holder: ThisDayTaskItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, deleteButtonClickListener, openButtonClickListener,
            updateThisDayTask, detailsShowClickListener, groupDetailsShowClickListener)
    }



    class ThisDayTaskItemViewHolder(val binding: ThisDayTaskItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ThisDayAddable,
                 deleteButtonClickListener: (ThisDayAddable) -> Unit,
                 openButtonClickListener: (Long) -> Unit,
                 updateThisDayTask: (ThisDayAddable) -> Unit,
                 detailsShowClickListener: (Long) -> Unit,
                 groupDetailsShowClickListener: (Long) -> Unit) {

            Log.i(TAG, "bind")
            if (item is ThisDayTask) {
                binding.thisDayTaskName.setText(item.name)
                //binding.thisDayTaskDescription.setText(item.description)

                binding.thisDayTaskSolved.isChecked = item.status == 1L
                //binding.thisDayTaskDeleteButton.visibility = if (item.status == 1L) View.VISIBLE else View.INVISIBLE
                binding.thisDayTaskSolved.setOnClickListener {
                    if (binding.thisDayTaskSolved.isChecked) {
                        item.status = 1
                        //binding.thisDayTaskDeleteButton.visibility = View.VISIBLE
                        updateThisDayTask(item)
                        //binding.thisDayTaskCardView.setCardBackgroundColor()
                    } else {
                        //binding.thisDayTaskDeleteButton.visibility = View.INVISIBLE
                        item.status = 0
                        updateThisDayTask(item)
                        //binding.thisDayTaskCardView.setCardBackgroundColor(Color.WHITE)
                    }
                }
                binding.thisDayTaskName.isClickable = true
                binding.thisDayTaskName.setOnClickListener {
                    Log.i(TAG, "click on task name")
                    detailsShowClickListener(item.id)
                }
            } else if (item is ThisDayGroup) {
                binding.thisDayTaskName.setText(item.name + " ( " + item.id + " )")
                binding.thisDayTaskCardView.setBackgroundColor(Color.YELLOW)
                //binding.thisDayTaskSolved.visibility = View.INVISIBLE
                binding.thisDayTaskName.setOnClickListener {
                    Log.i(TAG, "click on task name")
                    groupDetailsShowClickListener(item.id)
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