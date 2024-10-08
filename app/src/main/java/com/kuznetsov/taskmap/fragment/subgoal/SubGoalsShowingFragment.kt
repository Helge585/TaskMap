package com.kuznetsov.taskmap.fragment.subgoal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kuznetsov.taskmap.viewmodel.subgoal.SubGoalsShowingViewModel
import com.kuznetsov.taskmap.viewmodel.subgoal.SubGoalsShowingViewModelFactory
import com.kuznetsov.taskmap.adapter.SubGoalAdapter
import com.kuznetsov.taskmap.dao.GoalDatabase
import com.kuznetsov.taskmap.databinding.FragmentSubGoalShowingBinding


class SubGoalsShowingFragment : Fragment() {
    private var _binding: FragmentSubGoalShowingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSubGoalShowingBinding.inflate(inflater, container, false)
        val view = binding.root

        val application = requireNotNull(activity).application
        val db = GoalDatabase.getInstance(application)

        val mainGoalId = SubGoalsShowingFragmentArgs.fromBundle(requireArguments()).mainGoalId
        val viewModelFactory = SubGoalsShowingViewModelFactory(db.mainGoalDao, db.subGoalDao, mainGoalId)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(SubGoalsShowingViewModel::class.java)
        binding.viewModel = viewModel

        val adapter = SubGoalAdapter (
            {
                viewModel.navigateToSubGoalEditing(it)
                false
            },
            {
                viewModel.navigateToStepFragment(it)
                false
            }
        )
        binding.subGoalsList.adapter = adapter

        viewModel.subGoals.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.mainGoal.observe(viewLifecycleOwner, Observer {
            binding.subgoalTestText.text = viewModel.mainGoalInfo()
        })

        viewModel.isNavigatedToSubGoalCreating.observe(viewLifecycleOwner, Observer {
            if (it) {
                val action = SubGoalsShowingFragmentDirections.actionSubGoalFragmentToCreateSubGoalFragment(
                    mainGoalId
                )
                findNavController().navigate(action)
                viewModel.afterNavigateToSubGoalCreating()
            }
        })

        viewModel.isNavigatedToSubGoalEditing.observe(viewLifecycleOwner, Observer {
            if (it && viewModel.clickedSubGoalId != viewModel.NOT_CLICKED) {
                val action = SubGoalsShowingFragmentDirections.actionSubGoalFragmentToEditSubGoalFragment(
                    subGoalId = viewModel.clickedSubGoalId
                )
                findNavController().navigate(action)
                viewModel.afterNavigateToSubGoalEditing()
            }
        })

        viewModel.isNavigatedToStepFragment.observe(viewLifecycleOwner, Observer {
            if (it) {
                val action =
                    SubGoalsShowingFragmentDirections.actionSubGoalFragmentToStepFragment(viewModel.clickedSubGoalId)
                findNavController().navigate(action)
                viewModel.afterNavigateToStepFragment()
            }
        })

        viewModel.subGoals.observe(viewLifecycleOwner, Observer {
            viewModel.printSubGoals()
        })

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}