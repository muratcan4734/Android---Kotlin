package com.murat.expensestracker_assignment1

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast

class CreateExpense : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_expense)

        //Keep Data From activity_monthly_expenses INTENT
        val ExpDateId = intent.getStringExtra("ExpDateId")
        val ExpDateName =  intent.getStringExtra("ExpDateName")
        val MonthlyIncome = intent.getStringExtra("MonthlyIncome")

        val ExpName = findViewById<EditText>(R.id.EdtText_CrtExpName).text
        val ExpAmount = findViewById<EditText>(R.id.EdtTxt_CrtExpAmount).text

        val cv = ContentValues()
        val helper = DataBaseHelper(applicationContext)
        val db = helper.writableDatabase

        findViewById<Button>(R.id.BtnCreate).setOnClickListener {
                 val ExpStatus = if (findViewById<CheckBox>(R.id.ChckBoxRegular).isChecked) "TRUE" else "FALSE"

                if (ExpName.isNotEmpty() && ExpAmount.isNotEmpty()){

                    cv.put("DATEID",ExpDateId.toString().toInt())
                    cv.put("EXPNAME",ExpName.toString())
                    cv.put("EXPAMOUNT",(ExpAmount.toString().toFloat()*(-1)))
                    cv.put("EXPSTATUS",ExpStatus)

                    db.insert("EXPENSES",null,cv)
                    Toast.makeText(applicationContext,"Expenses: $ExpName Create Successful",Toast.LENGTH_LONG).show()

                    val newintent = Intent(this,ActivityMonthlyExpenses::class.java)
                    newintent.putExtra("date_id",ExpDateId)
                    newintent.putExtra("date_name",ExpDateName)
                    newintent.putExtra("monthly_income",MonthlyIncome)

                    startActivity(newintent)
                }
                else{
                    findViewById<EditText>(R.id.EdtText_CrtExpName).setText("")
                    findViewById<EditText>(R.id.EdtText_CrtExpName).requestFocus()
                    findViewById<EditText>(R.id.EdtTxt_CrtExpAmount).setText("")
                    Toast.makeText(applicationContext,"Please Do Not Leave The Fields Blank",Toast.LENGTH_LONG).show()
                }
        }
    }
}