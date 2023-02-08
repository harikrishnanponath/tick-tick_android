package com.h_ponathgopinadhan.ticktick

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView



class WorkedHoursAdapter (val onEditClick: (date: String, workedHours: Int) -> Unit, private val onDeleteClick: (date: String) -> Unit) :
    RecyclerView.Adapter<WorkedHoursViewHolder>() {
    private var workedHours = listOf<Pair<String, Int>>()
    private lateinit var listener: OnWorkedHoursClickListener

    fun setWorkedHours(workedHours: List<Pair<String, Int>>) {
        this.workedHours = workedHours
        notifyDataSetChanged()
    }

    fun setOnWorkedHoursClickListener(listener: OnWorkedHoursClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkedHoursViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_worked_hours, parent, false)
        return WorkedHoursViewHolder(view)
    }

    override fun onBindViewHolder(holder: WorkedHoursViewHolder, position: Int) {
        val (date, workedHours) = workedHours[position]
        holder.bind(date, workedHours)
        holder.itemView.setOnClickListener {
            listener.onWorkedHoursClick(date, workedHours)
        }

        holder.itemView.findViewById<ImageButton>(R.id.editButton).setOnClickListener {
            onEditClick(date, workedHours)
        }

        holder.itemView.findViewById<ImageButton>(R.id.deleteButton).setOnClickListener {
            onDeleteClick(date)
        }
    }



    override fun getItemCount() = workedHours.size

    interface OnWorkedHoursClickListener {
        fun onWorkedHoursClick(date: String, workedHours: Int)

    }
}



class WorkedHoursViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val dateTextView = view.findViewById<TextView>(R.id.date_text_view)
    private val workedHoursTextView = view.findViewById<TextView>(R.id.worked_hours_text_view)
    val editButton = view.findViewById<ImageButton>(R.id.editButton)
    val deleteButton = view.findViewById<ImageButton>(R.id.deleteButton)

    fun bind(date: String, workedHours: Int) {
        dateTextView.text = date
        workedHoursTextView.text = "$workedHours hours"
    }
}
