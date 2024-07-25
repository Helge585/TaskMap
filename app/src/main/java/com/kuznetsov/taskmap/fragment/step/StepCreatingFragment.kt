package com.kuznetsov.taskmap.fragment.step

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kuznetsov.taskmap.R
import com.kuznetsov.taskmap.dao.GoalDatabase
import com.kuznetsov.taskmap.databinding.FragmentStepCreatingBinding
import com.kuznetsov.taskmap.viewmodel.step.StepCreatingViewModel
import com.kuznetsov.taskmap.viewmodel.step.StepCreatingViewModelFactory
import com.kuznetsov.taskmap.viewmodel.step.StepsShowingViewModelFactory

class StepCreatingFragment : Fragment() {
    private var _binding: FragmentStepCreatingBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStepCreatingBinding.inflate(inflater, container, false)
        val view = binding.root

        val application = requireNotNull(activity).application
        val stepDao = GoalDatabase.getInstance(application).stepDao

        val subGoalId = StepCreatingFragmentArgs.fromBundle(requireArguments()).subGoalId

        val viewModelFactory = StepCreatingViewModelFactory(stepDao, subGoalId)
        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(StepCreatingViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.isNavigatedToStepsShowing.observe(viewLifecycleOwner, Observer {
            if (it) {
                val action = StepCreatingFragmentDirections
                    .actionStepCreatingFragmentToStepFragment(subGoalId)
                findNavController().navigate(action)
                viewModel.afterNavigateToStepsShowing()
            }
        })

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}