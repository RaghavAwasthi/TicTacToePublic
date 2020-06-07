package com.rvai.tictactoe

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.rvai.tictactoe.databinding.ActivityModeSelectBinding
import com.rvai.tictactoe.dialogs.RoomCreateDialog

class ModeSelectActivity : AppCompatActivity(), View.OnClickListener {
    lateinit  var binding: ActivityModeSelectBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModeSelectBinding.inflate(layoutInflater);
        setContentView(binding.root)
        binding.createroom.setOnClickListener(this)
        binding.joinroom.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.createroom -> {
                val i = Intent(this, MultiplayerActivity::class.java)
                startActivity(i)
            }
            R.id.joinroom -> showdialog()
        }
    }

    private fun showdialog() {
        val dialog = RoomCreateDialog()
        dialog.setListener { moID ->
            val i = Intent(applicationContext, MultiplayerActivity::class.java)
            i.putExtra(Constants.ROOM_ID, moID)
            startActivity(i)
        }
        dialog.show(supportFragmentManager, "RoomCreateDialog")
    }
}