package com.nhinguyen.test.utopiacities

import android.content.Context
import android.database.DatabaseUtils
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.nhinguyen.test.utopiacities.data.DBHelper
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * Created by NhiNguyen on 3/25/2020.
 */

@RunWith(AndroidJUnit4::class)
@LargeTest
class DatabaseUnitTest {
    private lateinit var dbHelper: DBHelper

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        dbHelper = DBHelper(context)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        dbHelper.close()
    }

    @Test
    @Throws(Exception::class)
    fun testTotalRecord() {
        val db = dbHelper.readableDatabase
        val count =
            DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM ${DBHelper.USER_TABLE_NAME}", null)
                .toInt()
        val expectResult = 272128
        assertThat(count, equalTo(expectResult))
    }
}