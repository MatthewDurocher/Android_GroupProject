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
}