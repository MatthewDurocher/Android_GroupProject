package ca.college.usa


import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
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
            increment()
            val selectedState = nameArray[position]

            if (selectedState == currentState.name) {
                binding.counterBackground.setBackgroundColor(Color.GREEN)

                saveScore()
            } else {
                binding.counterBackground.setBackgroundColor(Color.RED)
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
        val worst = sharPref.getInt(WORSTRESULT, 0)
        val best = sharPref.getInt(BESTRESULT, 0)

        with (sharPref.edit()) {
            Log.d("SCORE", "latest")
            putInt(LATESTRESULT, current)
            putString(LATESTTIME, formatted)

            if (current < best)  {
                Log.d("SCORE", "best")
                putInt(BESTRESULT, current)
                putString(BESTTIME, formatted)

            } else if (current > worst) {
                Log.d("SCORE", "worst")
                putInt(WORSTRESULT, current)
                putString(WORSTTIME, formatted)
            }
            apply()
        }
    }

}