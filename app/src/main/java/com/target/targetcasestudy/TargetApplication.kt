package com.target.targetcasestudy

import android.app.Application
import com.target.targetcasestudy.di.dealsDiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TargetApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@TargetApplication)
            modules(dealsDiModule)
        }
    }
}