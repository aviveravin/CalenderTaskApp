package com.example.calendertaskapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.calendertaskapp.R
import java.util.*

class MonthViewAdapter(
    private val days: MutableList<Date?>,
    private val onDateClick: (Date) -> Unit
) : RecyclerView.Adapter<MonthViewAdapter.DayViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_day, parent, false)
        return DayViewHolder(view)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        val date = days[position]
        if (date != null) {
            holder.bind(date, onDateClick)
        }
    }

    override fun getItemCount(): Int = days.size

    class DayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dayTextView: TextView = itemView.findViewById(R.id.tvDay)
        private val dateFormat = java.text.SimpleDateFormat("d", Locale.US)
        fun bind(date: Date, onDateClick: (Date) -> Unit) {
            dayTextView.text = dateFormat.format(date)
            itemView.setOnClickListener {
                onDateClick(date)
            }
        }
    }
}
