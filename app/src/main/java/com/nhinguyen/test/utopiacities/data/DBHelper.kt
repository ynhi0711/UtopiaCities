package com.nhinguyen.test.utopiacities.data

import android.content.Context
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.nhinguyen.test.utopiacities.application.AppModule
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
constructor(@AppModule.ApplicationContext val context: Context) :
    SQLiteOpenHelper(context, USER_DB_NAME, null, 1) {

    val path = (context.filesDir.parent ?: "") + "/databases/" + USER_DB_NAME

    init {
        createDatabase()
        openDatabase()
    }
    fun countRecord(): Int {
        val db = this.readableDatabase
        return DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM $USER_TABLE_NAME", null).toInt()
    }

    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
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
            Timber.d("connect database")
        } else {
            Timber.d("can not connect database")
            this.readableDatabase
            copyDatabase()
        }
        this.close()
        openDatabase()
    }

    companion object {
        const val USER_DB_NAME_FILE = "utopia_cities.db"
        const val USER_DB_NAME = "utopia_cities.sqlite"
        const val USER_TABLE_NAME = "cities"
        const val USER_COLUMN_ID = "id"
        const val USER_COLUMN_CITY = "city"
        const val USER_COLUMN_POPULATION = "population"
        const val USER_COLUMN_COUNTRY = "country"
    }
}