package ca.college.usa

import androidx.appcompat.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.os.Bundle
import android.widget.ListView
import ca.college.usa.databinding.ActivityMainBinding // important. We essentially 'imported' the xml
import java.util.ArrayList

/**
 * Full Name: Matthew Durocher, Irina Salikhova
 *
 * Student ID: 41036621
 *
 * Course: CST3104
 *
 * Term:  Fall 2022
 *
 * Assignment: Team Project
 *
 * Date : November 4, 2022
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    // ListView <---> ArrayAdapter <----> ArrayList
    private lateinit var mlistView: ListView
    private lateinit var mAdapter: ArrayAdapter<State>
    private lateinit var mStatesList: ArrayList<State>

    override fun onCreate(savedInstanceState: Bundle?) {

        /* Boilerplate */
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // load the state data
        State.readData(this, "usa.json")
        mStatesList = State.states

        mlistView = binding.listView

        mAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            mStatesList
        )

        mlistView.adapter = mAdapter
    }

}