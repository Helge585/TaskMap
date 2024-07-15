package com.kuznetsov.taskmap.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kuznetsov.taskmap.R
import com.kuznetsov.taskmap.dao.GoalDatabase
import com.kuznetsov.taskmap.databinding.FragmentCreateSubGoalBinding
import com.kuznetsov.taskmap.viewmodel.CreateMainGoalViewModel
import com.kuznetsov.taskmap.viewmodel.CreateSubGoalViewModel
import com.kuznetsov.taskmap.viewmodel.CreateSubGoalViewModelFactory

class CreateSubGoalFragment : Fragment() {
    private var _binding: FragmentCreateSubGoalBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateSubGoalBinding.inflate(inflater, container, false)

        val application = requireNotNull(activity).application
        val dao = GoalDatabase.getInstance(application).subGoalDao

        val mainGoalId = CreateSubGoalFragmentArgs.fromBundle(requireArguments()).mainGoalId

        val viewModelFactory = CreateSubGoalViewModelFactory(mainGoalId, dao)
        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(CreateSubGoalViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.isNavigatedToSubGoal.observe(viewLifecycleOwner, Observer {
            Log.i("CreateSubGoalFragment", "isNavigatedToSubGoal = $it")
            if (it) {
                val action = CreateSubGoalFragmentDirections
                    .actionCreateSubGoalFragmentToSubGoalFragment(mainGoalId)
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