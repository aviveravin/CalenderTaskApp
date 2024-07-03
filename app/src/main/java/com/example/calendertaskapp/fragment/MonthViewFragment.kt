package com.example.calendertaskapp.fragment


import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calendertaskapp.R
import com.example.calendertaskapp.adapter.MonthViewAdapter
import java.util.*

class MonthViewFragment : Fragment() {

    private lateinit var tvMonthYear: TextView
    private lateinit var rvDays: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_month_view, container, false)
        tvMonthYear = view.findViewById(R.id.tvMonthYear)
        rvDays = view.findViewById(R.id.rvDays)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)

        displayMonth(currentYear, currentMonth)
    }

    private fun displayMonth(year: Int, month: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, 1)
        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val days = mutableListOf<String>()

        for (i in 1..daysInMonth) {
            days.add(i.toString())
        }

        val monthYear = "${calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())} $year"
        tvMonthYear.text = monthYear

        val adapter = MonthViewAdapter(days) { day ->
            onDaySelected(day)
        }
        rvDays.layoutManager = GridLayoutManager(context, 7)
        rvDays.adapter = adapter
    }

    private fun onDaySelected(day: String) {
        showAddTaskDialog(day)
    }

    private fun showAddTaskDialog(day: String) {
        val builder = AlertDialog.Builder(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_task, null)
        builder.setView(dialogView)

        val dialog = builder.create()

        val etTaskTitle = dialogView.findViewById<EditText>(R.id.etTaskTitle)
        val etTaskDescription = dialogView.findViewById<EditText>(R.id.etTaskDescription)
        val btnAddTask = dialogView.findViewById<Button>(R.id.btnAddTask)

        btnAddTask.setOnClickListener {
            val taskTitle = etTaskTitle.text.toString().trim()
            val taskDescription = etTaskDescription.text.toString().trim()

            if (taskTitle.isEmpty() || taskDescription.isEmpty()) {
                Toast.makeText(context, "Please enter both title and description", Toast.LENGTH_SHORT).show()
            } else {
                // Handle adding the task (e.g., making an API call)
                dialog.dismiss()
                Toast.makeText(context, "Task added for $day", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }
}
