package com.nhinguyen.test.utopiacities.ui.main.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cavice.customer.base.BaseViewModel
import com.nhinguyen.test.utopiacities.model.City
import com.nhinguyen.test.utopiacities.repository.CityRepository
import javax.inject.Inject

/**
 * Created by NhiNguyen on 3/24/2020.
 */

class UtopiaCitiesViewModel @Inject constructor(private val repository: CityRepository) :
    BaseViewModel() {

    val LIMIT_PAGE = 20

    private var page = 0
    private val _cities = MutableLiveData<ArrayList<City>>()
    val cities: LiveData<ArrayList<City>>
        get() = _cities

    fun getCities(isRefresh: Boolean) {
        if (isRefresh) {
            _cities.postValue(arrayListOf())
            page = 0
        } else page += 1
        repository.getCities(LIMIT_PAGE, page).let {
            val value = _cities.value
            value?.addAll(it)
            _cities.postValue(value)
        }
    }
}