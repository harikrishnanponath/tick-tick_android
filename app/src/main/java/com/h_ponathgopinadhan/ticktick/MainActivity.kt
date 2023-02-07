package com.h_ponathgopinadhan.ticktick

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

import android.widget.CalendarView

import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var show: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        show = findViewById(R.id.show)

        val calendarView = findViewById<CalendarView>(R.id.calendar_view)
        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val date = "$year/${month + 1}/$dayOfMonth"
//            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
//            val date = dateFormat.format(Date(year - 1900, month, dayOfMonth))
            val dialogFragment = WorkedHoursDialogFragment.newInstance(date)
            dialogFragment.show(supportFragmentManager, "worked_hours_dialog")
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, WorkedHoursListFragment.newInstance())
                .commit()

        }

        show.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, WorkedHoursListFragment.newInstance())
                .commit()
        }
    }


}
