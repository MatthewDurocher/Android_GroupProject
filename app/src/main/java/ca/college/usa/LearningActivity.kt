package ca.college.usa


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
}