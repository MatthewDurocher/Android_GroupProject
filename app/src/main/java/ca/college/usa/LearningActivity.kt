package ca.college.usa


import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NavUtils
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.college.usa.databinding.LearningModeBinding

class LearningActivity : AppCompatActivity() {
    private lateinit var binding : LearningModeBinding

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

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)

        supportActionBar?.title = "Game"

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.showInf -> {
                callDialog()
            }
            R.id.home -> {
                NavUtils.navigateUpFromSameTask(this);
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun callDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(R.string.information) //What is the message:
            .setMessage(String.format(
                " %s \n \n %s \n \n %s",
                getString(R.string.learn_info),
                getString(R.string.inf2)
            ))
            .setPositiveButton(R.string.dialog_button) { click: DialogInterface?, arg: Int -> }
            .create().show()
    }
}