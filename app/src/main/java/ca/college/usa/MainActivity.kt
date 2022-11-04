package ca.college.usa

import androidx.appcompat.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.os.Bundle
import android.widget.ListView
import ca.college.usa.R
import ca.college.usa.MainActivity
import java.util.ArrayList

/**
 * Full Name:
 *
 * Student ID:
 *
 * Course: CST3104
 *
 * Term:  Fall 2022
 *
 * Assignment: Team Project
 *
 * Date :
 */
class MainActivity : AppCompatActivity() {
    // ListView <---> adapter <---> data
    private var mAdapter: ArrayAdapter<State>? = null
    private var mlistView: ListView? = null

    // Data Source for the Adapter
    private var mStatesList: ArrayList<State>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Load the data needed for the adapter
        mStatesList = State.readData(this, sFileName)
        mlistView = findViewById(R.id.listView)
        mAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            mStatesList
        )
        mlistView.setAdapter(mAdapter)
    }

    companion object {
        private const val sFileName = "usa.json"
    }
}