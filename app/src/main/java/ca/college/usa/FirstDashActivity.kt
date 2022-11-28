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
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import ca.college.usa.databinding.DashboardLayoutBinding

class FirstDashActivity: AppCompatActivity()  {
    private lateinit var binding : DashboardLayoutBinding
    private lateinit var toggle: ActionBarDrawerToggle

    private lateinit var dashAdapter: DashboardAdapter
    private lateinit var resultList: ArrayList<Results>

    private lateinit var sharPref: SharedPreferences

    private val SHARPREFNAME = "dashboard_results"
    private val LATESTTIME = "latest_time"
    private val LATESTRESULT = "latest_result"
    private val BESTTIME = "best_time"
    private val BESTRESULT = "best_result"
    private val WORSTTIME = "worst_time"
    private val WORSTRESULT = "worst_result"

    override fun onCreate(savedInstanceState: Bundle?) {
        /* Boilerplate */
        super.onCreate(savedInstanceState)
        binding = DashboardLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // to receive previous result information from sharedpref file
        sharPref = getSharedPreferences(SHARPREFNAME, MODE_PRIVATE)

        resultList = restorePrefs(sharPref)  // load data

        binding.dashboardView.layoutManager = LinearLayoutManager(this)

        dashAdapter = DashboardAdapter(resultList)
        binding.dashboardView.adapter = dashAdapter

        binding.newGameButton.setOnClickListener {
            val gameIntent = Intent(
                this@FirstDashActivity,
                GameActivity::class.java)
            startActivity(gameIntent)
        }

        binding.learningModeButton.setOnClickListener {
            val intent = Intent(this@FirstDashActivity, LearningActivity::class.java)
            startActivity(intent)
        }

        binding.apply {
            toggle = ActionBarDrawerToggle(
                this@FirstDashActivity,
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
                        Toast.makeText(
                            this@FirstDashActivity,
                            R.string.here_toast,
                            Toast.LENGTH_SHORT).show()

                    }
                    R.id.toGame -> {
                        val gameIntent = Intent(
                            this@FirstDashActivity,
                            GameActivity::class.java)
                        startActivity(gameIntent)
                    }
                    R.id.toLearnMode -> {
                        val learnIntent = Intent(
                            this@FirstDashActivity,
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

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        restorePrefs(sharPref)
        dashAdapter.notifyDataSetChanged()
        Toast.makeText(applicationContext, "onResume", Toast.LENGTH_SHORT).show()
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
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun restorePrefs(sharPref: SharedPreferences): ArrayList<Results> {

        val latestResult = Results(
            dateTime = sharPref.getString(LATESTTIME, ""),
            resultVal = sharPref.getInt(LATESTRESULT, 0),
            resultType = 1
        )
        val bestResult = Results(
            dateTime = sharPref.getString(BESTTIME, ""),
            resultVal = sharPref.getInt(BESTRESULT, 0),
            resultType = 2
        )
        val worstResult = Results(
            dateTime = sharPref.getString(WORSTTIME, ""),
            resultVal = sharPref.getInt(WORSTRESULT, 0),
            resultType = 3
        )
        Log.d("SCORE", "latest: ${latestResult.resultVal}")
        Log.d("SCORE", "best: ${bestResult.resultVal}")
        Log.d("SCORE", "worst: ${worstResult.resultVal}")

        if (this::resultList.isInitialized){
            resultList[0] = (latestResult)
            resultList[1] = (bestResult)
            resultList[2] = (worstResult)
        } else {
            resultList = ArrayList()
            resultList.add(latestResult)
            resultList.add(bestResult)
            resultList.add(worstResult)
        }

    return resultList
    }

}
