package com.cgo.realestatelisting.data.api

import com.cgo.realestatelisting.data.model.RealEstateData
import com.cgo.realestatelisting.data.model.RealEstateListing
import retrofit2.http.GET
import retrofit2.http.Path

interface RealEstateApi {

    @GET("listings.json")
    suspend fun getRealEstateListings(): RealEstateListing

    @GET("listings/{listingId}.json")
    suspend fun getRealEstate(@Path("listingId") listingId: Int): RealEstateData
}