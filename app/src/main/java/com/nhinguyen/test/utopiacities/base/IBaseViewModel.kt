package com.nhinguyen.test.utopiacities.base

import androidx.lifecycle.MutableLiveData

interface IBaseViewModel {
    var isLoading: MutableLiveData<Boolean>
    var error: MutableLiveData<Throwable>
    fun onCreate()
    fun onStart()
    fun onPause()
    fun onResume()
    fun onStop()
    fun onDestroy()
}