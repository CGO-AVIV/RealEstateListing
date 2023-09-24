package com.cgo.realestatelisting.inject

import com.cgo.realestatelisting.data.AndroidRealEstateConfig
import com.cgo.realestatelisting.data.RealEstateConfig
import com.cgo.realestatelisting.data.api.RealEstateRepository
import com.cgo.realestatelisting.data.api.RealEstateServer
import com.cgo.utils.network.inject.networkModule
import org.koin.dsl.module

val realEstateDataModule = module {

    includes(networkModule)

    single<RealEstateConfig> { AndroidRealEstateConfig(get()) }

    single {
        RealEstateServer(get(), get())
    }

    single {
        RealEstateRepository(get())
    }
}