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
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.core.view.GravityCompat
import ca.college.usa.databinding.GameLayoutBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


class GameActivity : AppCompatActivity() {
    private lateinit var binding: GameLayoutBinding
    private lateinit var toggle: ActionBarDrawerToggle

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
        binding.apply {
            toggle = ActionBarDrawerToggle(
                this@GameActivity,
                binding.drawerLayout,
                R.string.open,
                R.string.close
            )
            binding.drawerLayout.addDrawerListener(toggle)
            toggle.syncState()

            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            binding.navView.setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.toDash -> {
                        val dashIntent = Intent(
                            this@GameActivity,
                            FirstDashActivity::class.java)
                        startActivity(dashIntent)

                    }
                    R.id.toGame -> {
                        Toast.makeText(
                            this@GameActivity,
                            R.string.here_toast,
                            Toast.LENGTH_SHORT).show()
                    }
                    R.id.toLearnMode -> {
                        val learnIntent = Intent(
                            this@GameActivity,
                            LearningActivity::class.java)
                        startActivity(learnIntent)


                    }
                    R.id.showInf -> {
                        callDialog()
                    }
                }
                binding.drawerLayout.closeDrawer(GravityCompat.START)
                true
            }
            Toast.makeText(applicationContext, "onCreate", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        supportActionBar?.title = "Game"

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.wiki -> {
                val wikiPage = Intent(Intent.ACTION_VIEW)
                wikiPage.data = Uri.parse(currentState.wiki)
                startActivity(wikiPage)
                return true
            }
            R.id.newGame -> {
                this.recreate()
                return true
            }
            R.id.showInf -> {
                callDialog()
            }
            R.id.home -> {
                NavUtils.navigateUpFromSameTask(this);
            }
        }
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun callDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(R.string.information) //What is the message:
            .setMessage(String.format(
                " %s \n \n %s \n \n %s",
                getString(R.string.game_info),
                getString(R.string.toolbar_info),
                getString(R.string.inf2)
            ))
            .setPositiveButton(R.string.dialog_button) { click: DialogInterface?, arg: Int -> }
            .create().show()
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
        bestResult = sharPref.getInt(BESTRESULT, 999)

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
        text1.text = getString(R.string.your_result) + counter
        val text2  = dialogLayout.findViewById<TextView>(R.id.addCongrat)
        if (counter == bestResult) {
            text2.text = getString(R.string.additional_congrat1)
        } else {
            text2.text = String.format(getString(R.string.additional_congrat2), (worstResult - counter))
        }
        builder.setView(dialogLayout)
        builder.setPositiveButton("OK") { click: DialogInterface?, arg: Int -> }
        builder.show()
    }

}