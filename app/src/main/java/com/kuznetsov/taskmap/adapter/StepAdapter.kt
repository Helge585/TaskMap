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
                  val updateStep: (step: Step, newCurrentResult: Long) -> Unit,
                  val showIncrementsButtonClickListener: (stepId: Long) -> Boolean,
                  val addToThisDayClickListener: (Step) -> Unit)
    : ListAdapter<Step, StepAdapter.StepItemViewHolder>(StepDiffItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepItemViewHolder {
        return StepItemViewHolder.inflateFrom(parent)
    }

    override fun onBindViewHolder(holder: StepItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, editClickListener, getPercentText,
            updateStep, showIncrementsButtonClickListener,
            addToThisDayClickListener)
    }

    class StepItemViewHolder(val binding : StepItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Step, editClickListener: (stepId: Long) -> Boolean,
                 getPercentText: (current: Long, finish: Long) -> String,
                 updateStep: (step: Step, newCurrentResult: Long) -> Unit,
                 showIncrementsButtonClickListener: (stepId: Long) -> Boolean,
                 addToThisDayClickListener: (Step) -> Unit) {

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
                    //item.currentResult = binding.stepSlider.value.toLong()
                    updateStep(item, binding.stepSlider.value.toLong())
                    it.isVisible = false
                }
            }
            binding.incrementsShowButton.setOnClickListener {
                showIncrementsButtonClickListener(item.id)
            }
            binding.addThisDay.setOnClickListener {
                addToThisDayClickListener(item)
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