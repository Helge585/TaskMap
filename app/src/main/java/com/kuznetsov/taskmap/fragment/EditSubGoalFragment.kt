package com.kuznetsov.taskmap.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.kuznetsov.taskmap.R
import com.kuznetsov.taskmap.dao.GoalDatabase
import com.kuznetsov.taskmap.databinding.FragmentEditSubGoalBinding
import com.kuznetsov.taskmap.databinding.FragmentSubGoalBinding
import com.kuznetsov.taskmap.viewmodel.EditSubGoalViewModel
import com.kuznetsov.taskmap.viewmodel.EditSubGoalViewModelFactory


class EditSubGoalFragment : Fragment() {
    private var _binding: FragmentEditSubGoalBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentEditSubGoalBinding.inflate(inflater, container, false)

        val application = requireNotNull(activity).application
        val subGoalDao = GoalDatabase.getInstance(application).subGoalDao

        val subGoalId = EditSubGoalFragmentArgs.fromBundle(requireArguments()).subGoalId

        val viewModelFactory = EditSubGoalViewModelFactory(subGoalId, subGoalDao)
        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(EditSubGoalViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}