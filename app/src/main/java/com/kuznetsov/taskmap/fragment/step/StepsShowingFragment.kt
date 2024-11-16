package com.kuznetsov.taskmap.fragment.step

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kuznetsov.taskmap.adapter.StepAdapter
import com.kuznetsov.taskmap.dao.GoalDatabase
import com.kuznetsov.taskmap.databinding.FragmentStepShowingBinding
import com.kuznetsov.taskmap.entity.Step
import com.kuznetsov.taskmap.viewmodel.step.StepsShowingViewModel
import com.kuznetsov.taskmap.viewmodel.step.StepsShowingViewModelFactory

class StepsShowingFragment : Fragment() {
    private var _binding: FragmentStepShowingBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Log.i("StepFragment", "!!!!!      Step Fragment has been created")
        _binding = FragmentStepShowingBinding.inflate(inflater, container, false)
        val view = binding.root

        val subGoalId = StepsShowingFragmentArgs.fromBundle(requireArguments()).subGoalId

        val application = requireNotNull(activity).application
        val db = GoalDatabase.getInstance(application)

        val viewModelFactory = StepsShowingViewModelFactory(db.stepDao, db.subGoalDao,
            db.thisDayTaskDao, subGoalId)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(StepsShowingViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = StepAdapter (
            {
                viewModel.navigateToStepEditing(it)
                false
            },
            {current: Long, finish: Long ->
                viewModel.getPercentText(current, finish)
            },
            {step: Step, newCurrentResult: Long ->
                viewModel.updateStep(step, newCurrentResult)
            },
            {stepId: Long ->
                viewModel.navigateToIncrementsShowing(stepId)
                false
            },
            {
                viewModel.addToThisDay(it)
            }
        )
        binding.stepsList.adapter = adapter
        viewModel.steps.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        viewModel.isNavigatedToStepCreating.observe(viewLifecycleOwner, Observer {
            if (it) {
                val action =
                    StepsShowingFragmentDirections.actionStepFragmentToStepCreatingFragment(subGoalId)
                findNavController().navigate(action)
                viewModel.afterNavigateToStepCreating()
            }
        })

        viewModel.isNavigatedToStepEditing.observe(viewLifecycleOwner, Observer {
            if (it != viewModel.NOT_CHOOSEN_FOR_NAVIGATING) {
                val action = StepsShowingFragmentDirections
                    .actionStepFragmentToStepEditingFragment(it)
                findNavController().navigate(action)
                viewModel.afterNavigateToStepEditing()
            }
        })

        viewModel.isNavigatedToIncrementsShowing.observe(viewLifecycleOwner, Observer {
            if (it != viewModel.NOT_CHOOSEN_FOR_NAVIGATING) {
                val action = StepsShowingFragmentDirections
                    .actionStepFragmentToIncrementsShowingFragment(it)
                findNavController().navigate(action)
                viewModel.afterNavigateToIncrementsShowing()
            }
        })

        viewModel.subGoal.observe(viewLifecycleOwner, Observer {
            binding.subgoalTestText.text = viewModel.subGoalInfo()
        })

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}