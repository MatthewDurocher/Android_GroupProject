package ca.college.usa

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.college.usa.databinding.GameLayoutBinding

class GameActivity : AppCompatActivity() {
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding: GameLayoutBinding

    private lateinit var gameAdapter: ArrayAdapter<String>
    private lateinit var nameArray: ArrayList<String>
    private lateinit var stateList: ArrayList<State>

    private lateinit var currentState: State

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


    }

    private fun randomState(): State {
        //DEV: not going to work like this later
        val state = stateList.random()
        state.flagInImageView(binding.flagView)

        binding.capitalName.text = state.capital
        return state
    }

}