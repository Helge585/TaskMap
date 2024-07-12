package com.kuznetsov.taskmap

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kuznetsov.taskmap.databinding.FragmentEditMainGoalBinding


class EditMainGoalFragment : Fragment() {
    private var _binding: FragmentEditMainGoalBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEditMainGoalBinding.inflate(inflater, container, false)
        val view = binding.root

        val application = requireNotNull(activity).application
        val dao = GoalDatabase.getInstance(application).mainGoalDao


        val id = EditMainGoalFragmentArgs.fromBundle(requireArguments()).mainGoalId
        val viewModelFactory = EditMainGoalViewModelFactory(dao, id)
        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(EditMainGoalViewModel::class.java)

//        var strInfo = "viewModel info is ${viewModel.info()}"
//        strInfo += ", id = $id"
//        binding.editMainGoalTextView.text = strInfo

//        binding.editMainGoalUpdate.setOnClickListener {
//            viewModel.update()
//        }

        viewModel.mainGoal.observe(viewLifecycleOwner, Observer {
            binding.editMainGoalTextView.text = "!!! ${ viewModel.info() }"
        })

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.printMainGoals()

        return view
    }
}