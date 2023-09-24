package com.cgo.realestatelisting.domain.usecases

import com.cgo.realestatelisting.data.RealEstateConfig
import com.cgo.realestatelisting.data.api.RealEstateRepository
import com.cgo.realestatelisting.domain.mapper.toRealEstate
import com.cgo.realestatelisting.domain.model.RealEstate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetRealEstateUseCase(
    private val realEstateRepository: RealEstateRepository,
    private val realEstateConfig: RealEstateConfig
) {

    private var count = 0
    suspend fun getRealEstate(id: Int): Flow<RealEstate> {
        count++
        return realEstateRepository.getRealEstate(id)
            .map { data ->
                data.toRealEstate(realEstateConfig.currencySymbol)
            }
    }
}