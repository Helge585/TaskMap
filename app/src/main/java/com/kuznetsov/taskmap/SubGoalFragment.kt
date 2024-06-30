package com.kuznetsov.taskmap

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.findNavController


class SubGoalFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sub_goal, container, false)

        val receivedMsg = SubGoalFragmentArgs.fromBundle(requireArguments()).msgFromMainGoal
        val textView = view.findViewById<TextView>(R.id.subgoal_test_text)
        textView.text = textView.text.toString() + receivedMsg

        val button = view.findViewById<Button>(R.id.subgoal_test_button)
        val textEdit = view.findViewById<EditText>(R.id.subgoal_test_edit)
        button.setOnClickListener {
            val msg = textEdit.text.toString()
            val action = SubGoalFragmentDirections.actionSubGoalFragmentToStepFragment(msg)
            view.findNavController().navigate(action)
        }
        return view
    }
}