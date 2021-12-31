package com.example.tic_tac_toe

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.example.tic_tac_toe.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var difficulty: Enum<Difficulty>
    private lateinit var btns: MutableList<ImageButton>
    private lateinit var availableBtns: MutableList<ImageButton>
    private var btns_o: MutableList<ImageButton> = mutableListOf()
    private var btns_x: MutableList<ImageButton> = mutableListOf()
    private lateinit var wins_x: MutableList<Boolean>
    private lateinit var wins_o: MutableList<Boolean>
    private var won = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        difficulty = Difficulty.EASY
        binding.difficulty.text = difficulty.toString().lowercase()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        btns = mutableListOf(binding.imageButton1, binding.imageButton2, binding.imageButton3, binding.imageButton4,
            binding.imageButton5, binding.imageButton6, binding.imageButton7, binding.imageButton8, binding.imageButton9)
        availableBtns = btns

        for(bt in btns) {
            bt.setOnClickListener { clicked(it) }
        }

        binding.resetButton.setOnClickListener { reset() }

    }

    fun clicked(btn: View){
        if(!won) {
            if (difficulty == Difficulty.EASY) {
                easyGame(btn)
            }
        }
    }

    private fun easyGame(btn: View): Boolean{
        if(btn in availableBtns){
            btn.setBackgroundResource(R.drawable.tic_tac_toe_o)
            btns_o.add(btn as ImageButton)
            availableBtns.remove(btn)

            if(checkWin_o()){
                Snackbar.make(binding.coordinatorLayout, "You have won!", Snackbar.LENGTH_LONG).show()
                won = true
                return true
            }
            if(availableBtns.size > 0) {
                val randInt = (0 until availableBtns.size).random()
                val btn_x = availableBtns[randInt]
                btn_x.setBackgroundResource(R.drawable.tic_tac_toe_x)
                btns_x.add(btn_x)
                availableBtns.remove(btn_x)

                if (checkWin_x()) {
                    Snackbar.make(binding.coordinatorLayout, "The Computer has won!", Snackbar.LENGTH_LONG).show()
                    won = true
                    return true
                }
            }
        }
        if(availableBtns.size == 0) {
            Snackbar.make(binding.coordinatorLayout, "Tie!", Snackbar.LENGTH_LONG).show()
            won = true
        }
        return true
    }


    private fun reset() {
        btns = mutableListOf(binding.imageButton1, binding.imageButton2, binding.imageButton3, binding.imageButton4,
            binding.imageButton5, binding.imageButton6, binding.imageButton7, binding.imageButton8, binding.imageButton9)
        for(bt in btns) {
            bt.setBackgroundResource(R.drawable.tic_tac_toe_empty)
        }
//        binding.imageButton1.setBackgroundResource(R.drawable.tic_tac_toe_empty)
//        binding.imageButton2.setBackgroundResource(R.drawable.tic_tac_toe_empty)
//        binding.imageButton3.setBackgroundResource(R.drawable.tic_tac_toe_empty)
//        binding.imageButton4.setBackgroundResource(R.drawable.tic_tac_toe_empty)
//        binding.imageButton5.setBackgroundResource(R.drawable.tic_tac_toe_empty)
//        binding.imageButton6.setBackgroundResource(R.drawable.tic_tac_toe_empty)
//        binding.imageButton7.setBackgroundResource(R.drawable.tic_tac_toe_empty)
//        binding.imageButton8.setBackgroundResource(R.drawable.tic_tac_toe_empty)
//        binding.imageButton9.setBackgroundResource(R.drawable.tic_tac_toe_empty)
//        btns = mutableListOf(binding.imageButton1, binding.imageButton2, binding.imageButton3, binding.imageButton4,
//            binding.imageButton5, binding.imageButton6, binding.imageButton7, binding.imageButton8, binding.imageButton9)

        availableBtns = btns
        btns_o = mutableListOf()
        btns_x = mutableListOf()
        won = false
    }

    private fun checkWin_x(): Boolean {
        setWins()
        for(win in wins_x) {
            if(win) { return true }
        }
        return false
    }

    private fun checkWin_o(): Boolean {
        setWins()
        for(win in wins_o) {
            if(win) { return true }
        }
        return false
    }

    private fun setWins(){
        wins_x = mutableListOf(binding.imageButton1 in btns_x && binding.imageButton2 in btns_x && binding.imageButton3 in btns_x,
            binding.imageButton4 in btns_x && binding.imageButton5 in btns_x && binding.imageButton6 in btns_x,
            binding.imageButton7 in btns_x && binding.imageButton8 in btns_x && binding.imageButton9 in btns_x,
            binding.imageButton1 in btns_x && binding.imageButton4 in btns_x && binding.imageButton7 in btns_x,
            binding.imageButton2 in btns_x && binding.imageButton5 in btns_x && binding.imageButton8 in btns_x,
            binding.imageButton3 in btns_x && binding.imageButton6 in btns_x && binding.imageButton9 in btns_x,
            binding.imageButton1 in btns_x && binding.imageButton5 in btns_x && binding.imageButton9 in btns_x,
            binding.imageButton3 in btns_x && binding.imageButton5 in btns_x && binding.imageButton7 in btns_x)

        wins_o = mutableListOf(binding.imageButton1 in btns_o && binding.imageButton2 in btns_o && binding.imageButton3 in btns_o,
            binding.imageButton4 in btns_o && binding.imageButton5 in btns_o && binding.imageButton6 in btns_o,
            binding.imageButton7 in btns_o && binding.imageButton8 in btns_o && binding.imageButton9 in btns_o,
            binding.imageButton1 in btns_o && binding.imageButton4 in btns_o && binding.imageButton7 in btns_o,
            binding.imageButton2 in btns_o && binding.imageButton5 in btns_o && binding.imageButton8 in btns_o,
            binding.imageButton3 in btns_o && binding.imageButton6 in btns_o && binding.imageButton9 in btns_o,
            binding.imageButton1 in btns_o && binding.imageButton5 in btns_o && binding.imageButton9 in btns_o,
            binding.imageButton3 in btns_o && binding.imageButton5 in btns_o && binding.imageButton7 in btns_o)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_easy -> {
                difficulty = Difficulty.EASY
                binding.difficulty.text = difficulty.toString().lowercase()
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                true
            }

            R.id.action_medium -> {
                difficulty = Difficulty.MEDIUM
                binding.difficulty.text = difficulty.toString().lowercase()
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                true
            }

            R.id.action_impossible -> {
                difficulty = Difficulty.IMPOSSIBLE
                binding.difficulty.text = difficulty.toString().lowercase()
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}