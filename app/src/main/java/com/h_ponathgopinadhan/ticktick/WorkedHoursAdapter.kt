package com.h_ponathgopinadhan.ticktick

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WorkedHoursAdapter : RecyclerView.Adapter<WorkedHoursViewHolder>() {
    private var workedHours = listOf<Pair<String, Int>>()

    fun setWorkedHours(workedHours: List<Pair<String, Int>>) {
        this.workedHours = workedHours
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkedHoursViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_worked_hours, parent, false)
        return WorkedHoursViewHolder(view)
    }

    override fun onBindViewHolder(holder: WorkedHoursViewHolder, position: Int) {
        val (date, workedHours) = workedHours[position]
        holder.bind(date, workedHours)
    }

    override fun getItemCount() = workedHours.size


}

class WorkedHoursViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val dateTextView = view.findViewById<TextView>(R.id.date_text_view)
    private val workedHoursTextView = view.findViewById<TextView>(R.id.worked_hours_text_view)

    fun bind(date: String, workedHours: Int) {
        dateTextView.text = date
        workedHoursTextView.text = "$workedHours hours"
    }
}