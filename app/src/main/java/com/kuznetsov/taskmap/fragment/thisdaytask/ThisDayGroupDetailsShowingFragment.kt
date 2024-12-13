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
import com.kuznetsov.taskmap.adapter.ThisDayItemAdapter
import com.kuznetsov.taskmap.dao.GoalDatabase
import com.kuznetsov.taskmap.databinding.FragmentThisDayGroupDetailsShowingBinding
import com.kuznetsov.taskmap.viewmodel.thisdaytask.ThisDayGroupDetailsShowingViewModel
import com.kuznetsov.taskmap.viewmodel.thisdaytask.ThisDayGroupDetailsShowingViewModelFactory


class ThisDayGroupDetailsShowingFragment : Fragment() {

    private var _binding: FragmentThisDayGroupDetailsShowingBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentThisDayGroupDetailsShowingBinding.inflate(inflater,
            container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        val application = requireNotNull(activity).application
        val db = GoalDatabase.getInstance(application)

        val groupId = ThisDayGroupDetailsShowingFragmentArgs.fromBundle(requireArguments()).thisDayGroupId

        val viewModelFactory = ThisDayGroupDetailsShowingViewModelFactory(groupId, db)
        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ThisDayGroupDetailsShowingViewModel::class.java)

        viewModel.group.observe(viewLifecycleOwner, Observer {
            binding.group = it
        })

        binding.buttonDelete.setOnClickListener {
            viewModel.deleteGroup()
            val action = ThisDayGroupDetailsShowingFragmentDirections
                .actionThisDayGroupDetailsShowingFragmentToThisDayFragment()
            findNavController().navigate(action)
        }

        binding.buttonEdit.setOnClickListener {
            if (binding.groupName.isEnabled) {
                viewModel.updateGroup()
                binding.groupName.isEnabled = false
            } else {
                binding.groupName.isEnabled = true
            }
        }
        val adapter = ThisDayItemAdapter(
            {
                //viewModel.deleteThisDayTask(it)
            },
            {
                //viewModel.navigateToStepShowing(it)
            },
            {
                //viewModel.updateThisDayTask(it)
            },
            {
                viewModel.navigateToTaskDetailsShowing(it)
            },
            {
                //viewModel.navigateToGroupDetailsShowing(it)
            }
        )

        binding.groupsList.adapter = adapter

        viewModel.tasks.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        viewModel.isNavigatedTiTaskDetailsShow.observe(viewLifecycleOwner, Observer {
            if (it != viewModel.NOT_CHOOSEN_FOR_NAVIGATING) {
                val action = ThisDayGroupDetailsShowingFragmentDirections
                    .actionThisDayGroupDetailsShowingFragmentToThisDayTaskDetailsShowingFragment(it)
                findNavController().navigate(action)
                viewModel.afterNavigateToTaskDetailsShowing()
            }
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}