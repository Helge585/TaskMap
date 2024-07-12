package com.kuznetsov.taskmap

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kuznetsov.taskmap.databinding.FragmentCreateMainGoalBinding

class CreateMainGoalFragment : Fragment() {
    private var _binding: FragmentCreateMainGoalBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateMainGoalBinding.inflate(inflater, container, false)

        val application = requireNotNull(activity).application
        val dao = GoalDatabase.getInstance(application).mainGoalDao

        val viewModelFactory = CreateMainGoalViewModelFactory(dao)
        val viewModel = ViewModelProvider(
            this, viewModelFactory).get(CreateMainGoalViewModel::class.java)

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
}