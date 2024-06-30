package com.kuznetsov.taskmap

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.findNavController

class MainGoalFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_main_goal, container, false)
        val button = view.findViewById<Button>(R.id.main_goal_test_button)
        val textEdit = view.findViewById<EditText>(R.id.main_goal_test_edit)
        button.setOnClickListener {
            val msg = textEdit.text.toString()
            val action = MainGoalFragmentDirections.actionMainGoalFragmentToSubGoalFragment(msg)
            view.findNavController().navigate(action)
        }
        return view
    }
}