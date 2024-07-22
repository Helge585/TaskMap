package com.kuznetsov.taskmap.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.kuznetsov.taskmap.R
import com.kuznetsov.taskmap.dao.GoalDatabase
import com.kuznetsov.taskmap.databinding.FragmentStepBinding
import com.kuznetsov.taskmap.fragment.StepFragmentArgs
import com.kuznetsov.taskmap.viewmodel.StepViewModel
import com.kuznetsov.taskmap.viewmodel.StepViewModelFactory

class StepFragment : Fragment() {
    private var _binding: FragmentStepBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.i("StepFragment", "!!!!!      Step Fragment has been created")
        _binding = FragmentStepBinding.inflate(inflater, container, false)
        val view = binding.root

        val subGoalId = StepFragmentArgs.fromBundle(requireArguments()).subGoalId

        val application = requireNotNull(activity).application
        val db = GoalDatabase.getInstance(application)

        val viewModelFactory = StepViewModelFactory(db.stepDao, db.subGoalDao, subGoalId)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(StepViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.isNavigatedToStepCreating.observe(viewLifecycleOwner, Observer {
            if (it) {
                val action = StepFragmentDirections
                    .actionStepFragmentToStepCreatingFragment(subGoalId)
                findNavController().navigate(action)
                viewModel.afterNavigateToStepCreating()
            }
        })

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}