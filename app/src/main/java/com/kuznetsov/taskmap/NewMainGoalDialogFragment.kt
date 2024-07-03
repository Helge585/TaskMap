package com.kuznetsov.taskmap

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class NewMainGoalDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater;

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(inflater.inflate(R.layout.fragment_dialog_new_main_goal, null))
                // Add action buttons
                .setPositiveButton("Сохранить",
                    DialogInterface.OnClickListener { dialog, id ->
                        Log.i("NewMainGoalDialogFragment", "Кнопка сохранить")
                })
                .setNegativeButton("Отмена",
                    DialogInterface.OnClickListener { dialog, id ->
                        Log.i("NewMainGoalDialogFragment", "Кнопка отмена")
                        dialog.cancel()
                })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}