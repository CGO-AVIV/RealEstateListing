package com.cgo.realestatelisting.data.api

import com.cgo.realestatelisting.data.model.RealEstateData
import com.cgo.realestatelisting.data.model.RealEstateListing
import kotlinx.coroutines.flow.Flow

class RealEstateRepository(
    private val realEstateServer: RealEstateServer
) {

    suspend fun getRealEstateListing(): Flow<RealEstateListing> {
        return realEstateServer.getRealEstateListing()
    }

    suspend fun getRealEstate(id: Int): Flow<RealEstateData> {
        return realEstateServer.getRealEstate(id)
    }
}