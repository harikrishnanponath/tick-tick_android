package com.h_ponathgopinadhan.ticktick

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*


class WorkedHoursAdapter (
    val onEditClick: (date: String, workedHours: Int, breakHours: Int) -> Unit,
    val onDeleteClick: (date: String) -> Unit
) : RecyclerView.Adapter<WorkedHoursViewHolder>() {

    private var wHours = listOf<Triple<String, Int, Int>>()

    fun setWorkedHours(workedHours: List<Pair<String, Pair<Int, Int>>>) {
        this.wHours = workedHours.map { (date, hours) ->
            Triple(date, hours.first, hours.second)
        }
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkedHoursViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_worked_hours, parent, false)
        return WorkedHoursViewHolder(view)
    }

    override fun onBindViewHolder(holder: WorkedHoursViewHolder, position: Int) {
        val (date, workedHours, breakHours) = wHours[position]
        holder.bind(date, workedHours, breakHours)
        holder.itemView.setOnClickListener {
            onEditClick(date, workedHours, breakHours)
        }

        holder.editButton.setOnClickListener {
            onEditClick(date, workedHours, breakHours)
        }

        holder.deleteButton.setOnClickListener {
            onDeleteClick(date)
        }

        val currentItem = wHours[position]

        //deducting the break hour
        val netWorkedHours = currentItem.second - currentItem.third

        //total Worked hours calc
        val totalWorkedHours = netWorkedHours
        

    }

    override fun getItemCount() = wHours.size
}

class WorkedHoursViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val dateTextView = view.findViewById<TextView>(R.id.date_text_view)
    val workedHoursTextView = view.findViewById<TextView>(R.id.worked_hours_text_view)
    val breakHoursTextView = view.findViewById<TextView>(R.id.break_hours_text_view)
    val editButton = view.findViewById<ImageButton>(R.id.editButton)
    val deleteButton = view.findViewById<ImageButton>(R.id.deleteButton)

    fun bind(date: String, workedHours: Int, breakHours: Int) {
        dateTextView.text = date
        workedHoursTextView.text = "Worked Hours: $workedHours hours"
        breakHoursTextView.text = "Break Hours: $breakHours hours"
    }
}
