package com.murat.expensestracker_assignment1

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class ExpenseDetail : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_detail)

        //Intent keep data from ActivityMonthlyExpenses
        val ExpId = intent.getStringExtra("ExpId").toString().toInt()

        val EdtText_ExpName = findViewById<EditText>(R.id.EdtText_ExpName)
        val EXPNAME = intent.getStringExtra("ExpName")
        EdtText_ExpName.setText(EXPNAME)

        val EdtTxt_ExpAmount = findViewById<EditText>(R.id.EdtTxt_ExpAmount)
        val EXPAMOUNT = intent.getStringExtra("ExpAmount").toString().toFloat()*(-1)
        EdtTxt_ExpAmount.setText(EXPAMOUNT.toString())

        val ExpStatus = intent.getStringExtra("ExpStatus")
        findViewById<TextView>(R.id.TxtViewExpRegularStatus).setText("$EXPNAME Regular Status: $ExpStatus").toString()

        //DataBase
        val cv = ContentValues()
        val helper = DataBaseHelper(applicationContext)
        val db = helper.writableDatabase

        findViewById<Button>(R.id.BtnExpUpdate).setOnClickListener {
            if (EdtText_ExpName.text.isNotEmpty() && EdtTxt_ExpAmount.text.isNotEmpty()){

                val ExpNewName = EdtText_ExpName.text.toString()
                val ExpNewAmount = EdtTxt_ExpAmount.text.toString().toFloat()
                val ExpNewStatus = if (findViewById<CheckBox>(R.id.ChckBoxRegularDetail).isChecked) "TRUE" else "FALSE"

                cv.put("EXPNAME",ExpNewName)
                cv.put("EXPAMOUNT",ExpNewAmount*(-1))
                cv.put("EXPSTATUS",ExpNewStatus)
                db.update("EXPENSES",cv,"EXPID=$ExpId",null)
                Toast.makeText(applicationContext,"New Expense Updated SUCCESSFULLY",Toast.LENGTH_SHORT).show()
                GoMainAvtivity()
            }else{
                Toast.makeText(applicationContext,"Please Do Not Leave The Fields Blank",Toast.LENGTH_LONG).show()
            }
        }

        findViewById<Button>(R.id.BtnExpDelete).setOnClickListener {
            db.delete("EXPENSES","EXPID=$ExpId" ,null)
            Toast.makeText(applicationContext,"Expense Deleted SUCCESSFULLY",Toast.LENGTH_SHORT).show()
            GoMainAvtivity()
        }
    }
    fun GoMainAvtivity(){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
}