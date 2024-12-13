package com.kuznetsov.taskmap.fragment.thisdaytask

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kuznetsov.taskmap.adapter.ThisDayItemAdapter
import com.kuznetsov.taskmap.dao.GoalDatabase
import com.kuznetsov.taskmap.databinding.FragmentThisDayTasksShowingBinding
import com.kuznetsov.taskmap.viewmodel.thisdaytask.ThisDayTasksShowingViewModel
import com.kuznetsov.taskmap.viewmodel.thisdaytask.ThisDayTasksShowingViewModelFactory

private val TAG = "ThisDayTasksShowingFragment"

class ThisDayTasksShowingFragment : Fragment() {

    private var _binding: FragmentThisDayTasksShowingBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "ThisDayTasksShowingFragment - onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("wwwwww", "On Create View")

        _binding = FragmentThisDayTasksShowingBinding.inflate(inflater, container, false)
        val view = binding.root

        val application = requireNotNull(activity).application
        val thisDayTaskDao = GoalDatabase.getInstance(application).thisDayTaskDao

        val viewModelFactory = ThisDayTasksShowingViewModelFactory(thisDayTaskDao)
        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ThisDayTasksShowingViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = ThisDayItemAdapter(
            {
                viewModel.deleteThisDayTask(it)
            },
            {
                viewModel.navigateToStepShowing(it)
            },
            {
                viewModel.updateThisDayTask(it)
            },
            {
                viewModel.navigateToDetailsShowing(it)
            },
            {
                viewModel.navigateToGroupDetailsShowing(it)
            }
        )

        binding.thisDayTasksList.adapter = adapter


        viewModel.thisDayTasks.observe(viewLifecycleOwner, Observer {
            Log.i(TAG, "main page tasks submitted in adapter")
//            adapter.submitList(viewModel.thisDayMainPageItems.value)
//            viewModel.thisDayMainPageItems.value?.forEach {
//                Log.i(TAG, it.toString())
//            }
            if (viewModel.updateMainPageItems()) {
                adapter.submitList(viewModel.thisDayMainPageItems)
                //println("SS " + viewModel.thisDayMainPageItems)
            }
            //adapter.submitList(it)
        })



        viewModel.thisDayGroups.observe(viewLifecycleOwner, Observer {
            Log.i(TAG, "main page groups submitted in adapter")
//            adapter.submitList(viewModel.thisDayMainPageItems.value)
//            viewModel.thisDayMainPageItems.value?.forEach {
//                Log.i(TAG, it.toString())
//            }
            if (viewModel.updateMainPageItems()) {
                adapter.submitList(viewModel.thisDayMainPageItems)
                //adapter.notifyDataSetChanged()
                //println("SS " + viewModel.thisDayMainPageItems)
            }
        })

//        viewModel.thisDayMainPageItems.observe(viewLifecycleOwner, Observer {
//            Log.i(TAG, "main page items submitted in adapter")
//            adapter.submitList(it)
////            it.forEach {
////                Log.i(TAG, it.toString())
////            }
//        })

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

        viewModel.isNavigatedToStepShowing.observe(viewLifecycleOwner, Observer {
            if (it != viewModel.NOT_CHOOSEN_FOR_NAVIGATING) {
                val action = ThisDayTasksShowingFragmentDirections
                    .actionThisDayFragmentToOneStepShowingFragment(it)
                findNavController().navigate(action)
                viewModel.afterNavigateToStepShowing()
            }
        })

        viewModel.isNavigatedToDetailsShowing.observe(viewLifecycleOwner, Observer {
            if (it != viewModel.NOT_CHOOSEN_FOR_NAVIGATING) {
                val action = ThisDayTasksShowingFragmentDirections
                    .actionThisDayFragmentToThisDayTaskDetailsShowingFragment(it)
                findNavController().navigate(action)
                viewModel.afterNavigateToDetailsShowing()
            }
        })

        viewModel.isNavigatedToGroupDetailsShowing.observe(viewLifecycleOwner, Observer {
            if (it != viewModel.NOT_CHOOSEN_FOR_NAVIGATING) {
                val action = ThisDayTasksShowingFragmentDirections
                    .actionThisDayFragmentToThisDayGroupDetailsShowingFragment(it)
                findNavController().navigate(action)
                viewModel.afterNavigateToGroupDetailsShowing()
            }
        })

        return view
    }

    override fun onResume() {
        super.onResume()
        Log.i("wwwwww", "On Resume")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}