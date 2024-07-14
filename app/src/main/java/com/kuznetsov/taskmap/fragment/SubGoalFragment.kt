package com.kuznetsov.taskmap.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kuznetsov.taskmap.fragment.SubGoalFragmentArgs
import com.kuznetsov.taskmap.viewmodel.SubGoalViewModel
import com.kuznetsov.taskmap.viewmodel.SubGoalViewModelFactory
import com.kuznetsov.taskmap.adapter.SubGoalAdapter
import com.kuznetsov.taskmap.dao.GoalDatabase
import com.kuznetsov.taskmap.databinding.FragmentSubGoalBinding


class SubGoalFragment : Fragment() {
    private var _binding: FragmentSubGoalBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSubGoalBinding.inflate(inflater, container, false)
        val view = binding.root

        val application = requireNotNull(activity).application
        val db = GoalDatabase.getInstance(application)

        val mainGoalId = SubGoalFragmentArgs.fromBundle(requireArguments()).mainGoalId
        val viewModelFactory = SubGoalViewModelFactory(db.mainGoalDao, db.subGoalDao, mainGoalId)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(SubGoalViewModel::class.java)

        val adapter = SubGoalAdapter()
        binding.subGoalsList.adapter = adapter

        viewModel.subGoals.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.mainGoal.observe(viewLifecycleOwner, Observer {
            binding.subgoalTestText.text = viewModel.mainGoalInfo()
        })
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}