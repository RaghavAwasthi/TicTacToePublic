package com.rvai.tictactoe

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import com.rvai.tictactoe.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    var gameStarted = false
    var binding: ActivityMainBinding? = null
    var list: MutableList<AppCompatImageButton> = ArrayList()
    var matrix = intArrayOf(-1, -1, -1, -1, -1, -1, -1, -1, -1) // Our MAtrix
    val PLAYER1 = 0
    val PLAYER2 = 1
    var turn = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
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

    var timer: CountDownTimer? = null
    fun startTimer() {
        if (timer == null) {
            timer = object : CountDownTimer(5000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    binding!!.timer.text = "Time Left:" + millisUntilFinished / 1000
                }

                override fun onFinish() {
                    turn = if (turn == 1) {
                        2
                    } else 1
                    binding!!.displayTv.text = "Player $turn Turn"
                    restartTimer()
                }
            }
            (timer as CountDownTimer).start()
        }
    }

    private fun stopTimer() {
        if (timer != null) {
            timer!!.cancel()
            timer = null
        }
    }

    private fun restartTimer() {
        stopTimer()
        startTimer()
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

    var count = 0
    private fun startGame() {
        gameStarted = true
        turn = 1
        startTimer()
        binding!!.startGame.isEnabled = false
        binding!!.PlayAgain.isEnabled = true
        binding!!.displayTv.visibility = View.VISIBLE
    }

    private fun resetGameProperties() {
        matrix = intArrayOf(-1, -1, -1, -1, -1, -1, -1, -1, -1)
        updateViews()
        turn = 1
        count = 0
        gameStarted = false
        binding!!.startGame.isEnabled = true
        binding!!.PlayAgain.isEnabled = false
        binding!!.displayTv.text = "Player 1 Turn"
        binding!!.displayTv.visibility = View.GONE
        stopTimer()
    }

    private fun endGame() {
        if (turn == 1) {
            Toast.makeText(this, "Player " + 2 + " Won", Toast.LENGTH_SHORT).show()
        } else if (turn == 2) {
            Toast.makeText(this, "Player " + 1 + " Won", Toast.LENGTH_SHORT).show()
        }
        resetGameProperties()
    }

    private fun updatematrix(index: Int) {
        if (matrix[index] == -1) {
            restartTimer()
            count++
            if (turn == 1) {
                matrix[index] = PLAYER1
                turn = 2
            } else {
                matrix[index] = PLAYER2
                turn = 1
            }
        }
    }

    private fun checkWin() {
        // Matrix 0 1 2
        //        3 4 5
        //        6 7 8


        // Check Horizontal Row
        if (matrix[0] == matrix[1] && matrix[1] == matrix[2] && matrix[2] != -1) endGame()
        if (matrix[3] == matrix[4] && matrix[4] == matrix[5] && matrix[5] != -1) endGame()
        if (matrix[6] == matrix[7] && matrix[7] == matrix[8] && matrix[8] != -1) endGame()

        // Check Vertical Row
        if (matrix[0] == matrix[3] && matrix[3] == matrix[6] && matrix[6] != -1) endGame()
        if (matrix[1] == matrix[4] && matrix[4] == matrix[7] && matrix[7] != -1) endGame()
        if (matrix[2] == matrix[5] && matrix[5] == matrix[8] && matrix[8] != -1) endGame()

        // Check Left Diagonal
        if (matrix[0] == matrix[4] && matrix[4] == matrix[8] && matrix[8] != -1) endGame()

        // Check Right Diagonal
        if (matrix[2] == matrix[4] && matrix[4] == matrix[6] && matrix[6] != -1) endGame()
        if (count == 9) {
            Toast.makeText(this, "We have a Tie", Toast.LENGTH_SHORT).show()
            resetGameProperties()
        }
    }

    private fun updateViews() {
        binding!!.displayTv.text = "Player $turn Turn"
        for (i in list.indices) {
            if (matrix[i] == PLAYER1) list[i].setImageDrawable(getDrawable(R.drawable.ic_cross)) else if (matrix[i] == PLAYER2) list[i].setImageDrawable(getDrawable(R.drawable.ic_zero)) else if (matrix[i] == -1) {
                list[i].setImageDrawable(null
                )
            }
        }
    }
}