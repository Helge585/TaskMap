package com.kuznetsov.taskmap

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.findNavController
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
//        val button = view.findViewById<Button>(R.id.main_goal_test_button)
//        val textEdit = view.findViewById<EditText>(R.id.main_goal_test_edit)
//        button.setOnClickListener {
//            val msg = textEdit.text.toString()
//            val action = MainGoalFragmentDirections.actionMainGoalFragmentToSubGoalFragment(msg)
//            view.findNavController().navigate(action)
//        }
        binding.fab.setOnClickListener {
            activity?.let {
                NewMainGoalDialogFragment().show(it.supportFragmentManager, "Test dialog")
            }
        }
        return view
    }
}