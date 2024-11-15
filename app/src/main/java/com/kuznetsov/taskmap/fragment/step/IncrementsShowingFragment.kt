package com.kuznetsov.taskmap.fragment.step

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kuznetsov.taskmap.R
import com.kuznetsov.taskmap.adapter.IncrementAdapter
import com.kuznetsov.taskmap.adapter.StepAdapter
import com.kuznetsov.taskmap.dao.GoalDatabase
import com.kuznetsov.taskmap.databinding.FragmentIncrementsShowingBinding
import com.kuznetsov.taskmap.databinding.FragmentStepCreatingBinding
import com.kuznetsov.taskmap.databinding.FragmentStepShowingBinding
import com.kuznetsov.taskmap.entity.Increment
import com.kuznetsov.taskmap.entity.Step
import com.kuznetsov.taskmap.viewmodel.step.IncrementsShowingViewModel
import com.kuznetsov.taskmap.viewmodel.step.IncrementsShowingViewModelFactory
import com.kuznetsov.taskmap.viewmodel.step.StepCreatingViewModel
import com.kuznetsov.taskmap.viewmodel.step.StepCreatingViewModelFactory


class IncrementsShowingFragment : Fragment() {
    private var _binding: FragmentIncrementsShowingBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIncrementsShowingBinding.inflate(inflater, container, false)
        val view = binding.root

        val application = requireNotNull(activity).application
        val stepDao = GoalDatabase.getInstance(application).stepDao

        val stepId = IncrementsShowingFragmentArgs.fromBundle(requireArguments()).stepId

        val viewModelFactory = IncrementsShowingViewModelFactory(stepDao, stepId)
        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(IncrementsShowingViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = IncrementAdapter {
            increment: Increment ->
                viewModel.deleteIncrement(increment)
        }

        binding.incrementsList.adapter = adapter
        viewModel.increments.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        viewModel.step.observe(viewLifecycleOwner, Observer {
            binding.incrementsTitle.text = viewModel.getStepInfo()
        })

        return view
    }
}