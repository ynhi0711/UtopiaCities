package com.nhinguyen.test.utopiacities.ui.main.activity

import com.nhinguyen.test.utopiacities.di.scope.FragmentScoped
import com.nhinguyen.test.utopiacities.ui.main.fragment.UtopiaFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by NhiNguyen on 3/24/2020.
 */

@Module
abstract class MainModule {
    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeUtopiaFragment(): UtopiaFragment

}