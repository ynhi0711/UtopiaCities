package com.nhinguyen.test.utopiacities.data

import android.content.Context
import android.database.Cursor
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.nhinguyen.test.utopiacities.application.AppModule
import com.nhinguyen.test.utopiacities.model.City
import timber.log.Timber
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Created by NhiNguyen on 3/24/2020.
 */

@Singleton
open class DBHelper @Inject
constructor(
    @AppModule.ApplicationContext val context: Context
) : SQLiteOpenHelper(context, USER_DB_NAME, null, 1) {

    val path = (context.filesDir.parent ?: "") + "/databases/" + USER_DB_NAME

    fun getCities(limit: Int, page: Int = 0): List<City> {
        val offset = limit * page
        var cursor: Cursor? = null
        val listGirl = arrayListOf<City>()
        try {
            val db = this.writableDatabase
            cursor = db.rawQuery("select * from $USER_TABLE_NAME LIMIT $limit OFFSET $offset", null)

            if (cursor?.count ?: 0 > 0) {
                cursor?.moveToFirst()
                do {
                    val city = City(
                        cursor?.getString(cursor.getColumnIndex(USER_COLUMN_ID)),
                        cursor?.getString(cursor.getColumnIndex(USER_COLUMN_CITY)),
                        cursor?.getString(cursor.getColumnIndex(USER_COLUMN_COUNTRY)),
                        cursor?.getFloat(cursor.getColumnIndex(USER_COLUMN_POPULATION)) ?: 0f
                    )
                    listGirl.add(city)
                } while (cursor?.moveToNext() == true)
            }
        } catch (e: NullPointerException) {
            e.printStackTrace()
        } finally {
            cursor?.close()
            return listGirl
        }
    }

    fun countRecord(): Int {
        val db = this.readableDatabase
        return DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM $USER_TABLE_NAME", null).toInt()
    }


    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
//        tableCreateStatements(sqLiteDatabase)
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS $USER_TABLE_NAME")
        onCreate(sqLiteDatabase)
    }

    protected fun clearDatabase() {
        writableDatabase.execSQL("delete from $USER_TABLE_NAME")
    }

    fun openDatabase(): SQLiteDatabase {
        return SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE)
    }

    private fun copyDatabase() {
        try {
            val `is` = context.assets.open(USER_DB_NAME_FILE)
            val os = FileOutputStream(path)
            val buffer = ByteArray(1024)
            while (`is`.read(buffer) > 0) {
                os.write(buffer)
                Timber.d("writing>>")
            }
            os.flush()
            os.close()
            `is`.close()

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun checkDatabase(): Boolean {
        var checkDB: SQLiteDatabase? = null
        try {
            checkDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        checkDB?.close()

        return checkDB != null
    }

    fun createDatabase() {
        val kt = checkDatabase()
        if (kt) {
            Log.d("LOG ", "connect database")
        } else {
            Log.d("LOG ", "can not connect database")
            this.readableDatabase
            copyDatabase()
        }
        this.close()
        openDatabase()

    }

    companion object {
        val USER_DB_NAME_FILE = "utopia_cities.sqlite"
        val USER_DB_NAME = "utopia_cities.sqlite"
        val USER_TABLE_NAME = "cities"
        val USER_COLUMN_ID = "id"
        val USER_COLUMN_CITY = "city"
        val USER_COLUMN_POPULATION = "population"
        val USER_COLUMN_COUNTRY = "country"
    }
}