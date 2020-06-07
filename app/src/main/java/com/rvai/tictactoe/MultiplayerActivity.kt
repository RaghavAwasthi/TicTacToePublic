package com.rvai.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.rvai.tictactoe.databinding.ActivityMultiplayerBinding
import com.rvai.tictactoe.models.RoomModel
import com.rvai.tictactoe.utils.RoomUtils
import com.rvai.tictactoe.utils.TimeUtils
import java.util.*

class MultiplayerActivity : AppCompatActivity(), View.OnClickListener {
    var RoomID: String? = null
    var db = FirebaseDatabase.getInstance()
    var game_state = 0
    var list: MutableList<AppCompatImageButton> = ArrayList()
    var binding: ActivityMultiplayerBinding? = null
    var matrix = longArrayOf(-1, -1, -1, -1, -1, -1, -1, -1, -1) // Our MAtrix
    val PLAYER1 = 0
    val PLAYER2 = 1
    var turn: Long = 0
    var player = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMultiplayerBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        val i = intent
        if (i.extras == null) {
            RoomID = RoomUtils.generateRoomID()
            player = PLAYER1
            createRoom()
        } else {
            player = PLAYER2
            RoomID = i.extras!![Constants.ROOM_ID] as String?
        }
        attachListeners()
        binding!!.roomId.text = "RoomId$RoomID"
        // Adding all the ImageButtons to the list
        list.add(binding!!.button1)
        list.add(binding!!.button2)
        list.add(binding!!.button3)
        list.add(binding!!.button4)
        list.add(binding!!.button5)
        list.add(binding!!.button6)
        list.add(binding!!.button7)
        list.add(binding!!.button8)
        list.add(binding!!.button9)

        // Adding OnClick Listeners
        binding!!.button1.setOnClickListener(this)
        binding!!.button2.setOnClickListener(this)
        binding!!.button3.setOnClickListener(this)
        binding!!.button4.setOnClickListener(this)
        binding!!.button5.setOnClickListener(this)
        binding!!.button6.setOnClickListener(this)
        binding!!.button7.setOnClickListener(this)
        binding!!.button8.setOnClickListener(this)
        binding!!.button9.setOnClickListener(this)
        binding!!.startGame.setOnClickListener(this)
        binding!!.PlayAgain.setOnClickListener(this)
        binding!!.startGame.isEnabled = true
        binding!!.PlayAgain.isEnabled = false
        binding!!.displayTv.visibility = View.GONE
    }

    override fun onClick(v: View) {
        if (gameStarted) {
            when (v.id) {
                R.id.button1 -> {
                    updatematrix(0)
                    updateViews()
                    checkWin()
                }
                R.id.button2 -> {
                    updatematrix(1)
                    updateViews()
                    checkWin()
                }
                R.id.button3 -> {
                    updatematrix(2)
                    updateViews()
                    checkWin()
                }
                R.id.button4 -> {
                    updatematrix(3)
                    updateViews()
                    checkWin()
                }
                R.id.button5 -> {
                    updatematrix(4)
                    updateViews()
                    checkWin()
                }
                R.id.button6 -> {
                    updatematrix(5)
                    updateViews()
                    checkWin()
                }
                R.id.button7 -> {
                    updatematrix(6)
                    updateViews()
                    checkWin()
                }
                R.id.button8 -> {
                    updatematrix(7)
                    updateViews()
                    checkWin()
                }
                R.id.button9 -> {
                    updatematrix(8)
                    updateViews()
                    checkWin()
                }
                R.id.PlayAgain -> resetGameProperties()
            }
        } else {
            if (v.id == R.id.startGame) {
                startGame()
            }
        }
    }

    private fun endGame() {
        if (turn == 1L) {
            Toast.makeText(this, "Player " + 2 + " Won", Toast.LENGTH_SHORT).show()
        } else if (turn == 2L) {
            Toast.makeText(this, "Player " + 1 + " Won", Toast.LENGTH_SHORT).show()
        }
        resetGameProperties()
    }

    var count = 0
    var gameStarted = false
    private fun startGame() {
        gameStarted = true
        turn = 1
        binding!!.startGame.isEnabled = false
        binding!!.PlayAgain.isEnabled = true
        binding!!.displayTv.visibility = View.VISIBLE
    }

    private fun resetGameProperties() {
        matrix = longArrayOf(-1, -1, -1, -1, -1, -1, -1, -1, -1)
        updateViews()
        turn = 1
        count = 0
        gameStarted = false
        binding!!.startGame.isEnabled = true
        binding!!.PlayAgain.isEnabled = false
        binding!!.displayTv.text = "Player 1 Turn"
        binding!!.displayTv.visibility = View.GONE
    }

    private fun updatematrix(index: Int) {
        updateGameState()
        if (turn == player.toLong()) if (matrix[index] == -1.toLong()) {
            count++
            if (turn == 1L) {
                matrix[index] = PLAYER1.toLong()
                turn = 2
            } else {
                matrix[index] = PLAYER2.toLong()
                turn = 1
            }
        }
    }

    fun createRoom() {
        val roomModel = RoomModel()
        roomModel.roomId = RoomID
        roomModel.timestamp = TimeUtils.currentDateTime
        roomModel.roomId?.let {
            db.reference.child("APPS").child(Constants.GAME).child(Constants.GAMEROOMS).child(it).setValue(roomModel)
                .addOnCompleteListener { }
        }
    }

    private fun checkWin() {
        // Matrix 0 1 2
        //        3 4 5
        //        6 7 8


        // Check Horizontal Row
        if (matrix[0] == matrix[1] && matrix[1] == matrix[2] && matrix[2] != -1.toLong()) endGame()
        if (matrix[3] == matrix[4] && matrix[4] == matrix[5] && matrix[5] != -1.toLong()) endGame()
        if (matrix[6] == matrix[7] && matrix[7] == matrix[8] && matrix[8] != -1.toLong()) endGame()

        // Check Vertical Row
        if (matrix[0] == matrix[3] && matrix[3] == matrix[6] && matrix[6] != -1.toLong()) endGame()
        if (matrix[1] == matrix[4] && matrix[4] == matrix[7] && matrix[7] != -1.toLong()) endGame()
        if (matrix[2] == matrix[5] && matrix[5] == matrix[8] && matrix[8] != -1.toLong()) endGame()

        // Check Left Diagonal
        if (matrix[0] == matrix[4] && matrix[4] == matrix[8] && matrix[8] != -1.toLong()) endGame()

        // Check Right Diagonal
        if (matrix[2] == matrix[4] && matrix[4] == matrix[6] && matrix[6] != -1.toLong()) endGame()
        if (count == 9) {
            Toast.makeText(this, "We have a Tie", Toast.LENGTH_SHORT).show()
            endGame()
        }
    }

    private fun updateViews() {
        binding!!.displayTv.text = "Player $turn Turn"
        for (i in list.indices) {
            if (matrix[i] == PLAYER1 ) list[i].setImageDrawable(getDrawable(R.drawable.ic_cross)) else if (matrix[i] == PLAYER2) list[i].setImageDrawable(getDrawable(R.drawable.ic_zero)) else if (matrix[i] == -1) {
                list[i].setImageDrawable(null
                )
            }
        }
    }

    fun updateGameState() {
        db.reference.child("APPS").child(Constants.GAME).child(Constants.GAMEROOMS).child(RoomID!!).child("STATE").setValue(gameStarted)
        val tmplist: MutableList<Long> = ArrayList()
        for (elem in matrix) {
            tmplist.add(elem)
        }
        db.reference.child("APPS").child(Constants.GAME).child(Constants.GAMEROOMS).child(RoomID!!).child("MOVES").setValue(tmplist)
        db.reference.child("APPS").child(Constants.GAME).child(Constants.GAMEROOMS).child(RoomID!!).child("TURN").setValue(turn)
    }

    var roomStateListener: ValueEventListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            if (dataSnapshot.value != null) gameStarted = dataSnapshot.value as Boolean
        }

        override fun onCancelled(databaseError: DatabaseError) {}
    }
    var MovesListener: ValueEventListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            if (dataSnapshot.value != null) {
                val list: List<Long>? = dataSnapshot.value as ArrayList<Long>?
                var pos = 0
                for (elem in list!!) {
                    matrix[pos++] = elem
                }
            }
        }

        override fun onCancelled(databaseError: DatabaseError) {}
    }
    var TurnListener: ValueEventListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            if (dataSnapshot.value != null) turn = (dataSnapshot.getValue() as Long)
        }

        override fun onCancelled(databaseError: DatabaseError) {}
    }

    private fun attachListeners() {
        db.reference.child("APPS").child(Constants.GAME).child(Constants.GAMEROOMS).child(RoomID!!).child("STATE").addValueEventListener(roomStateListener)
        db.reference.child("APPS").child(Constants.GAME).child(Constants.GAMEROOMS).child(RoomID!!).child("MOVES").addValueEventListener(MovesListener)
        db.reference.child("APPS").child(Constants.GAME).child(Constants.GAMEROOMS).child(RoomID!!).child("TURN").addValueEventListener(TurnListener)
    }

    private fun detachListeners() {
        db.reference.child("APPS").child(Constants.GAME).child(Constants.GAMEROOMS).child(RoomID!!).child("STATE").removeEventListener(roomStateListener)
        db.reference.child("APPS").child(Constants.GAME).child(Constants.GAMEROOMS).child(RoomID!!).child("MOVES").removeEventListener(MovesListener)
        db.reference.child("APPS").child(Constants.GAME).child(Constants.GAMEROOMS).child(RoomID!!).child("TURN").removeEventListener(TurnListener)
    }
}