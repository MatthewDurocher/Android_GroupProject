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

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DashboardAdapter(private val resultList: ArrayList<Results>) : RecyclerView.Adapter<DashboardAdapter.ViewHolder>() {
    class ViewHolder(dashItemView: View) : RecyclerView.ViewHolder(dashItemView) {
        val resultIcon: ImageView = dashItemView.findViewById(R.id.resultIcon)
        val dateAndTime: TextView = dashItemView.findViewById(R.id.dateAndTime)
        val resultValue: TextView = dashItemView.findViewById(R.id.result)
    }


    override fun getItemCount(): Int {
       return resultList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.dashboard_element, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemResultView = resultList[position]

        when (itemResultView.resultType) { // 1 - latest, 2 - best, 3 - worse
            1 ->  holder.resultIcon.setImageResource(R.drawable.latest)
            2 ->  holder.resultIcon.setImageResource(R.drawable.winning)
            3 ->  holder.resultIcon.setImageResource(R.drawable.failure)
        }
        holder.resultValue.text = itemResultView.resultVal.toString()

        holder.dateAndTime.text = itemResultView.dateTime
    }
}

data class Results (
        var dateTime: String? = null,
        var resultVal: Int? = null,
        var resultType: Int? = null // 1 - latest, 2 - best, 3 - worse
    ) {

    companion object {
        //List of all data class instances
        val resultList = ArrayList<Results>()
    }
}

