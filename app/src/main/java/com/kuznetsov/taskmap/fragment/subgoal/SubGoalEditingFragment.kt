package com.kuznetsov.taskmap.fragment.subgoal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kuznetsov.taskmap.dao.GoalDatabase
import com.kuznetsov.taskmap.databinding.FragmentSubGoalEditingBinding
import com.kuznetsov.taskmap.viewmodel.subgoal.SubGoalEditingViewModel
import com.kuznetsov.taskmap.viewmodel.subgoal.SubGoalEditingViewModelFactory


class SubGoalEditingFragment : Fragment() {
    private var _binding: FragmentSubGoalEditingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentSubGoalEditingBinding.inflate(inflater, container, false)

        val application = requireNotNull(activity).application
        val subGoalDao = GoalDatabase.getInstance(application).subGoalDao

        val subGoalId = SubGoalEditingFragmentArgs.fromBundle(requireArguments()).subGoalId

        val viewModelFactory = SubGoalEditingViewModelFactory(subGoalId, subGoalDao)
        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(SubGoalEditingViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.isNavigatedToSubGoal.observe(viewLifecycleOwner, Observer {
            if (it) {
                viewModel.subGoal.value?.let {
                    val action =
                        SubGoalEditingFragmentDirections.actionEditSubGoalFragmentToSubGoalFragment(it.mainGoalId)
                    findNavController().navigate(action)
                    viewModel.afterNavigateToSubGoal()
                }
            }
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}