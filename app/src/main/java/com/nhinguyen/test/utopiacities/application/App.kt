package com.nhinguyen.test.utopiacities.application

import com.nhinguyen.test.utopiacities.BuildConfig
import com.nhinguyen.test.utopiacities.data.DBHelper
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber
import javax.inject.Inject

/**
 *Created by NhiNguyen on 3/24/2020.
 */

class App : DaggerApplication() {


    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val appComponent = DaggerAppComponent.builder().application(this).build()
        appComponent.inject(this)
        return appComponent
    }
}
