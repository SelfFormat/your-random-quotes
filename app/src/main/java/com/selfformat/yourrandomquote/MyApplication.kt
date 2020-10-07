package com.selfformat.yourrandomquote

import android.app.Application
import com.selfformat.yourrandomquote.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class MyApplication : Application() {
    override fun onCreate(){
        super.onCreate()
        Timber.plant(CrashReportingTree())
        startKoin {
            androidContext(this@MyApplication)
            modules(appModule)
        }
    }
}
