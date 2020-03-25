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

    private val LIMIT_PAGE = 20

    private var page = 0
    private val _cities = MutableLiveData<ArrayList<City>>()
    val cities: LiveData<ArrayList<City>>
        get() = _cities

    fun getCities(isRefresh: Boolean, onSuccess: ((Boolean, List<City>) -> Unit)? = null) {
        isLoading.postValue(true)
        if (isRefresh) {
            page = 0
            _cities.value?.clear()
        } else page += 1
        repository.getCities(LIMIT_PAGE, page).let {
            isLoading.postValue(false)
            onSuccess?.invoke(isRefresh, it)
            if (isRefresh) {
                var value = _cities.value
                if (value == null) value = arrayListOf()
                value.addAll(it)
                _cities.postValue(value)
            }
        }
    }
}