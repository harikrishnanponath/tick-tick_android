package com.h_ponathgopinadhan.ticktick

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class WorkedHoursListFragment : Fragment() {
    private lateinit var workedHoursRecyclerView: RecyclerView
    lateinit var adapter: WorkedHoursAdapter

    companion object {
        fun newInstance(): WorkedHoursListFragment {
            return WorkedHoursListFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_worked_hours_list, container, false)
        workedHoursRecyclerView = view.findViewById(R.id.worked_hours_recycler_view)
        workedHoursRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = WorkedHoursAdapter(::onEditClick)
        workedHoursRecyclerView.adapter = adapter

        return view
    }


    override fun onResume() {
        super.onResume()
        val sharedPreferences = requireContext().getSharedPreferences("worked_hours", Context.MODE_PRIVATE)
        val workedHoursMapType = object : TypeToken<HashMap<String, Int>>() {}.type
        val workedHoursJson = sharedPreferences.getString("worked_hours_map", "{}")
        val workedHoursMap = try {
            Gson().fromJson<HashMap<String, Int>>(workedHoursJson, workedHoursMapType)
        } catch (e: JsonSyntaxException) {
            hashMapOf<String, Int>()
        }
        adapter.setWorkedHours(workedHoursMap.toList().sortedBy { (date, _) ->
            SimpleDateFormat("yyyy/MM/dd").parse(date)
        }.reversed())
    }

    private fun onEditClick(date: String, workedHours: Int) {
        val builder = AlertDialog.Builder(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.fragment_edit_dialog, null)
        builder.setView(dialogView)
        val workedHoursEditText = dialogView.findViewById<EditText>(R.id.editWorkedHours)
        workedHoursEditText.setText(workedHours.toString())
        builder.setPositiveButton("Edit") { _, _ ->
            val newWorkedHours = workedHoursEditText.text.toString().toIntOrNull()
            if (newWorkedHours == null) {
                Toast.makeText(requireContext(), "Invalid worked hours", Toast.LENGTH_SHORT).show()
            } else {
                val sharedPreferences = requireContext().getSharedPreferences("worked_hours", Context.MODE_PRIVATE)
                val workedHoursMapType = object : TypeToken<HashMap<String, Int>>() {}.type
                val workedHoursJson = sharedPreferences.getString("worked_hours_map", "{}")
                val workedHoursMap = try {
                    Gson().fromJson<HashMap<String, Int>>(workedHoursJson, workedHoursMapType)
                } catch (e: JsonSyntaxException) {
                    hashMapOf<String, Int>()
                }
                workedHoursMap[date] = newWorkedHours
                sharedPreferences.edit()
                    .putString("worked_hours_map", Gson().toJson(workedHoursMap))
                    .apply()
                adapter.setWorkedHours(workedHoursMap.toList().sortedBy { (date, _) ->
                    SimpleDateFormat("yyyy/MM/dd").parse(date)
                }.reversed())
            }
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }



}


