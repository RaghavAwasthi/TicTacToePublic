package com.rvai.tictactoe.dialogs

import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import com.rvai.tictactoe.R

class RoomCreateDialog : AppCompatDialogFragment() {
    private var editTextUsername: EditText? = null
    private var listener: ExampleDialogListener? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.layout_dialog, null)
        builder.setView(view)
                .setTitle("Enter Room ID ")
                .setNegativeButton("CANCEL") { dialogInterface, i -> }
                .setPositiveButton("OK") { dialogInterface, i ->
                    val username = editTextUsername!!.text.toString()
                    //  String password = editTextPassword.getText().toString();
                    listener!!.applyTexts(username)
                }
        editTextUsername = view.findViewById(R.id.edit_username)
        // editTextPassword = view.findViewById(R.id.edit_password);
        return builder.create()
    }

    fun setListener(listener: ExampleDialogListener?) {
        this.listener = listener
    }

    interface ExampleDialogListener {
        fun applyTexts(moID: String?)
    }
}