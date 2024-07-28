package com.kuznetsov.taskmap.fragment.step

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.kuznetsov.taskmap.R
import com.kuznetsov.taskmap.dao.GoalDatabase
import com.kuznetsov.taskmap.databinding.FragmentStepEditingBinding
import com.kuznetsov.taskmap.viewmodel.step.StepEditingViewModel
import com.kuznetsov.taskmap.viewmodel.step.StepEditingViewModelFactory


class StepEditingFragment : Fragment() {
    private var _binding: FragmentStepEditingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStepEditingBinding.inflate(inflater, container, false)
        val view = binding.root

        val stepId = StepEditingFragmentArgs.fromBundle(requireArguments()).stepId

        val application = requireNotNull(activity).application
        val stepDao = GoalDatabase.getInstance(application).stepDao

        val viewModelFactory = StepEditingViewModelFactory(stepDao, stepId)
        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(StepEditingViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}