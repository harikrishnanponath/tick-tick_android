package com.h_ponathgopinadhan.ticktick

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

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
            val breakHoursEditText = view.findViewById<EditText>(R.id.break_hours_edit_text)

            builder.setView(view)
                .setPositiveButton(R.string.save) { _, _ ->
                    val workedHours = workedHoursEditText.text.toString().toInt()
                    val breakHours = breakHoursEditText.text.toString().toInt()
                    saveWorkedHours(requireContext(), date, workedHours, breakHours)
                }
                .setNegativeButton(R.string.cancel) { _, _ ->
                    dismiss()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun saveWorkedHours(context: Context, date: String, workedHours: Int, breakHours: Int) {
        val sharedPreferences = context.getSharedPreferences("worked_hours", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val type = object : TypeToken<HashMap<String, Pair<Int, Int>>>() {}.type
        val workedHoursMap = sharedPreferences.getString("worked_hours_map", "{}")?.let {
            try {
                Gson().fromJson<HashMap<String, Pair<Int, Int>>>(it, type)
            } catch (e: JsonSyntaxException) {
                Log.e("Error", e.toString())
                hashMapOf()
            }
        } ?: hashMapOf()

        workedHoursMap[date] = Pair(workedHours, breakHours)
        editor.putString("worked_hours_map", Gson().toJson(workedHoursMap))
        editor.apply()
    }
}
