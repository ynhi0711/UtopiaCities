package com.nhinguyen.test.utopiacities.repository

import com.nhinguyen.test.utopiacities.data.DBManager
import javax.inject.Inject

/**
 * Created by NhiNguyen on 3/24/2020.
 */

class CityRepository @Inject constructor(private val dbManager: DBManager) {
    fun getCities(limit: Int, page: Int = 0) = dbManager.getCities(limit, page)
}