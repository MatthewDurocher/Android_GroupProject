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

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LearningAdapter(private val stateList: ArrayList<State>) : RecyclerView.Adapter<LearningAdapter.ViewHolder>() {
    var onItemClick: ((State) -> Unit)? = null

   inner class ViewHolder(learnItemView: View) : RecyclerView.ViewHolder(learnItemView) {
        val flagImage: ImageView = learnItemView.findViewById(R.id.flagView)
        val stateName: TextView = learnItemView.findViewById(R.id.stateName)
        val capitalName: TextView = learnItemView.findViewById(R.id.capitalName)
        val wikiButton: ImageButton = learnItemView.findViewById(R.id.wikiButton)
        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(stateList[adapterPosition])
            }
        }
    }


    override fun getItemCount(): Int {
       return stateList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.learning_mode_element, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemStateView = stateList[position]

        holder.stateName.text = itemStateView.name
        holder.capitalName.text = itemStateView.capital
        holder.wikiButton.setOnClickListener{
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(itemStateView.wiki)
            holder.wikiButton.context.startActivity(i)
            }
       itemStateView.flagInImageView(holder.flagImage)



        }
}


