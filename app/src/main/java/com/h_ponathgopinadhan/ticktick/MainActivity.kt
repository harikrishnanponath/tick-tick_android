package com.h_ponathgopinadhan.ticktick

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CalendarView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

    lateinit var deleteButton : FloatingActionButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        deleteButton = findViewById(R.id.floatingActionButton)

        instance = this

        showResults()
        val calendarView = findViewById<CalendarView>(R.id.calendar_view)
        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val date = "$year/${month + 1}/$dayOfMonth"
            val dialogFragment = WorkedHoursDialogFragment.newInstance(date)
            dialogFragment.show(supportFragmentManager, "worked_hours_dialog")
            showResults()
        }


        //deleting all the entries
        deleteButton.setOnClickListener{

            val fragment = supportFragmentManager.findFragmentById(R.id.main_container)
            if (fragment is WorkedHoursListFragment) {
                fragment.deleteAllEntries()
            }
        }
    }

    fun showResults(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, WorkedHoursListFragment.newInstance())
            .commit()

    }

    companion object {
        private lateinit var instance: MainActivity

        fun getInstance(): MainActivity {
            return instance
        }
    }
}