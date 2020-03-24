package com.nhinguyen.test.utopiacities.application

import android.app.Application
import android.content.Context
import com.nhinguyen.test.utopiacities.di.module.ActivityModule
import com.nhinguyen.test.utopiacities.di.module.DBModule
import dagger.Module
import dagger.Provides
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Qualifier
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import com.nhinguyen.test.utopiacities.application.AppModule.ApplicationContext




/**
 *Created by NhiNguyen on 3/24/2020.
 */

@Module(
    includes = [
        AndroidSupportInjectionModule::class,
        ActivityModule::class,
        DBModule::class
    ]
)
class AppModule {

    @Provides
    @ApplicationContext
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    annotation class ActivityContext

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    annotation class ApplicationContext

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    annotation class DatabaseInfo
}