package com.nhinguyen.test.utopiacities.application

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Singleton

/**
 *Created by NhiNguyen on 3/24/2020.
 */

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent : AndroidInjector<DaggerApplication> {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent

    }

    fun inject(app: App)

    override fun inject(instance: DaggerApplication)
}