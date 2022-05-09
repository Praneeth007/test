package com.example.imptest.api

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
        SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    // below is the method for creating a database by a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        // below is a sqlite query, where column names
        // along with their data types is given


        val testedQuery = ("CREATE TABLE " + TABLE_NAME_TEST + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FLAG_COL + " INTEGER DEFAULT 0, "
                + TESTED_AS_OF + " TEXT,"
                + DAILY_SAMPLE_RTP + " TEXT,"
                + SAMPLE_REPORTED_TODAY + " TEXT,"
                + TOTAL_DOSE_ADMINISTERED + " TEXT,"
                + SOURCE + " TEXT)")

        val casesQuery = ("CREATE TABLE " + TABLE_NAME_CASES + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FLAG_COL + " INTEGER DEFAULT 0, "
                + DATE_COL + " TEXT,"
                + TOTAL_CONFIRMED_COL + " TEXT,"
                + TOTAL_DECEASED_COL + " TEXT,"
                + TOTAL_RECOVERED_COL + " TEXT)")


        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FLAG_COL + " INTEGER DEFAULT 0, "
                + STATE_COL + " TEXT,"
                + ACTIVE_COL + " TEXT,"
                + RECOVERED_COL + " TEXT,"
                + DEATHS_COL + " TEXT)")

        // we are calling sqlite
        // method for executing our query
        db.execSQL(query)
        db.execSQL(casesQuery)
        db.execSQL(testedQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // this method is to check if table already exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CASES)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TEST)
        onCreate(db)
    }


    fun deleteTables(){
        val db = this.writableDatabase
        db.execSQL("delete from "+ TABLE_NAME)
        db.execSQL("delete from "+ TABLE_NAME_CASES)
        db.execSQL("delete from "+ TABLE_NAME_TEST)
        db.close()

    }
    fun updateQuery(tableName:String,flag :Int,id:Int, callback: DoneCallback? = null){
//        UPDATE COMPANY SET ADDRESS = 'Texas' WHERE ID = 6;

        val db = this.writableDatabase
        db.execSQL("UPDATE $tableName SET $FLAG_COL = '$flag' WHERE $ID_COL = $id")
        db.close()
        callback?.done()

    }
    interface DoneCallback {
        fun done()
    }


    // This method is for adding data in our database
    fun addStateWise(state: String, active: String, recovered: String, deaths: String,flag:Int) {

        // below we are creating
        // a content values variable
        val values = ContentValues()

        // we are inserting our values
        // in the form of key-value pair
        values.put(FLAG_COL, flag)
        values.put(STATE_COL, state)
        values.put(ACTIVE_COL, active)
        values.put(RECOVERED_COL, recovered)
        values.put(DEATHS_COL, deaths)

        // here we are creating a
        // writable variable of
        // our database as we want to
        // insert value in our database
        val db = this.writableDatabase

        // all values are inserted into database
        db.insert(TABLE_NAME, null, values)

        // at last we are
        // closing our database
        db.close()
    }

    // This method is for adding data in our database
    fun addTestWise(testedasof: String, dailyrtpcrsamplescollectedicmrapplication: String, samplereportedtoday: String, totaldosesadministered: String,source: String,flag: Int) {

        // below we are creating
        // a content values variable
        val values = ContentValues()

        // we are inserting our values
        // in the form of key-value pair
        values.put(FLAG_COL, flag)
        values.put(TESTED_AS_OF, testedasof)
        values.put(DAILY_SAMPLE_RTP, dailyrtpcrsamplescollectedicmrapplication)
        values.put(SAMPLE_REPORTED_TODAY, samplereportedtoday)
        values.put(TOTAL_DOSE_ADMINISTERED, totaldosesadministered)
        values.put(SOURCE, source)

        // here we are creating a
        // writable variable of
        // our database as we want to
        // insert value in our database
        val db = this.writableDatabase

        // all values are inserted into database
        db.insert(TABLE_NAME_TEST, null, values)

        // at last we are
        // closing our database
        db.close()
    }

    // This method is for adding data in our database
    fun addCaseWise(date: String, confirmed: String, deceased: String, recovered: String,flag: Int) {
        // below we are creating
        // a content values variable
        val values = ContentValues()

        // we are inserting our values
        // in the form of key-value pair
        values.put(FLAG_COL, flag)
        values.put(DATE_COL, date)
        values.put(TOTAL_CONFIRMED_COL, confirmed)
        values.put(TOTAL_DECEASED_COL, deceased)
        values.put(TOTAL_RECOVERED_COL, recovered)

        // here we are creating a
        // writable variable of
        // our database as we want to
        // insert value in our database
        val db = this.writableDatabase

        // all values are inserted into database
        db.insert(TABLE_NAME_CASES, null, values)

        // at last we are
        // closing our database
        db.close()
    }

    // below method is to get
    // all data from our database
    fun getStateWiseDetails(): MutableList<StateDetails> {
        val statesList = mutableListOf<StateDetails>()
        // here we are creating a readable
        // variable of our database
        // as we want to read value from it
        val db = this.readableDatabase

        // below code returns a cursor to
        // read data from the database
        val cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null)

        // moving our cursor to first position.
        // moving our cursor to first position.
        if (cursor.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                statesList.add(StateDetails(cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5)
                ))
            } while (cursor.moveToNext())
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        // at last closing our cursor
        // and returning our array list.
        cursor.close()

        return statesList

    }

    fun getStateWiseDetailsOffline(): MutableList<StateDetails> {
        val statesList = mutableListOf<StateDetails>()
        // here we are creating a readable
        // variable of our database
        // as we want to read value from it
        val db = this.readableDatabase

        // below code returns a cursor to
        // read data from the database
        val cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME +" where  $FLAG_COL == 1", null)

        // moving our cursor to first position.
        // moving our cursor to first position.
        if (cursor.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                statesList.add(StateDetails(cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5)
                ))
            } while (cursor.moveToNext())
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        // at last closing our cursor
        // and returning our array list.
        cursor.close()

        return statesList

    }
    fun getTestWiseDetails(): MutableList<TestDetails> {
        val testList = mutableListOf<TestDetails>()
        // here we are creating a readable
        // variable of our database
        // as we want to read value from it
        val db = this.readableDatabase

        // below code returns a cursor to
        // read data from the database
        val cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_TEST, null)

        // moving our cursor to first position.
        // moving our cursor to first position.
        if (cursor.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                testList.add(TestDetails(cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6)
                ))
            } while (cursor.moveToNext())
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        // at last closing our cursor
        // and returning our array list.
        cursor.close()

        return testList

    }
    fun getTestWiseDetailsOffline(): MutableList<TestDetails> {
        val testList = mutableListOf<TestDetails>()
        // here we are creating a readable
        // variable of our database
        // as we want to read value from it
        val db = this.readableDatabase

        // below code returns a cursor to
        // read data from the database
        val cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_TEST +" where  $FLAG_COL == 1", null)

        // moving our cursor to first position.
        // moving our cursor to first position.
        if (cursor.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                testList.add(TestDetails(cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6)
                ))
            } while (cursor.moveToNext())
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        // at last closing our cursor
        // and returning our array list.
        cursor.close()

        return testList

    }
    fun getCaseWiseDetails(): MutableList<CaseDetails> {
        val caseList = mutableListOf<CaseDetails>()
        // here we are creating a readable
        // variable of our database
        // as we want to read value from it
        val db = this.readableDatabase

        // below code returns a cursor to
        // read data from the database
        val cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_CASES, null)

        // moving our cursor to first position.
        // moving our cursor to first position.
        if (cursor.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                caseList.add(CaseDetails(cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5)
                ))
            } while (cursor.moveToNext())
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        // at last closing our cursor
        // and returning our array list.
        cursor.close()

        return caseList

    }
    fun getCaseWiseDetailsOffline(): MutableList<CaseDetails> {
        val caseList = mutableListOf<CaseDetails>()
        // here we are creating a readable
        // variable of our database
        // as we want to read value from it
        val db = this.readableDatabase

        // below code returns a cursor to
        // read data from the database
        val cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_CASES  +" where  $FLAG_COL == 1", null)

        // moving our cursor to first position.
        // moving our cursor to first position.
        if (cursor.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                caseList.add(CaseDetails(cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5)
                ))
            } while (cursor.moveToNext())
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        // at last closing our cursor
        // and returning our array list.
        cursor.close()

        return caseList

    }

    data class StateDetails(
            val id: String,
            val flag:String,
            val state: String,
            val active: String,
            val recovered: String,
            val deaths: String
    )

    data class CaseDetails(
            val id: String,
            val flag:String,
            val date: String,
            val totalconfirmed: String,
            val totaldeceased: String,
            val totalrecovered: String
    )

    data class TestDetails(
            val id: String,
            val flag:String,
            val testedasof: String,
            val dailyrtpcrsamplescollectedicmrapplication: String,
            val samplereportedtoday: String,
            val totaldosesadministered: String,
            val source: String
    )

    companion object {
        // here we have defined variables for our database

        // below is variable for database name
        private val DATABASE_NAME = "DATA_FOR_VAC"

        // below is the variable for database version
        private val DATABASE_VERSION = 1

        // below is the variable for table name
        val TABLE_NAME = "state_table"
        val TABLE_NAME_CASES = "cases_table"
        val TABLE_NAME_TEST = "tests_table"

        // below is the variable for id column
        val ID_COL = "id"
        val FLAG_COL = "flag"
        val STATE_COL = "state"
        val ACTIVE_COL = "active"
        val RECOVERED_COL = "recovered"
        val DEATHS_COL = "deaths"

        val DATE_COL = "date"
        val TOTAL_CONFIRMED_COL = "totalconfirmed"
        val TOTAL_DECEASED_COL = "totaldeceased"
        val TOTAL_RECOVERED_COL = "totalrecovered"

        val TESTED_AS_OF = "testedasof"
        val DAILY_SAMPLE_RTP = "dailyrtpcrsamplescollectedicmrapplication"
        val SAMPLE_REPORTED_TODAY = "samplereportedtoday"
        val TOTAL_DOSE_ADMINISTERED = "totaldosesadministered"
        val SOURCE = "source"


    }
}