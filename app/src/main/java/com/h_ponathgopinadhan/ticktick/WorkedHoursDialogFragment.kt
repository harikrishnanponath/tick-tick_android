package com.h_ponathgopinadhan.ticktick

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.app.Dialog
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WorkedHoursDialogFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class WorkedHoursDialogFragment : DialogFragment() {

    private lateinit var date: String

    companion object {
        fun newInstance(date: String): WorkedHoursDialogFragment {
            val workedHoursDialogFragment = WorkedHoursDialogFragment()
            workedHoursDialogFragment.date = date
            Log.i("DATEEEE", date)
            return workedHoursDialogFragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.fragment_worked_hours_dialog, null)
            val workedHoursEditText = view.findViewById<EditText>(R.id.worked_hours_edit_text)

            builder.setView(view)
                .setPositiveButton(R.string.save) { _, _ ->
                    val workedHours = workedHoursEditText.text.toString().toInt()
                    saveWorkedHours(requireContext(), date, workedHours)
                }
                    //negative Button
                .setNegativeButton(R.string.cancel) { _, _ ->
                    dismiss()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun saveWorkedHours(context: Context, date: String, workedHours: Int) {
        val sharedPreferences = context.getSharedPreferences("worked_hours", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val type = object : TypeToken<HashMap<String, Int>>() {}.type
        val workedHoursMap = sharedPreferences.getString("worked_hours_map", "{}")?.let {
            Gson().fromJson<HashMap<String, Int>>(it, type)
        } ?: hashMapOf()

        workedHoursMap[date] = workedHours
        editor.putString("worked_hours_map", Gson().toJson(workedHoursMap))
        editor.apply()

    }
}




