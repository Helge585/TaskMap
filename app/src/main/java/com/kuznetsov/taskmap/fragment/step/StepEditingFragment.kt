package com.kuznetsov.taskmap.fragment.step

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kuznetsov.taskmap.R
import com.kuznetsov.taskmap.dao.GoalDatabase
import com.kuznetsov.taskmap.databinding.FragmentStepEditingBinding
import com.kuznetsov.taskmap.viewmodel.step.StepEditingViewModel
import com.kuznetsov.taskmap.viewmodel.step.StepEditingViewModelFactory

const val TAG = "StepEditingFragment"
class StepEditingFragment : Fragment() {
    private var _binding: FragmentStepEditingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStepEditingBinding.inflate(inflater, container, false)
        val view = binding.root

        val stepId = StepEditingFragmentArgs.fromBundle(requireArguments()).stepId

        val application = requireNotNull(activity).application
        val stepDao = GoalDatabase.getInstance(application).stepDao

        val viewModelFactory = StepEditingViewModelFactory(stepDao, stepId)
        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(StepEditingViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.isNavigatedToStepShowingFragment.observe(viewLifecycleOwner, Observer {
            if (it) {
                viewModel.step.value?.let {
                    val action = StepEditingFragmentDirections
                        .actionStepEditingFragmentToStepFragment(it.subGoalId)
                    findNavController().navigate(action)
                }
                viewModel.afterNavigateToStepShowingFragment()
            }
        })


        viewModel.isMadeToast.observe(viewLifecycleOwner, Observer {
            //Log.d(TAG, "isMadeToast, it = $it")
            if (it) {
                Toast.makeText(context, viewModel.ERROR_UPDATING, Toast.LENGTH_SHORT).show()
                viewModel.afterMadeToast()
            }
        })

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}