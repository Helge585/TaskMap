package com.kuznetsov.taskmap.fragment.thisdaytask

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kuznetsov.taskmap.R
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
            viewModel.updateStep()
            if (it == null) {
                binding.progressButtonsLayout.visibility = View.INVISIBLE
                binding.thisDaySliderLayout.visibility = View.INVISIBLE
                binding.allStepSliderLayout.visibility = View.INVISIBLE
                binding.currentResultLayout.visibility = View.INVISIBLE
                binding.startResultLayout.visibility = View.INVISIBLE
                binding.finishResultLayout.visibility = View.INVISIBLE
                return@Observer
            }
            binding.task = it
            if (it.stepId < 0) {
                binding.progressButtonsLayout.visibility = View.INVISIBLE
                binding.thisDaySliderLayout.visibility = View.INVISIBLE
                binding.allStepSliderLayout.visibility = View.INVISIBLE
                binding.currentResultLayout.visibility = View.INVISIBLE
                binding.startResultLayout.visibility = View.INVISIBLE
                binding.finishResultLayout.visibility = View.INVISIBLE
            }
            if (it.startResult < it.finishResult) {
                binding.thisDayStepSlider.valueFrom = it.startResult.toFloat()
                binding.thisDayStepSlider.value = it.currentResult.toFloat()
                binding.thisDayStepSlider.valueTo = it.finishResult.toFloat()
            }

            binding.thisDayResultPercentText.text =
                MyStringUtils.getPercentText(it.currentResult, it.finishResult)
        })

        viewModel.step?.observe(viewLifecycleOwner, Observer {
            Log.i("ThisDayTaskDetailsShowingFragment", "step updating - ${it}")
            if (it == null) {
                binding.stepSlider.visibility = View.INVISIBLE
//                binding.sliderLayout1.visibility = View.INVISIBLE
//                binding.sliderLayout2.visibility = View.INVISIBLE
//                binding.sliderLayout3.visibility = View.INVISIBLE
                return@Observer
            }

            binding.step = it
            binding.stepSlider.valueFrom = it.startResult.toFloat()
            binding.stepSlider.value = it.currentResult.toFloat()
            binding.stepSlider.valueTo = it.finishResult.toFloat()
            binding.percentText.text = MyStringUtils.getPercentText(it.currentResult, it.finishResult)

            //binding.saveButton.isVisible = false
            binding.plusButton.setOnClickListener {
                val newCurrent = (binding.stepSlider.value + 1).toLong()
                if (newCurrent <= binding.stepSlider.valueTo.toLong()) {
                    binding.stepSlider.value = newCurrent.toFloat()
                    binding.percentText.text = MyStringUtils.getPercentText(newCurrent,
                        binding.stepSlider.valueTo.toLong())
                    val oldResult = binding.twDoneCount.text.toString().toLong()
                    binding.twDoneCount.text = (oldResult + 1).toString()
                }

                val thisDayNewCurrent = (binding.thisDayStepSlider.value + 1).toLong()
                if (thisDayNewCurrent <= binding.thisDayStepSlider.valueTo.toLong()) {
                    binding.thisDayStepSlider.value = thisDayNewCurrent.toFloat()
                    binding.thisDayResultPercentText.text = MyStringUtils.getPercentText(
                        thisDayNewCurrent,
                        binding.thisDayStepSlider.valueTo.toLong()
                    )
                }

                binding.saveButton.setColorFilter(resources.getColor(R.color.lime_green))
            }
            binding.minusButton.setOnClickListener {
                val newCurrent = (binding.stepSlider.value - 1).toLong()
                if (newCurrent >= (binding.stepSlider.valueFrom).toLong()) {
                    binding.stepSlider.value = newCurrent.toFloat()
                    binding.percentText.text = MyStringUtils.getPercentText(newCurrent,
                        binding.stepSlider.valueTo.toLong())
                    val oldResult = binding.twDoneCount.text.toString().toLong()
                    binding.twDoneCount.text = (oldResult - 1).toString()
                }

                val thisDayNewCurrent = (binding.thisDayStepSlider.value - 1).toLong()
                if (thisDayNewCurrent >= binding.thisDayStepSlider.valueFrom.toLong()) {
                    binding.thisDayStepSlider.value = thisDayNewCurrent.toFloat()
                    binding.thisDayResultPercentText.text = MyStringUtils.getPercentText(
                        thisDayNewCurrent,
                        binding.thisDayStepSlider.valueTo.toLong()
                    )
                }

                binding.saveButton.setColorFilter(resources.getColor(R.color.lime_green))
            }
            binding.saveButton.setOnClickListener {
                if (binding.saveButton.isVisible) {
                    viewModel.updateStep(binding.stepSlider.value.toLong())
                    viewModel.updateThisDayTask(
                        binding.textwName.text.toString(),
                        binding.textwDescription.text.toString(),
                        binding.thisDayStepSlider.valueFrom.toLong(),
                        binding.thisDayStepSlider.value.toLong(),
                        binding.thisDayStepSlider.valueTo.toLong(),
                        binding.groupsSpinner.selectedItem.toString()
                    )
                }
                binding.saveButton.setColorFilter(resources.getColor(R.color.grey))
            }
        })

        binding.buttonEdit.setOnClickListener {
            if (!binding.textwName.isEnabled) {
                binding.textwName.isEnabled = true
                binding.textwDescription.isEnabled = true

                binding.etStartResult.isEnabled = true
                binding.etCurrentResult.isEnabled = true
                binding.etFinishResult.isEnabled = true

                binding.groupsSpinner.isEnabled = true

            } else {
                binding.textwName.isEnabled = false
                binding.textwDescription.isEnabled = false

                binding.etStartResult.isEnabled = false
                binding.etCurrentResult.isEnabled = false
                binding.etFinishResult.isEnabled = false

                binding.groupsSpinner.isEnabled = false

                viewModel.updateThisDayTask(
                    binding.textwName.text.toString(),
                    binding.textwDescription.text.toString(),
                    binding.etStartResult.text.toString().toLong(),
                    binding.etCurrentResult.text.toString().toLong(),
                    binding.etFinishResult.text.toString().toLong(),
                    binding.groupsSpinner.selectedItem.toString()
                )
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
            viewModel.step?.removeObservers(viewLifecycleOwner)
            viewModel.deleteThisDayTask()
            val action = ThisDayTaskDetailsShowingFragmentDirections
                .actionThisDayTaskDetailsShowingFragmentToThisDayFragment()
            //findNavController().navigate(action)
            findNavController().popBackStack()
        }

        binding.groupsSpinner.isEnabled = false

        viewModel.groups.observe(viewLifecycleOwner, Observer {
            it?.let {
                val groupsName = mutableListOf<String>()
                var selectIndex = 0
                groupsName.add("None")
                it.forEachIndexed { i, gn ->
                    groupsName.add(gn.name)
                    if (gn.id == viewModel.task.value?.groupId) {
                        selectIndex = i + 1
                    }
                }
                val spinnerAdapter = ArrayAdapter<String>(requireContext(),
                    com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                    groupsName)
                spinnerAdapter.setDropDownViewResource(
                    com.google.android.material.R.layout.support_simple_spinner_dropdown_item)
                binding.groupsSpinner.adapter = spinnerAdapter
                binding.groupsSpinner.setSelection(selectIndex)
            }
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}