package com.kuznetsov.taskmap.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import com.kuznetsov.taskmap.R
import com.kuznetsov.taskmap.fragment.StepFragmentArgs

class StepFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_step, container, false)

        val textView = view.findViewById<TextView>(R.id.step_test_text)
        val receivedMsg = StepFragmentArgs.fromBundle(requireArguments()).msgFromSubGoal
        textView.text = textView.text.toString() + receivedMsg

        val button = view.findViewById<Button>(R.id.step_test_button)
        button.setOnClickListener {
            view.findNavController().navigate(R.id.action_stepFragment_to_mainGoalFragment)
        }
        return view
    }
}