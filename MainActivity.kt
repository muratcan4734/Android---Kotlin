package com.murat.expensestracker_assignment1

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity(),OnMonthClickListener {
    private var monthlies = ArrayList<Month>()

    @SuppressLint("CutPasteId", "NotifyDataSetChanged", "SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val monthAdapter = MonthAdapter(monthlies,this)
        createMonthList()

        findViewById<RecyclerView>(R.id.rv_monthlies_list).layoutManager = LinearLayoutManager(this)
        findViewById<RecyclerView>(R.id.rv_monthlies_list).adapter = monthAdapter
        monthAdapter.notifyDataSetChanged()

        var MonthName:String? = ""
        var Year:String? = ""

        val SpnMonths = findViewById<Spinner>(R.id.Spn_Month)
        val SpnYears = findViewById<Spinner>(R.id.Spn_Year)

        SpnMonths.onItemSelectedListener = object:AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                MonthName = p0?.getItemAtPosition(p2).toString()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        SpnYears.onItemSelectedListener = object:AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Year = p0?.getItemAtPosition(p2).toString()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        val CurrentMonth = SimpleDateFormat("LLL").format(System.currentTimeMillis())
        val CurrentYear = SimpleDateFormat("yyyy").format(System.currentTimeMillis())

        val MonthIncome = findViewById<EditText>(R.id.EdtTxtMonthIncome).text

        //Crate new A Sheet inside the DataBase
        val cv = ContentValues()
        val helper = DataBaseHelper(this)
        val db = helper.writableDatabase

        findViewById<Button>(R.id.BtnCreateSheet).setOnClickListener {
            if (MonthIncome.isNotEmpty()) {
                if (MonthName == "Select"){MonthName = CurrentMonth}
                if (Year == "Select"){Year = CurrentYear}
                val DateName = "$MonthName - $Year"
                cv.put("DATENAME", DateName)
                cv.put("DATEINCOME", MonthIncome.toString().toFloat())
                db.insert("DATE", null, cv)
                Toast.makeText(
                    applicationContext,
                    "Create New Sheet SUCCESSFUL",
                    Toast.LENGTH_SHORT
                ).show()
                createMonthList()
                startActivity(Intent(this, MainActivity::class.java))
            }else{
                Toast.makeText(applicationContext,"Please Enter Monthly Income",Toast.LENGTH_LONG).show()
            }
        }
    }

    @SuppressLint("Range", "Recycle")
    fun createMonthList(){
        val helper = DataBaseHelper(this)
        val db = helper.readableDatabase
        val query = "SELECT * FROM DATE"
        val result = db.rawQuery(query,null)
        if (result.moveToFirst()){
            do {
                val DateId = result.getString(result.getColumnIndex("DATEID")).toInt()
                val Monthly_Total_Expense_Amount: Float = calculate_monthly_total_Expense(DateId)
                val DateName = result.getString(result.getColumnIndex("DATENAME"))
                val DateIncome = result.getString(result.getColumnIndex("DATEINCOME")).toFloat()
                val DateAmount = DateIncome + Monthly_Total_Expense_Amount.toString().toFloat()
                monthlies.add(Month(DateId,DateName,DateAmount,DateIncome))
            }while (result.moveToNext())
        }
    }

    @SuppressLint("Range", "Recycle")
    fun calculate_monthly_total_Expense(dateId:Int): Float {

        var totalExpensesAmount= 0f
        val helper = DataBaseHelper(applicationContext)
        val db = helper.readableDatabase
        val query = "SELECT EXPAMOUNT FROM EXPENSES WHERE DATEID=$dateId"
        val result = db.rawQuery(query,null)
        if (result.moveToFirst()){
         do {
             totalExpensesAmount += result.getString(result.getColumnIndex("EXPAMOUNT")).toFloat()
         }while (result.moveToNext())
        }
        return totalExpensesAmount
    }

    override fun onMonthItemClicked(position: Int) {
        val intent = Intent(this,ActivityMonthlyExpenses::class.java)

        val monthly_income:String = monthlies[position].monthly_income.toString()
        val dateID:String =monthlies[position].date_id.toString()
        val dateName:String = monthlies[position].date_name

        intent.putExtra("date_id",dateID)
        intent.putExtra("date_name",dateName)
        intent.putExtra("monthly_income",monthly_income)

        startActivity(intent)
    }
}