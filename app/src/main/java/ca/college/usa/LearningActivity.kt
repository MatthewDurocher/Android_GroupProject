package ca.college.usa
/**
 * Full Name: Irina Salikhova (main developer for the activity) and Matthew Durocher
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
            toggle = ActionBarDrawerToggle(
                this@LearningActivity,
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
                            this@LearningActivity,
                            FirstDashActivity::class.java)
                        startActivity(dashIntent)

                    }
                    R.id.toGame -> {
                        val gameIntent = Intent(
                            this@LearningActivity,
                            GameActivity::class.java)
                        startActivity(gameIntent)
                    }
                    R.id.toLearnMode -> {
                        Toast.makeText(
                            this@LearningActivity,
                            R.string.here_toast,
                            Toast.LENGTH_SHORT).show()

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

    private fun callDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(R.string.information) //What is the message:
            .setMessage(String.format(
                "%s \n \n %s",
                getString(R.string.inf1),
                getString(R.string.inf2)
            ))
            .setPositiveButton(R.string.dialog_button) { click: DialogInterface?, arg: Int -> }
            .create().show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return toggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item)
    }
}