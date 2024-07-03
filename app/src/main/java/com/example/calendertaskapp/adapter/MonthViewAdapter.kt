package com.example.calendertaskapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.calendertaskapp.R
import java.util.*

class MonthViewAdapter(
    private val days: List<String>,
    private val onDayClick: (String) -> Unit
) : RecyclerView.Adapter<MonthViewAdapter.DayViewHolder>() {

    class DayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDay: TextView = itemView.findViewById(R.id.tvDay)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_day, parent, false)
        return DayViewHolder(view)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        val day = days[position]
        holder.tvDay.text = day
        holder.itemView.setOnClickListener {
            onDayClick(day)
        }
    }

    override fun getItemCount(): Int = days.size
}