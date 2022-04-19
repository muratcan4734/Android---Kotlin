package com.murat.expensestracker_assignment1

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.murat.expensestracker_assignment1.R.id.TxtIncomeAmount

class ActivityMonthlyExpenses : AppCompatActivity(), OnExpenseClickListener{
    private var expenses = ArrayList<Expense>()

    @SuppressLint("CutPasteId", "NotifyDataSetChanged", "Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monthly_expenses)


        //Intent From MainActivity
        val ExpDate_Id = intent.getStringExtra("date_id")

        val ExpDate_Name = intent.getStringExtra("date_name")
        findViewById<TextView>(R.id.TxtDate).text = ExpDate_Name

        val Monthly_Income = intent.getStringExtra("monthly_income")
        findViewById<TextView>(R.id.EdtTxtIncome).text = Monthly_Income
        findViewById<TextView>(TxtIncomeAmount).text = Monthly_Income

        createExpensesList(ExpDate_Id)
        calculateTotalExpense()

        val expenseAdapter = ExpenseAdapter(expenses,this)
        findViewById<RecyclerView>(R.id.rv_expenses_list).layoutManager = LinearLayoutManager(this)
        findViewById<RecyclerView>(R.id.rv_expenses_list).adapter = expenseAdapter
        expenseAdapter.notifyDataSetChanged()

        findViewById<Button>(R.id.BtnCreateNewExp).setOnClickListener {
            val newintent = Intent(this,CreateExpense::class.java)
            newintent.putExtra("ExpDateId",ExpDate_Id)
            newintent.putExtra("ExpDateName",ExpDate_Name)
            newintent.putExtra("MonthlyIncome",Monthly_Income)

            startActivity(newintent)
        }

        findViewById<Button>(R.id.BtnExit).setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.BtnRegular).setOnClickListener {
            calculateRegularExpenseAmount()
        }

        val cv = ContentValues()
        val helper = DataBaseHelper(applicationContext)
        val db = helper.writableDatabase

        findViewById<Button>(R.id.BtnUpdateIncome).setOnClickListener {
            var newIncome = findViewById<EditText>(R.id.EdtTxtIncome).text
            if (newIncome.isNotEmpty()){
                val newIncomeFloat = newIncome.toString().toFloat()
                cv.put("DATEINCOME",newIncomeFloat)
                db.update("DATE",cv,"DATEID=$ExpDate_Id",null)
                Toast.makeText(applicationContext,"Income Updated SUCCESSFULLY",Toast.LENGTH_SHORT).show()
                findViewById<Button>(R.id.BtnExit).callOnClick()
            }
            else{
                Toast.makeText(applicationContext,"Please Enter Monthly Income", Toast.LENGTH_LONG).show()
            }
        }

        findViewById<Button>(R.id.BtnDeleteMonth).setOnClickListener {
            db.delete("DATE","DATEID=$ExpDate_Id",null)
            db.delete("EXPENSES","DATEID=$ExpDate_Id",null)
            Toast.makeText(applicationContext,"Month Deleted SUCCESSFULLY",Toast.LENGTH_SHORT).show()
            findViewById<Button>(R.id.BtnExit).callOnClick()
        }
    }

    @SuppressLint("Range", "Recycle")
    private fun createExpensesList(dateId: String?){

        val helper = DataBaseHelper(applicationContext)
        val db = helper.readableDatabase
        val expdate = dateId.toString().toInt()
        val query = "SELECT * FROM EXPENSES WHERE DATEID=$expdate"
        val result = db.rawQuery(query,null)

        if (result.moveToFirst()){
            do {
                val expId = result.getString(result.getColumnIndex("EXPID")).toInt()
                val expName = result.getString(result.getColumnIndex("EXPNAME"))
                val expAmount = result.getString(result.getColumnIndex("EXPAMOUNT")).toFloat()
                val expStatus = result.getString(result.getColumnIndex("EXPSTATUS"))

                expenses.add(Expense(expId,expdate, expName, expAmount, expStatus))

            }while (result.moveToNext())
            }
        }

    override fun onExpenseItemClicked(position: Int) {
        val intent = Intent(this,ExpenseDetail::class.java)
        val exp_id:String = expenses[position].exp_id.toString()
        val exp_amount:String = expenses[position].exp_amount.toString()

        intent.putExtra("ExpId",exp_id)
        intent.putExtra("ExpDateId",expenses[position].exp_date)
        intent.putExtra("ExpName",expenses[position].exp_name)
        intent.putExtra("ExpAmount",exp_amount)
        intent.putExtra("ExpStatus",expenses[position].exp_statuse)

        startActivity(intent)
    }

    private fun calculateTotalExpense(){
        var totalExpensesAmount= 0f
        for (i in 0 until(expenses.size)){
            totalExpensesAmount += expenses[i].exp_amount
        }
        findViewById<TextView>(R.id.TxtExpensesAmount).text = totalExpensesAmount.toString()
    }

    private fun calculateRegularExpenseAmount(){
        var totalExpensesAmount= 0f
        for (i in 0 until(expenses.size)){
            if (expenses[i].exp_statuse == "TRUE"){
                totalExpensesAmount += expenses[i].exp_amount
            }
        }
        findViewById<TextView>(R.id.TxtExpensesAmount).text = totalExpensesAmount.toString()
    }
}