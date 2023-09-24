package com.cgo.realestatelisting.domain.usecases

import com.cgo.realestatelisting.data.RealEstateConfig
import com.cgo.realestatelisting.data.api.RealEstateRepository
import com.cgo.realestatelisting.domain.mapper.toRealEstate
import com.cgo.realestatelisting.domain.model.RealEstate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetRealEstateListingUseCase(
    private val realEstateRepository: RealEstateRepository,
    private val realEstateConfig: RealEstateConfig
) {

    suspend fun getRealEstateListing(): Flow<List<RealEstate>> {
        return realEstateRepository.getRealEstateListing()
            .map { listing ->
                listing.items.map {
                    it.toRealEstate(realEstateConfig.currencySymbol)
                }
            }
    }
}