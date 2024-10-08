package com.kuznetsov.taskmap.fragment.maingoal

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kuznetsov.taskmap.viewmodel.maingoal.MainGoalsShowingViewModel
import com.kuznetsov.taskmap.viewmodel.maingoal.MainGoalsShowingViewModelFactory
import com.kuznetsov.taskmap.R
import com.kuznetsov.taskmap.adapter.MainGoalAdapter
import com.kuznetsov.taskmap.dao.GoalDatabase
import com.kuznetsov.taskmap.databinding.FragmentMainGoalShowingBinding

class MainGoalsShowingFragment : Fragment() {

    private var _binding: FragmentMainGoalShowingBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainGoalShowingBinding.inflate(inflater, container, false)
        val view = binding.root

        val application = requireNotNull(this.activity).application
        val dao = GoalDatabase.getInstance(application).mainGoalDao
        Log.i("MainGoalFragment", "dao is ${dao.toString()}")

        val viewModelFactory = MainGoalsShowingViewModelFactory(dao)
        val viewModel = ViewModelProvider(
            this, viewModelFactory).get(MainGoalsShowingViewModel::class.java)

        val adapter = MainGoalAdapter (
            {
                viewModel.navigateToEditing(it)
                false
            },
            {
                viewModel.navigateToSubGoals(it)
                false
            }
        )
        binding.mainGoalsList.adapter = adapter

        viewModel.mainGoals.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.isNavigatedToCreating.observe(viewLifecycleOwner, Observer {
            if (it) {
                this.findNavController()
                    .navigate(R.id.action_mainGoalFragment_to_createMainGoalFragment)
                viewModel.afterNavigateToCreating()
            }
        })

        viewModel.isNavigatedToEditing.observe(viewLifecycleOwner, Observer {
            if (it) {
                val action =
                    MainGoalsShowingFragmentDirections.actionMainGoalFragmentToEditMainGoalFragment(
                        viewModel.clickedMainGoal.id
                    )
                this.findNavController().navigate(action)
                viewModel.afterNavigateToEditing()
            }
        })

        viewModel.isNavigatedToSubGoals.observe(viewLifecycleOwner, Observer {
            if (it) {
                val action =
                    MainGoalsShowingFragmentDirections.actionMainGoalFragmentToSubGoalFragment((viewModel.clickedMainGoal.id))
                findNavController().navigate(action)
                viewModel.afterNavigateToSubGoals()
            }
        })
//        viewModel.mainGoals.observe(viewLifecycleOwner, Observer {
//            var sb = StringBuilder("")
//            for (mg in it) {
//                sb.append("id = ${ mg.id }, name = ${ mg.name }\n")
//            }
//            binding.mainGoalFragmentTextView.text = "Main goals:\n" +  sb.toString()
//        })

        binding.fab.setOnClickListener {
//            this.findNavController()
//                    .navigate(R.id.action_mainGoalFragment_to_creating)
            viewModel.navigateToCreating()
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}