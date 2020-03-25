package com.nhinguyen.test.utopiacities.data

import android.database.Cursor
import com.nhinguyen.test.utopiacities.model.City
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by NhiNguyen on 3/25/2020.
 */

@Singleton
class DBManager @Inject constructor(private val helper: DBHelper) {
    fun getCities(limit: Int, page: Int = 0): List<City> {
        Timber.d("DBManager getCities limit: $limit; page: $page")
        val offset = limit * page
        var cursor: Cursor? = null
        val cities = arrayListOf<City>()
        try {
            val db = helper.writableDatabase
            cursor = db.rawQuery(
                "select * from ${DBHelper.USER_TABLE_NAME} LIMIT $limit OFFSET $offset",
                null
            )
            if (cursor?.count ?: 0 > 0) {
                cursor?.moveToFirst()
                do {
                    val city = City(
                        cursor?.getString(cursor.getColumnIndex(DBHelper.USER_COLUMN_ID)),
                        cursor?.getString(cursor.getColumnIndex(DBHelper.USER_COLUMN_CITY)),
                        cursor?.getString(cursor.getColumnIndex(DBHelper.USER_COLUMN_COUNTRY)),
                        cursor?.getFloat(cursor.getColumnIndex(DBHelper.USER_COLUMN_POPULATION))
                            ?: 0f
                    )
                    cities.add(city)
                } while (cursor?.moveToNext() == true)
            }
        } catch (e: NullPointerException) {
            e.printStackTrace()
        } finally {
            cursor?.close()
            return cities
        }
    }

}