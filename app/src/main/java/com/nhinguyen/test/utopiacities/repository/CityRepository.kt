package com.nhinguyen.test.utopiacities.repository

import com.nhinguyen.test.utopiacities.data.DBHelper
import javax.inject.Inject

/**
 * Created by NhiNguyen on 3/24/2020.
 */

class CityRepository @Inject constructor(private val dbHelper: DBHelper) {
    fun getCities(limit: Int, page: Int = 0) = dbHelper.getCities(limit, page)
}