package com.h_ponathgopinadhan.ticktick

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.DecimalFormat



class WorkedHoursAdapter (
    val onEditClick: (date: String, workedHours: Float, breakHours: Float) -> Unit,
    val onDeleteClick: (date: String) -> Unit
) : RecyclerView.Adapter<WorkedHoursViewHolder>() {

    private var wHours = listOf<Triple<String, Float, Float>>()

    fun setWorkedHours(workedHours: List<Pair<String, Pair<Float, Float>>>) {
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

        }

        holder.editButton.setOnClickListener {
            onEditClick(date, workedHours, breakHours)
        }

        holder.deleteButton.setOnClickListener {
            onDeleteClick(date)
        }

        val currentItem = wHours[position]
        val decimalFormat = DecimalFormat("#.##")
        val netWorkedHours = currentItem.second - currentItem.third

        //total Worked hours calc
        val totalWorkedHours = netWorkedHours
        val formattedValue = decimalFormat.format(totalWorkedHours)
        holder.totalWorkedHoursTextView.text = "${formattedValue} hours net worked"


    }

    override fun getItemCount() = wHours.size
}

class WorkedHoursViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val dateTextView = view.findViewById<TextView>(R.id.date_text_view)
    val workedHoursTextView = view.findViewById<TextView>(R.id.worked_hours_text_view)
    val breakHoursTextView = view.findViewById<TextView>(R.id.break_hours_text_view)
    val totalWorkedHoursTextView = view.findViewById<TextView>(R.id.total_worked_hours_text_view)
    val editButton = view.findViewById<ImageButton>(R.id.editButton)
    val deleteButton = view.findViewById<ImageButton>(R.id.deleteButton)

    val decimalFormat = DecimalFormat("#.##")

    fun bind(date: String, workedHours: Float, breakHours: Float) {
        dateTextView.text = date
        val formattedValueWorked = decimalFormat.format(workedHours)
        val formattedValueBreak = decimalFormat.format(breakHours)
        workedHoursTextView.text = "Worked Hours: $formattedValueWorked hours"
        breakHoursTextView.text = "Break Hours: $formattedValueBreak hours"
    }
}
