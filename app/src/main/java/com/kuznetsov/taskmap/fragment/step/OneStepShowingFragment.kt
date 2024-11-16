package com.kuznetsov.taskmap.fragment.step

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kuznetsov.taskmap.R
import com.kuznetsov.taskmap.adapter.IncrementAdapter
import com.kuznetsov.taskmap.dao.GoalDatabase
import com.kuznetsov.taskmap.databinding.StepItemBinding
import com.kuznetsov.taskmap.entity.Increment
import com.kuznetsov.taskmap.viewmodel.step.OneStepShowingViewModel
import com.kuznetsov.taskmap.viewmodel.step.OneStepShowingViewModelFactory

class OneStepShowingFragment : Fragment() {
    private var _binding: StepItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = StepItemBinding.inflate(layoutInflater, container, false)

        val stepId = OneStepShowingFragmentArgs.fromBundle(requireArguments()).stepId

        val application = requireNotNull(activity).application
        val db = GoalDatabase.getInstance(application)

        val viewModelFactory = OneStepShowingViewModelFactory(db, stepId)
        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(OneStepShowingViewModel::class.java)

        viewModel.step.observe(viewLifecycleOwner, Observer {step ->
            binding.step = step
            binding.stepSlider.valueFrom = step.startResult.toFloat()
            binding.stepSlider.value = step.currentResult.toFloat()
            binding.stepSlider.valueTo = step.finishResult.toFloat()
            binding.percentText.text = viewModel.getPercentText(step.currentResult, step.finishResult)
            binding.stepEditButton.visibility = View.INVISIBLE
            binding.addThisDay.visibility = View.INVISIBLE
            binding.incrementsShowButton.visibility = View.INVISIBLE

            binding.plusButton.setOnClickListener {
                val newCurrent = (binding.stepSlider.value + 1).toLong()
                if (newCurrent <= binding.stepSlider.valueTo.toLong()) {
                    binding.stepSlider.value = newCurrent.toFloat()
                    binding.percentText.text = viewModel.getPercentText(newCurrent,
                        binding.stepSlider.valueTo.toLong())
                }
                binding.saveButton.isVisible = true
            }
            binding.minusButton.setOnClickListener {
                val newCurrent = (binding.stepSlider.value - 1).toLong()
                if (newCurrent >= (binding.stepSlider.valueFrom).toLong()) {
                    binding.stepSlider.value = newCurrent.toFloat()
                    binding.percentText.text = viewModel.getPercentText(newCurrent,
                        binding.stepSlider.valueTo.toLong())
                }
                binding.saveButton.isVisible = true
            }
            binding.saveButton.setOnClickListener {
                if (it.isVisible) {
                    viewModel.updateStep(step, binding.stepSlider.value.toLong())
                    it.isVisible = false
                }
            }
            //binding.incrementsList.
        })

        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = IncrementAdapter { increment: Increment ->
            viewModel.deleteIncrement(increment)
        }
        binding.incrementsList.visibility = View.VISIBLE
        binding.incrementsList.adapter = adapter
        viewModel.increments.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}