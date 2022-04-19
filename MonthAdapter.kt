package com.murat.expensestracker_assignment1

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MonthAdapter(private val monthlies:ArrayList<Month>,
                   private val onExpenseClickListener: OnMonthClickListener): RecyclerView.Adapter<MonthViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthViewHolder {
        return MonthViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_list_month,parent,false))
    }

    override fun onBindViewHolder(holder: MonthViewHolder, position: Int) {
        val month = monthlies[position]

        holder.itemView.findViewById<TextView>(R.id.date_name).text = month.date_name
        holder.itemView.findViewById<TextView>(R.id.monthly_money_status).text = month.monthly_money_status.toString()
        holder.itemView.setOnClickListener { onExpenseClickListener.onMonthItemClicked(position) }
    }

    override fun getItemCount(): Int {
        return monthlies.size
    }
}