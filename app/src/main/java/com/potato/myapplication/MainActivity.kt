package com.potato.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private val diceImageViewsMA = mutableListOf<ImageView>()
    private lateinit var diceContainerMA: LinearLayout
    private lateinit var totalTextViewMA: TextView
    private val diceMA = Dice(6)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        diceContainerMA = findViewById(R.id.dice_container_AM)
        totalTextViewMA = findViewById(R.id.roll_total_text_AM)
        val rollButtonMA: Button = findViewById(R.id.button_AM)
        val addButtonMA: Button = findViewById(R.id.button_add_die_AM)
        val removeButtonMA: Button = findViewById(R.id.button_remove_die_AM)

        rollButtonMA.setOnClickListener {
            rollAllDiceMA()
        }
        addButtonMA.setOnClickListener {
            addDieMA()
        }
        removeButtonMA.setOnClickListener {
            removeDieMA()
        }

        addDieMA()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun addDieMA() {
        if (diceImageViewsMA.size >= 20) {
            Toast.makeText(this, getString(R.string.max_dice_reached), Toast.LENGTH_SHORT).show()
            return
        }

        val newDieImage = ImageView(this)
        val params = LinearLayout.LayoutParams(100, 100).apply {
            setMargins(8, 0, 8, 0)
        }
        newDieImage.layoutParams = params

        diceImageViewsMA.add(newDieImage)
        diceContainerMA.addView(newDieImage)
        rollAllDiceMA()
    }

    private fun removeDieMA() {
        if (diceImageViewsMA.size <= 1) {
            Toast.makeText(this, getString(R.string.cannot_remove_last_die), Toast.LENGTH_SHORT).show()
            return
        }
        val lastDie = diceImageViewsMA.removeAt(diceImageViewsMA.lastIndex)
        diceContainerMA.removeView(lastDie)
        rollAllDiceMA()
    }

    private fun rollAllDiceMA() {
        var total = 0
        for (diceImage in diceImageViewsMA) {
            val cubeRoll = diceMA.rollMA()
            total += cubeRoll
            val drawableResource = getDrawableForRollMA(cubeRoll)
            diceImage.setImageResource(drawableResource)
        }

        totalTextViewMA.text = getString(R.string.total_format, total) // CHANGED
    }

    private fun getDrawableForRollMA(cubeRoll: Int): Int {
        return when (cubeRoll) {
            1 -> R.drawable.dice
            2 -> R.drawable.dice_1_
            3 -> R.drawable.dice_4_
            4 -> R.drawable.dice_2_
            5 -> R.drawable.dice_6_
            6 -> R.drawable.dice_7_
            else -> R.drawable.dice
        }
    }

    class Dice(val numSidesMA: Int) {
        fun rollMA(): Int {
            return (1..numSidesMA).random()
        }
    }
}