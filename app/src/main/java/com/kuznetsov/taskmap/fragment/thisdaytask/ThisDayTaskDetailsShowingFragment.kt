package com.kuznetsov.taskmap.fragment.thisdaytask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kuznetsov.taskmap.dao.GoalDatabase
import com.kuznetsov.taskmap.databinding.FragmentThisDayTaskDetailsShowingBinding
import com.kuznetsov.taskmap.utils.MyStringUtils
import com.kuznetsov.taskmap.viewmodel.thisdaytask.ThisDayTaskDetailsShowingViewModel
import com.kuznetsov.taskmap.viewmodel.thisdaytask.ThisDayTaskDetailsShowingViewModelFactory

class ThisDayTaskDetailsShowingFragment : Fragment() {
    private var _binding: FragmentThisDayTaskDetailsShowingBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentThisDayTaskDetailsShowingBinding
            .inflate(layoutInflater, container, false)

        val taskId = ThisDayTaskDetailsShowingFragmentArgs
            .fromBundle(requireArguments()).thisDayTaskId

        val application = requireNotNull(activity).application
        val db = GoalDatabase.getInstance(application)

        val viewModelFactory = ThisDayTaskDetailsShowingViewModelFactory(taskId, db)
        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ThisDayTaskDetailsShowingViewModel::class.java)

        viewModel.task.observe(viewLifecycleOwner, Observer {
            binding.task = it
        })

        viewModel.step.observe(viewLifecycleOwner, Observer {
            if (it == null) {
                binding.stepSlider.visibility = View.INVISIBLE
                binding.sliderLayout1.visibility = View.INVISIBLE
                binding.sliderLayout2.visibility = View.INVISIBLE
                binding.sliderLayout3.visibility = View.INVISIBLE
                binding.sliderLayout4.visibility = View.INVISIBLE
                return@Observer
            }

            binding.step = it
            binding.stepSlider.valueFrom = it.startResult.toFloat()
            binding.stepSlider.value = it.currentResult.toFloat()
            binding.stepSlider.valueTo = it.finishResult.toFloat()
            binding.percentText.text = MyStringUtils.getPercentText(it.currentResult, it.finishResult)
            binding.saveButton.isVisible = false
            binding.plusButton.setOnClickListener {
                val newCurrent = (binding.stepSlider.value + 1).toLong()
                if (newCurrent <= binding.stepSlider.valueTo.toLong()) {
                    binding.stepSlider.value = newCurrent.toFloat()
                    binding.percentText.text = MyStringUtils.getPercentText(newCurrent,
                        binding.stepSlider.valueTo.toLong())
                }
                binding.saveButton.isVisible = true
            }
            binding.minusButton.setOnClickListener {
                val newCurrent = (binding.stepSlider.value - 1).toLong()
                if (newCurrent >= (binding.stepSlider.valueFrom).toLong()) {
                    binding.stepSlider.value = newCurrent.toFloat()
                    binding.percentText.text = MyStringUtils.getPercentText(newCurrent,
                        binding.stepSlider.valueTo.toLong())
                }
                binding.saveButton.isVisible = true
            }
            binding.saveButton.setOnClickListener {
                if (binding.saveButton.isVisible) {
                    viewModel.updateStep(binding.stepSlider.value.toLong())
                }
                binding.saveButton.isVisible = false
            }
        })

        binding.buttonEdit.setOnClickListener {
            if (binding.buttonEdit.text.toString() == "Edit") {
                binding.buttonEdit.text = "Save"
                binding.textwName.isEnabled = true
                binding.textwDescription.isEnabled = true
            } else {
                binding.buttonEdit.text = "Edit"
                binding.textwName.isEnabled = false
                binding.textwDescription.isEnabled = false
                viewModel.updateThisDayTask(binding.textwName.text.toString(),
                    binding.textwDescription.text.toString())
            }
        }

        binding.buttonDelete.visibility = View.INVISIBLE
        binding.checkboxDone.setOnClickListener {
            if (binding.checkboxDone.isChecked) {
                binding.buttonDelete.visibility = View.VISIBLE
            } else {
                binding.buttonDelete.visibility = View.INVISIBLE
            }
        }

        binding.buttonDelete.setOnClickListener {
            viewModel.deleteThisDayTask()
            val action = ThisDayTaskDetailsShowingFragmentDirections
                .actionThisDayTaskDetailsShowingFragmentToThisDayFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}