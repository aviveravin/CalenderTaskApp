package com.example.calendertaskapp.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.GridLayoutManager
import com.example.calendertaskapp.R
import com.example.calendertaskapp.adapter.MonthViewAdapter
import com.example.calendertaskapp.databinding.FragmentMonthViewBinding
import com.example.calendertaskapp.model.TaskData
import com.example.calendertaskapp.model.TaskRequest
import com.example.calendertaskapp.utils.ViewState
import com.example.calendertaskapp.viewmodel.TaskViewModel
import com.example.calendertaskapp.viewmodel.TaskViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MonthViewFragment : Fragment() {

    private var _binding: FragmentMonthViewBinding? = null
    private val binding get() = _binding!!

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    private var selectedDate: Date? = null
    private val calendar = Calendar.getInstance()
    @Inject
    lateinit var viewModelFactory: TaskViewModelFactory

    private val taskViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(TaskViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMonthViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateMonthName()
        displayWeekdayNames()
        displayMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH))

        binding.btnPrevMonth.setOnClickListener {
            calendar.add(Calendar.MONTH, -1)
            updateMonthName()
            displayMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH))
        }

        binding.btnNextMonth.setOnClickListener {
            calendar.add(Calendar.MONTH, 1)
            updateMonthName()
            displayMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH))
        }
        setObservers()
    }

    private fun showAddTaskDialog(day: Date) {
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
                if (day.before(Calendar.getInstance().time)) {
                    dialog.dismiss()
                    Toast.makeText(context, "Cannot add tasks for past dates", Toast.LENGTH_SHORT).show()
                } else {
                    dialog.dismiss()
                    taskViewModel.addTask(
                        taskRequest = TaskRequest(
                            123,
                            task = TaskData(
                                title = taskTitle,
                                description = taskDescription,
                                date = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(day)
                            )
                        )
                    )
                }
            }
        }

        dialog.show()
    }

    private fun setObservers() {
        taskViewModel.storeTaskFlow.asLiveData()
            .observe(requireActivity()) { viewState ->
                when (viewState) {
                    is ViewState.Loading -> {
                        Log.d("is ViewState.ErrorsData ->", "setObservers: Loading...")

                    }

                    is ViewState.Success -> {
                        Log.d("aviveravin", "store task")
                        Toast.makeText(context, "Task added for $", Toast.LENGTH_SHORT).show()
                    }

                    is ViewState.NetworkFailed -> {
                        Log.d("aviveravin", "setObservers: Network failed")
                        Toast.makeText(
                            requireContext(),
                            "Constants.NO_INTERNET_MESSAGE",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    is ViewState.ErrorsData -> {
                        Log.d("aviveravin", "setObservers: ${viewState.errorData}")
                    }

                    else -> {
                        Log.d("aviveravin", "else: $viewState")
                    }
                }
            }
    }

    private fun displayWeekdayNames() {
        val weekdays = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
        weekdays.forEach { weekday ->
            val textView = TextView(requireContext()).apply {
                text = weekday
                layoutParams = LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f
                )
                textAlignment = View.TEXT_ALIGNMENT_CENTER
            }
            binding.llWeekdays.addView(textView)
        }
    }

    private fun displayMonth(year: Int, month: Int) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, 1)
        }

        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1

        val days = mutableListOf<Date?>()

        // Add null for days before the 1st of the current month
        for (i in 0 until firstDayOfWeek) {
            days.add(null)
        }

        // Add dates for the current month
        for (day in 1..daysInMonth) {
            calendar.set(Calendar.DAY_OF_MONTH, day)
            days.add(calendar.time)
        }

        val adapter = MonthViewAdapter(days) { date ->
            date.let {
                selectedDate = it
                showAddTaskDialog(it)
            }
        }

        binding.recyclerViewCalendar.layoutManager = GridLayoutManager(requireContext(), 7)
        binding.recyclerViewCalendar.adapter = adapter
    }



    private fun updateMonthName() {
        val monthFormat = SimpleDateFormat("MMMM yyyy", Locale.US)
        binding.tvMonth.text = monthFormat.format(calendar.time)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}






