package com.kuznetsov.taskmap.fragment.thisdaytask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kuznetsov.taskmap.R
import com.kuznetsov.taskmap.adapter.IncrementAdapter
import com.kuznetsov.taskmap.adapter.ThisDayTaskAdapter
import com.kuznetsov.taskmap.dao.GoalDatabase
import com.kuznetsov.taskmap.databinding.FragmentIncrementsShowingBinding
import com.kuznetsov.taskmap.databinding.FragmentThisDayTasksShowingBinding
import com.kuznetsov.taskmap.entity.Increment
import com.kuznetsov.taskmap.fragment.step.IncrementsShowingFragmentArgs
import com.kuznetsov.taskmap.viewmodel.step.IncrementsShowingViewModel
import com.kuznetsov.taskmap.viewmodel.step.IncrementsShowingViewModelFactory
import com.kuznetsov.taskmap.viewmodel.thisdaytask.ThisDayTasksShowingViewModel
import com.kuznetsov.taskmap.viewmodel.thisdaytask.ThisDayTasksShowingViewModelFactory

class ThisDayTasksShowingFragment : Fragment() {

    private var _binding: FragmentThisDayTasksShowingBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentThisDayTasksShowingBinding.inflate(inflater, container, false)
        val view = binding.root

        val application = requireNotNull(activity).application
        val thisDayTaskDao = GoalDatabase.getInstance(application).thisDayTaskDao

        val viewModelFactory = ThisDayTasksShowingViewModelFactory(thisDayTaskDao)
        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ThisDayTasksShowingViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = ThisDayTaskAdapter()

        binding.thisDayTasksList.adapter = adapter
        viewModel.thisDayTasks.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        binding.fab.setOnClickListener {
            viewModel.navigateToThisDayTaskCreating()
        }

        viewModel.isNavigatedToThisDayTaskCreating.observe(viewLifecycleOwner, Observer {
            if (it) {
                val action = ThisDayTasksShowingFragmentDirections
                    .actionThisDayFragmentToThisDayTaskCreatingFragment()
                findNavController().navigate(action)
                viewModel.afterNavigateToThisDayTaskCreating()
            }
        })

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}