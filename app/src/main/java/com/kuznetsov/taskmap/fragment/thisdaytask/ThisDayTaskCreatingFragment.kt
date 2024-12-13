package com.kuznetsov.taskmap.fragment.thisdaytask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kuznetsov.taskmap.R
import com.kuznetsov.taskmap.dao.GoalDatabase
import com.kuznetsov.taskmap.databinding.FragmentThisDayTaskCreatingBinding
import com.kuznetsov.taskmap.viewmodel.thisdaytask.ThisDayTaskCreatingViewModel
import com.kuznetsov.taskmap.viewmodel.thisdaytask.ThisDayTaskCreatingViewModelFactory

class ThisDayTaskCreatingFragment : Fragment() {
    private var _binding: FragmentThisDayTaskCreatingBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentThisDayTaskCreatingBinding.inflate(layoutInflater, container, false)

        val application = requireNotNull(activity).application
        val thisDayTaskDao = GoalDatabase.getInstance(application).thisDayTaskDao

        val viewModelFactory = ThisDayTaskCreatingViewModelFactory(thisDayTaskDao)
        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(ThisDayTaskCreatingViewModel::class.java)

        binding.thisDayTaskSaveButton.setOnClickListener {
            viewModel.saveThisDayTask()
            val action = ThisDayTaskCreatingFragmentDirections
                .actionThisDayTaskCreatingFragmentToThisDayFragment()
            findNavController().navigate(action)
        }

        binding.thisDayGroupSaveButton.setOnClickListener {
            viewModel.saveThisDayGroup()
            val action = ThisDayTaskCreatingFragmentDirections
                .actionThisDayTaskCreatingFragmentToThisDayFragment()
            findNavController().navigate(action)
        }

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}