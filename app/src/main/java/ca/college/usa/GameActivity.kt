package ca.college.usa

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.RecyclerView
import ca.college.usa.databinding.GameLayoutBinding

class GameActivity : AppCompatActivity() {
    private lateinit var binding: GameLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = GameLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            val toggle = ActionBarDrawerToggle(
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

    private fun callDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(R.string.information) //What is the message:
            .setMessage(String.format("%s \n \n %s", getString(R.string.inf1), getString(R.string.inf2)))
            .setPositiveButton(R.string.dialog_button) { click: DialogInterface?, arg: Int -> }
            .create().show()
    }

}