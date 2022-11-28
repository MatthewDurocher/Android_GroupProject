package ca.college.usa
/**
 * Full Name: Matthew Durocher (main developer for the activity) and Irina Salikhova
 *
 * Student ID: 041036621 (Matt) 041025826 (Irina)
 *
 * Course: CST3104
 *
 * Term:  Fall 2022
 *
 * Assignment: Team Project
 *
 * Date : 2022-11-27
 */

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import ca.college.usa.databinding.GameLayoutBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class GameActivity : AppCompatActivity() {
    private lateinit var binding: GameLayoutBinding

    private lateinit var gameAdapter: ArrayAdapter<String>
    private lateinit var nameArray: ArrayList<String>
    private lateinit var stateList: ArrayList<State>

    private lateinit var currentState: State

    private var counter = 0
    private var stopGame = false
    private var bestResult = 0
    private var worstResult = 0

    private val SHARPREFNAME = "dashboard_results"
    private val LATESTTIME = "latest_time"
    private val LATESTRESULT = "latest_result"
    private val BESTTIME = "best_time"
    private val BESTRESULT = "best_result"
    private val WORSTTIME = "worst_time"
    private val WORSTRESULT = "worst_result"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = GameLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        stateList = State.states ?: State.readData(this)

        //Choose state
        currentState = randomState()

        //Get list of names
        val fetchNames: (State) -> String = {it.name}
        nameArray = stateList.map(fetchNames) as ArrayList<String>

        gameAdapter = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, nameArray)
        binding.listView.adapter = gameAdapter

        binding.listView.setOnItemClickListener { parent, view, position, id ->

            if (!stopGame) {
                increment()
                val selectedState = nameArray[position]

                if (selectedState == currentState.name) {
                    binding.counterBackground.setBackgroundColor(Color.GREEN)
                    saveScore()
                    stopGame = true
                    customAlert(binding.listView)
                } else {
                    binding.counterBackground.setBackgroundColor(Color.RED)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.wiki -> {
                val wikiPage = Intent(Intent.ACTION_VIEW)
                wikiPage.data = Uri.parse(currentState.wiki)
                startActivity(wikiPage)
            }
            R.id.newGame -> {
                this.recreate()
            }
        }
        return true
    }

    private fun increment() {
        counter ++
        binding.counter.text = counter.toString()
    }

    private fun randomState(): State {
        //DEV: not going to work like this later
        val state = stateList.random()
        state.flagInImageView(binding.flagView)

        binding.capitalName.text = state.capital
        return state
    }

    private fun saveScore() {
        val datetime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
        val formatted = datetime.format(formatter)

        val sharPref = getSharedPreferences(SHARPREFNAME, MODE_PRIVATE)

        val current = counter
        worstResult = sharPref.getInt(WORSTRESULT, 0)
        bestResult = sharPref.getInt(BESTRESULT, 0)

        with (sharPref.edit()) {
            Log.d("SCORE", "latest")
            putInt(LATESTRESULT, current)
            putString(LATESTTIME, formatted)

            if (current <= bestResult)  {
                Log.d("SCORE", "best")
                putInt(BESTRESULT, current)
                putString(BESTTIME, formatted)
                bestResult = current

            } else if (current > worstResult) {
                Log.d("SCORE", "worst")
                putInt(WORSTRESULT, current)
                putString(WORSTTIME, formatted)
                worstResult = current
            }
            apply()
        }
    }

    fun customAlert(view: View) {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        builder.setTitle(R.string.congrats)
        val dialogLayout = inflater.inflate(R.layout.final_alert, null)
        val text1  = dialogLayout.findViewById<TextView>(R.id.yourResult)
        text1.setText(getString(R.string.your_result) + counter)
        val text2  = dialogLayout.findViewById<TextView>(R.id.addCongrat)
        if (counter == bestResult) {
            text2.setText(getString(R.string.additional_congrat1))
        } else {
            text2.setText(String.format(getString(R.string.additional_congrat2), (worstResult - counter)))
        }
        builder.setView(dialogLayout)
        builder.setPositiveButton("OK") { click: DialogInterface?, arg: Int -> }
        builder.show()
    }

}