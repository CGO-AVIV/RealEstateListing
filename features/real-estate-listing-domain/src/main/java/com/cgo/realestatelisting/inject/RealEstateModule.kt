package com.cgo.realestatelisting.inject

import com.cgo.realestatelisting.domain.RealEstateViewModel
import com.cgo.realestatelisting.domain.resourceprovider.AndroidRealEstateResourceProvider
import com.cgo.realestatelisting.domain.resourceprovider.RealEstateResourceProvider
import com.cgo.realestatelisting.domain.usecases.GetRealEstateListingUseCase
import com.cgo.realestatelisting.domain.usecases.GetRealEstateUseCase
import org.koin.dsl.module

val realEstateDomainModule = module {

    includes(realEstateDataModule)

    single<RealEstateResourceProvider> {
        AndroidRealEstateResourceProvider(
            get()
        )
    }

    single {
        GetRealEstateListingUseCase(get(), get())
    }

    single {
        GetRealEstateUseCase(get(), get())
    }

    single {
        RealEstateViewModel(get(), get(), get())
    }
}
