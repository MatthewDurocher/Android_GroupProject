package ca.college.usa

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.college.usa.databinding.DashboardLayoutBinding
import ca.college.usa.databinding.LearningModeBinding

class LearningActivity : AppCompatActivity() {
    private lateinit var binding : LearningModeBinding
    private lateinit var toggle: ActionBarDrawerToggle

    private lateinit var learnView: RecyclerView
    private lateinit var learnAdapter: LearningAdapter
    private lateinit var stateList: ArrayList<State>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LearningModeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // load the state data
        stateList = State.states ?: State.readData(this)


        learnView = binding.learningView
        learnAdapter = LearningAdapter(stateList)
        learnView.adapter = learnAdapter
        learnView.layoutManager = LinearLayoutManager(this)

        learnAdapter.onItemClick = { contact ->
            // do something with your item
            Log.d("TAG", "i clicked it")
        }

        binding.apply {
            toggle = ActionBarDrawerToggle(this@LearningActivity, learnDrawer.drawerLayout, R.string.open, R.string.close)
            learnDrawer.drawerLayout.addDrawerListener(toggle)
            toggle.syncState()

            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            learnDrawer.navView.setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.toDash -> {
                        val intent = Intent(this@LearningActivity, FirstDashActivity::class.java)
                        startActivity(intent)
                    }
                    R.id.toGame -> {
                        Toast.makeText(this@LearningActivity, "Second Item Clicked", Toast.LENGTH_SHORT).show()
                        val gameIntent = Intent(this@LearningActivity, GameActivity::class.java)
                        startActivity(gameIntent)
                    }
                    R.id.toLearnMode -> {
                        Toast.makeText(this@LearningActivity,  R.string.here_toast, Toast.LENGTH_SHORT).show()

                    }
                    R.id.showInf -> {
                        callDialog()
                    }
                }
                learnDrawer.drawerLayout.closeDrawer(GravityCompat.START)
                true
            }
        }
    }

    private fun callDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(R.string.information) //What is the message:
            .setMessage(String.format("%s \n \n %s", getString(R.string.inf1), getString(R.string.inf2)))
            .setPositiveButton(R.string.dialog_button) { click: DialogInterface?, arg: Int -> }
            .create().show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            true
        }
        return super.onOptionsItemSelected(item)
    }
}