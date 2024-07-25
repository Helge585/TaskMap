package com.kuznetsov.taskmap.fragment.maingoal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kuznetsov.taskmap.viewmodel.maingoal.MainGoalEditingViewModel
import com.kuznetsov.taskmap.viewmodel.maingoal.MainGoalEditingViewModelFactory
import com.kuznetsov.taskmap.dao.GoalDatabase
import com.kuznetsov.taskmap.R
import com.kuznetsov.taskmap.databinding.FragmentMainGoalEditingBinding

//import com.kuznetsov.taskmap.fragment.EditMainGoalFragmentArgs


class MainGoalEditingFragment : Fragment() {
    private var _binding: FragmentMainGoalEditingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainGoalEditingBinding.inflate(inflater, container, false)
        val view = binding.root

        val application = requireNotNull(activity).application
        val dao = GoalDatabase.getInstance(application).mainGoalDao


        val id = MainGoalEditingFragmentArgs.fromBundle(requireArguments()).mainGoalId
        val viewModelFactory = MainGoalEditingViewModelFactory(dao, id)
        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(MainGoalEditingViewModel::class.java)

        viewModel.mainGoal.observe(viewLifecycleOwner, Observer {
            binding.editMainGoalTextView.text = viewModel.info()
        })

        viewModel.isNavigateToMainGoal.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    findNavController()
                        .navigate(R.id.action_editMainGoalFragment_to_mainGoalFragment)
                    viewModel.afterNavigateToMainGoal()
                }
            }
        })

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}