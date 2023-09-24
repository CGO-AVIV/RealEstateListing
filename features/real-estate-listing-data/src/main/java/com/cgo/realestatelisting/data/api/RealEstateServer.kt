package com.cgo.realestatelisting.data.api

import com.cgo.realestatelisting.data.RealEstateConfig
import com.cgo.realestatelisting.data.model.RealEstateData
import com.cgo.realestatelisting.data.model.RealEstateListing
import com.cgo.utils.network.create
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.OkHttpClient
import retrofit2.converter.moshi.MoshiConverterFactory

class RealEstateServer(
    private val okHttpClient: OkHttpClient,
    private val realEstateConfig: RealEstateConfig
) {

    private val moshi = Moshi.Builder().build()

    private val realEstateApi: RealEstateApi by lazy {
        create(
            httpClient = okHttpClient,
            baseUrl = realEstateConfig.baseUrl,
            MoshiConverterFactory.create(moshi)
        )
    }

    suspend fun getRealEstateListing(): Flow<RealEstateListing> {
        return flow {
            emit(realEstateApi.getRealEstateListings())
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getRealEstate(id: Int): Flow<RealEstateData> {
        return flow {
            emit(realEstateApi.getRealEstate(id))
        }.flowOn(Dispatchers.IO)
    }
}