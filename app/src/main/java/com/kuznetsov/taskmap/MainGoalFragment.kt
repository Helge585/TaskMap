package com.kuznetsov.taskmap

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.kuznetsov.taskmap.databinding.FragmentMainGoalBinding

class MainGoalFragment : Fragment() {

    private var _binding: FragmentMainGoalBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainGoalBinding.inflate(inflater, container, false)
        val view = binding.root

        val application = requireNotNull(this.activity).application
        val dao = GoalDatabase.getInstance(application).mainGoalDao
        Log.i("MainGoalFragment", "dao is ${dao.toString()}")

        val viewModelFactory = MainGoalViewModelFactory(dao)
        val viewModel = ViewModelProvider(
            this, viewModelFactory).get(MainGoalViewModel::class.java)

        val adapter = MainGoalAdapter {
            viewModel.navigateToEditing(it)
            false
        }
        binding.mainGoalsList.adapter = adapter

        viewModel.mainGoals.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.navigateToCreating.observe(viewLifecycleOwner, Observer {
            if (it) {
                this.findNavController()
                    .navigate(R.id.action_mainGoalFragment_to_createMainGoalFragment)
                viewModel.afterNavigateToCreating()
            }
        })

        viewModel.navigateToEditing.observe(viewLifecycleOwner, Observer {
            if (it) {
                val action = MainGoalFragmentDirections
                    .actionMainGoalFragmentToEditMainGoalFragment(viewModel.clickedMainGoal.id)
                this.findNavController().navigate(action)
                viewModel.afterNavigateToEditing()
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