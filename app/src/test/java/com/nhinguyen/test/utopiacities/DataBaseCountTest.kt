package com.nhinguyen.test.utopiacities

import android.os.Build.VERSION_CODES.LOLLIPOP
import com.nhinguyen.test.utopiacities.data.DBHelper
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricGradleTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

/**
 * Created by NhiNguyen on 3/24/2020.
 */

@RunWith(RobolectricGradleTestRunner::class)
@Config(
    constants = BuildConfig::class,
    sdk = intArrayOf(LOLLIPOP),
    packageName = "com.nhinguyen.test.utopiacities"
)
class DataBaseCountTest {
    lateinit var dbHelper: DBHelper

    @Before
    fun setup() {
        dbHelper = DBHelper(RuntimeEnvironment.application)
    }

    @Test
    @Throws(Exception::class)
    fun testDbInsertion() {
        assertEquals(272128, dbHelper.countRecord())
    }

    @After
    fun tearDown() {
//        dbHelper.clearDb()
    }
}
