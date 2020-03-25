package com.nhinguyen.test.utopiacities.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.io.*
import java.sql.SQLException


/**
 * Created by NhiNguyen on 25/03/2020.
 */
class DataBaseHelper(context: Context) :
    SQLiteOpenHelper(
        context,
        DATABASE_NAME,
        null,
        DATABASE_VERSION
    ) {
    private var myDataBase: SQLiteDatabase? = null
    private val myContext: Context = context

    //Create a empty database on the system
    @Throws(IOException::class)
    fun createDatabase() {
        val dbExist = checkDataBase()
        if (dbExist) {
            Log.v("DB Exists", "db exists")
        }
        val dbExist1 = checkDataBase()
        if (!dbExist1) {
            this.readableDatabase
            try {
                close()
                copyDataBase()
            } catch (e: IOException) {
                throw Error("Error copying database")
            }
        }
    }

    //Check database already exist or not
    private fun checkDataBase(): Boolean {
        var checkDB = false
        try {
            val myPath =
                DATABASE_PATH + DATABASE_NAME
            val dbfile = File(myPath)
            checkDB = dbfile.exists()
        } catch (e: SQLiteException) {
        }
        return checkDB
    }

    //Copies your database from your local assets-folder to the just created empty database in the system folder
    @Throws(IOException::class)
    private fun copyDataBase() {
        val mInput: InputStream = myContext.assets.open(DATABASE_NAME)
        val outFileName =
            DATABASE_PATH + DATABASE_NAME
        val mOutput: OutputStream = FileOutputStream(outFileName)
        val mBuffer = ByteArray(2024)
        var mLength: Int
        while (mInput.read(mBuffer).also { mLength = it } > 0) {
            mOutput.write(mBuffer, 0, mLength)
        }
        mOutput.flush()
        mOutput.close()
        mInput.close()
    }

    //delete database
    fun db_delete() {
        val file =
            File(DATABASE_PATH + DATABASE_NAME)
        if (file.exists()) {
            file.delete()
            println("delete database file.")
        }
    }

    //Open database
    @Throws(SQLException::class)
    fun openDatabase() {
        val myPath =
            DATABASE_PATH + DATABASE_NAME
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE)
    }

    @Synchronized
    @Throws(SQLException::class)
    fun closeDataBase() {
        if (myDataBase != null) myDataBase!!.close()
        super.close()
    }

    override fun onCreate(db: SQLiteDatabase?) { // TODO Auto-generated method stub
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
        if (newVersion > oldVersion) {
            Log.v("Database Upgrade", "Database version higher than old.")
            db_delete()
        }
    }

    companion object {
        private const val DATABASE_NAME = "YOURDBNAME"
        const val DATABASE_PATH = "/data/data/com.your.packagename/databases/"
        const val DATABASE_VERSION = 1
    }

}