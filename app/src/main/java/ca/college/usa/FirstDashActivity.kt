package ca.college.usa

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.college.usa.databinding.DashboardLayoutBinding

class FirstDashActivity: AppCompatActivity()  {
    private lateinit var binding : DashboardLayoutBinding
    private lateinit var toggle: ActionBarDrawerToggle

    private lateinit var dashView: RecyclerView
    private lateinit var dashAdapter: DashboardAdapter
    private lateinit var resultList: ArrayList<Results>

    private val SHARPREFNAME = "dashboard_results"
    private val LATESTTIME = "latest_time"
    private val LATESTRESULT = "latest_result"
    private val BESTTIME = "best_time"
    private val BESTRESULT = "best_result"
    private val WORSTTIME = "worst_time"
    private val WORSTRESULT = "worst_result"

    private lateinit var sharPref: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        /* Boilerplate */
        super.onCreate(savedInstanceState)
        binding = DashboardLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // to receive previous result information from sharedpref file
        sharPref = getSharedPreferences(SHARPREFNAME, MODE_PRIVATE)

        // writer part here is for test only, it will be populated with actual data later
        val writer: SharedPreferences.Editor = sharPref.edit()
        writer.putString(LATESTTIME, "it is latest time for test")
        writer.putString(BESTTIME, "it is best time for test")
        writer.putString(WORSTTIME, "it is worst time for test")
        writer.putInt(LATESTRESULT, 2)
        writer.putInt(BESTRESULT, 1)
        writer.putInt(WORSTRESULT, 3)
        writer.apply() //save to disk

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
                d.drawerLayout,
                R.string.open,
                R.string.close
            )
            d.drawerLayout.addDrawerListener(toggle)
            toggle.syncState()

            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            d.navView.setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.toDash -> {
                        Toast.makeText(
                            this@FirstDashActivity,
                            R.string.here_toast,
                            Toast.LENGTH_SHORT).show()

                        val dashIntent = Intent(
                            this@FirstDashActivity,
                            FirstDashActivity::class.java)
                        startActivity(dashIntent)
                    }
                    R.id.toGame -> {
                        Toast.makeText(
                            this@FirstDashActivity,
                            "Second Item Clicked",
                            Toast.LENGTH_SHORT).show()

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
                d.drawerLayout.closeDrawer(GravityCompat.START)
                true
            }
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
        val newResultList = ArrayList<Results>()
        newResultList.add(latestResult)
        newResultList.add(bestResult)
        newResultList.add(worstResult)
    return newResultList
    }

}
