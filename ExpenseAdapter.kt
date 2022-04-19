package com.murat.expensestracker_assignment1

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExpenseAdapter(private val expenses:ArrayList<Expense>,
                     private val onExpenseClickListener: OnExpenseClickListener): RecyclerView.Adapter<ExpenseViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        return ExpenseViewHolder(LayoutInflater.from(parent.context).
        inflate(R.layout.layout_list_expense,parent,false))
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = expenses[position]
        holder.itemView.findViewById<TextView>(R.id.exp_name).text = expense.exp_name
        holder.itemView.findViewById<TextView>(R.id.exp_amount).text = expense.exp_amount.toString()
        holder.itemView.findViewById<TextView>(R.id.exp_status).text = expense.exp_statuse

        holder.itemView.setOnClickListener{
            onExpenseClickListener.onExpenseItemClicked(position)
        }
    }
    override fun getItemCount(): Int {
        return expenses.size
    }
}