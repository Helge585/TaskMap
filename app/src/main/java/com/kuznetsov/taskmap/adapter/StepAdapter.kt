package com.kuznetsov.taskmap.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kuznetsov.taskmap.databinding.StepItemBinding
import com.kuznetsov.taskmap.entity.Step


class StepAdapter(val editClickListener: (stepId: Long) -> Boolean,
                  val getPercentText: (current: Long, finish: Long) -> String,
                  val updateStep: (step: Step) -> Unit)
    : ListAdapter<Step, StepAdapter.StepItemViewHolder>(StepDiffItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepItemViewHolder {
        return StepItemViewHolder.inflateFrom(parent)
    }

    override fun onBindViewHolder(holder: StepItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, editClickListener, getPercentText, updateStep)
    }

    class StepItemViewHolder(val binding : StepItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Step, editClickListener: (stepId: Long) -> Boolean,
                 getPercentText: (current: Long, finish: Long) -> String,
                 updateStep: (step: Step) -> Unit) {

            binding.step = item
            binding.stepEditButton.setOnClickListener {
                editClickListener(item.id)
            }
            binding.stepSlider.valueFrom = item.startResult.toFloat()
            binding.stepSlider.value = item.currentResult.toFloat()
            binding.stepSlider.valueTo = item.finishResult.toFloat()
            binding.percentText.text = getPercentText(item.currentResult, item.finishResult)
            binding.saveButton.isVisible = false
            binding.plusButton.setOnClickListener {
                val newCurrent = (binding.stepSlider.value + 1).toLong()
                if (newCurrent <= binding.stepSlider.valueTo.toLong()) {
                    binding.stepSlider.value = newCurrent.toFloat()
                    binding.percentText.text = getPercentText(newCurrent,
                        binding.stepSlider.valueTo.toLong())
                }
                binding.saveButton.isVisible = true
            }
            binding.minusButton.setOnClickListener {
                val newCurrent = (binding.stepSlider.value - 1).toLong()
                if (newCurrent >= (binding.stepSlider.valueFrom).toLong()) {
                    binding.stepSlider.value = newCurrent.toFloat()
                    binding.percentText.text = getPercentText(newCurrent,
                        binding.stepSlider.valueTo.toLong())
                }
                binding.saveButton.isVisible = true
            }
            binding.saveButton.setOnClickListener {
                if (it.isVisible) {
                    item.currentResult = binding.stepSlider.value.toLong()
                    updateStep(item)
                    it.isVisible = false
                }
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