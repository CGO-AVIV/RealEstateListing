package com.cgo.realestatelisting.ui

import android.app.Application
import com.cgo.realestatelisting.inject.realEstateDomainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RealEstateApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@RealEstateApplication)
            modules(realEstateDomainModule)
        }
    }
}
