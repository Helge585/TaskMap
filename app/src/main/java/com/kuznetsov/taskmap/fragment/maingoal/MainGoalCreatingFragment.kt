package com.kuznetsov.taskmap.fragment.maingoal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kuznetsov.taskmap.viewmodel.maingoal.MainGoalCreatingViewModel
import com.kuznetsov.taskmap.viewmodel.maingoal.MainGoalCreatingViewModelFactory
import com.kuznetsov.taskmap.dao.GoalDatabase
import com.kuznetsov.taskmap.R
import com.kuznetsov.taskmap.databinding.FragmentMainGoalCreatingBinding

class MainGoalCreatingFragment : Fragment() {
    private var _binding: FragmentMainGoalCreatingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainGoalCreatingBinding.inflate(inflater, container, false)

        val application = requireNotNull(activity).application
        val dao = GoalDatabase.getInstance(application).mainGoalDao

        val viewModelFactory = MainGoalCreatingViewModelFactory(dao)
        val viewModel = ViewModelProvider(
            this, viewModelFactory).get(MainGoalCreatingViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.navigateToMainGoal.observe(viewLifecycleOwner, Observer {
            if (it) {
                this.findNavController()
                    .navigate(R.id.action_createMainGoalFragment_to_mainGoalFragment)
                viewModel.afterNavigateToMainGoal()
            }
        })

        viewModel.printMainGoals()

//        binding.newMainGoalCancel.setOnClickListener {
//            viewModel.afterNavigateToMainGoal()
////            this.findNavController()
////                .navigate(R.id.action_createMainGoalFragment_to_mainGoalFragment)
//        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}