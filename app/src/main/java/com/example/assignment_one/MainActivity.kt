package com.example.assignment_one

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var grid: Array<Array<Button>>
    private var currentPlayer = "X"
    private var gameActive = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize grid
        grid =
            arrayOf(
                arrayOf(
                    findViewById(R.id.button_00),
                    findViewById(R.id.button_01),
                    findViewById(R.id.button_02),
                ),
                arrayOf(
                    findViewById(R.id.button_10),
                    findViewById(R.id.button_11),
                    findViewById(R.id.button_12),
                ),
                arrayOf(
                    findViewById(R.id.button_20),
                    findViewById(R.id.button_21),
                    findViewById(R.id.button_22),
                ),
            )

        val resultText: TextView = findViewById(R.id.textViewResult)
        val playAgainButton: Button = findViewById(R.id.buttonPlayAgain)

        // Set click listeners for all buttons
        for (i in grid.indices) {
            for (j in grid[i].indices) {
                grid[i][j].setOnClickListener {
                    if (gameActive && grid[i][j].text.isEmpty()) {
                        grid[i][j].text = currentPlayer
                        if (checkWinner()) {
                            resultText.text = getString(R.string.player_wins, currentPlayer)
                            resultText.visibility = View.VISIBLE
                            playAgainButton.visibility = View.VISIBLE
                            gameActive = false
                        } else if (isDraw()) {
                            resultText.text = getString(R.string.its_a_draw)
                            resultText.visibility = View.VISIBLE
                            playAgainButton.visibility = View.VISIBLE
                            gameActive = false
                        } else {
                            currentPlayer = if (currentPlayer == "X") "O" else "X"
                        }
                    }
                }
            }
        }

        // Play again button resets the game
        playAgainButton.setOnClickListener {
            resetGame()
        }
    }

    private fun checkWinner(): Boolean {
        val size = grid.size
        val winningConditions = mutableListOf<List<Button>>()

        // Add rows
        for (i in 0 until size) {
            winningConditions.add(grid[i].toList())
        }

        // Add columns
        for (i in 0 until size) {
            val column = mutableListOf<Button>()
            for (j in 0 until size) {
                column.add(grid[j][i])
            }
            winningConditions.add(column)
        }

        // Add main diagonal
        val mainDiagonal = mutableListOf<Button>()
        for (i in 0 until size) {
            mainDiagonal.add(grid[i][i])
        }
        winningConditions.add(mainDiagonal)

        // Add anti-diagonal
        val antiDiagonal = mutableListOf<Button>()
        for (i in 0 until size) {
            antiDiagonal.add(grid[i][size - i - 1])
        }
        winningConditions.add(antiDiagonal)

        // Check if any condition is satisfied
        return winningConditions.any { condition ->
            condition.all { it.text == currentPlayer }
        }
    }

    private fun isDraw(): Boolean {
        for (row in grid) {
            for (button in row) {
                if (button.text.isEmpty()) return false
            }
        }
        return true
    }

    private fun resetGame() {
        for (row in grid) {
            for (button in row) {
                button.text = ""
            }
        }
        currentPlayer = "X"
        gameActive = true
        findViewById<TextView>(R.id.textViewResult).visibility = View.GONE
        findViewById<Button>(R.id.buttonPlayAgain).visibility = View.GONE
    }
}