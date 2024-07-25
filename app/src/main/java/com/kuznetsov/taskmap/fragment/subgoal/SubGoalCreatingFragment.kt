package com.kuznetsov.taskmap.fragment.subgoal

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kuznetsov.taskmap.dao.GoalDatabase
import com.kuznetsov.taskmap.databinding.FragmentSubGoalCreatingBinding
import com.kuznetsov.taskmap.viewmodel.subgoal.SubGoalCreatingViewModel
import com.kuznetsov.taskmap.viewmodel.subgoal.SubGoalCreatingViewModelFactory

class SubGoalCreatingFragment : Fragment() {
    private var _binding: FragmentSubGoalCreatingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSubGoalCreatingBinding.inflate(inflater, container, false)

        val application = requireNotNull(activity).application
        val dao = GoalDatabase.getInstance(application).subGoalDao

        val mainGoalId = SubGoalCreatingFragmentArgs.fromBundle(requireArguments()).mainGoalId

        val viewModelFactory = SubGoalCreatingViewModelFactory(mainGoalId, dao)
        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(SubGoalCreatingViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.isNavigatedToSubGoal.observe(viewLifecycleOwner, Observer {
            Log.i("CreateSubGoalFragment", "isNavigatedToSubGoal = $it")
            if (it) {
                val action =
                    SubGoalCreatingFragmentDirections.actionCreateSubGoalFragmentToSubGoalFragment(
                        mainGoalId
                    )
                findNavController().navigate(action)
                viewModel.afterNavigateToSubGoal()
            }
        })


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}