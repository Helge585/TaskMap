package com.kuznetsov.taskmap

import android.os.Bundle
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
        val viewModelFactory = MainGoalViewModelFactory(dao)
        val viewModel = ViewModelProvider(
            this, viewModelFactory).get(MainGoalViewModel::class.java)

//        viewModel.navigateToCreating.observe(viewLifecycleOwner, Observer {
//            if (it) {
//                this.findNavController()
//                    .navigate(R.id.action_mainGoalFragment_to_createMainGoalFragment)
//                viewModel.beforeNavigateToCreating()
//            }
//        })

        binding.fab.setOnClickListener {
            this.findNavController()
                    .navigate(R.id.action_mainGoalFragment_to_creating)
            //viewModel.afterNavigateToCreating()
        }
        return view
    }
}