package com.kuznetsov.taskmap

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


class EditMainGoalFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_edit_main_goal, container, false)

        val text = view.findViewById<TextView>(R.id.edit_main_goal_text_view)
        val id = EditMainGoalFragmentArgs.fromBundle(requireArguments()).mainGoalId
        text.text = id.toString()

        return view
    }
}