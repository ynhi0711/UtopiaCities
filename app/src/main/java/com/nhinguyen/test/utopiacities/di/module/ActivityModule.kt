package com.nhinguyen.test.utopiacities.di.module

import com.nhinguyen.test.utopiacities.ui.main.activity.MainActivity
import com.nhinguyen.test.utopiacities.di.scope.ActivityScoped
import com.nhinguyen.test.utopiacities.ui.main.activity.MainModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 *Created by NhiNguyen on 3/24/2020.
 */

@Module
abstract class ActivityModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun contributeMainActivity(): MainActivity

}