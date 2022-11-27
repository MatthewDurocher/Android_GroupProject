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

        binding.apply {
            toggle = ActionBarDrawerToggle(
                this@GameActivity,
                gameDrawer.drawerLayout,
                R.string.open,
                R.string.close
            )
            gameDrawer.drawerLayout.addDrawerListener(toggle)
            toggle.syncState()

            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            gameDrawer.navView.setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.toDash -> {
                        Toast.makeText(this@GameActivity, R.string.here_toast, Toast.LENGTH_SHORT)
                            .show()
                        val dashIntent = Intent(this@GameActivity, FirstDashActivity::class.java)
                        startActivity(dashIntent)
                    }
                    R.id.toGame -> {
                        Toast.makeText(this@GameActivity, "Second Item Clicked", Toast.LENGTH_SHORT)
                            .show()
                    }
                    R.id.toLearnMode -> {
                        val learnIntent = Intent(this@GameActivity, LearningActivity::class.java)
                        startActivity(learnIntent)
                    }
                    R.id.showInf -> {
                        callDialog()
                    }
                }
                gameDrawer.drawerLayout.closeDrawer(GravityCompat.START)
                true
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun callDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(R.string.information) //What is the message:
            .setMessage(String.format("%s \n \n %s", getString(R.string.inf1), getString(R.string.inf2)))
            .setPositiveButton(R.string.dialog_button) { click: DialogInterface?, arg: Int -> }
            .create().show()
    }

    private fun randomState(): State {
        //DEV: not going to work like this later
        val state = stateList.random()
        state.flagInImageView(binding.flagView)

        binding.capitalName.text = state.capital
        return state
    }

}