package com.cavice.customer.base

import androidx.lifecycle.*
import com.nhinguyen.test.utopiacities.base.IBaseViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 *Created by NhiNguyen on 3/24/2020.
 */

abstract class BaseViewModel : ViewModel(),
    IBaseViewModel, LifecycleObserver {

    private var compositeDisposable = CompositeDisposable()

    override var isLoading: MutableLiveData<Boolean> = MutableLiveData()

    override var error: MutableLiveData<Throwable> = MutableLiveData()

    fun addDisposable(disposable: Disposable) {
        if (compositeDisposable.isDisposed) compositeDisposable = CompositeDisposable()
        compositeDisposable.add(disposable)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    override fun onCreate() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    override fun onStart() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    override fun onResume() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    override fun onPause() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    override fun onStop() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun onDestroy() {
        if (!compositeDisposable.isDisposed) compositeDisposable.dispose()
    }
}