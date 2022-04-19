package com.murat.expensestracker_assignment1

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

private val database_name = "DataBaseExpTracker"
private val database_version = 1

class DataBaseHelper(context: Context):SQLiteOpenHelper(context,database_name,null, database_version) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE EXPENSES(EXPID INTEGER PRIMARY KEY AUTOINCREMENT,DATEID INTEGER,EXPNAME VARCHAR,EXPAMOUNT NUMERIC,EXPSTATUS TEXT)")
        db?.execSQL("CREATE TABLE DATE(DATEID INTEGER PRIMARY KEY AUTOINCREMENT,DATENAME TEXT,DATEINCOME NUMERIC)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
    }
}