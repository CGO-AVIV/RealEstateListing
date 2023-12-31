package com.cgo.realestatelisting.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RealEstateListing(
    @Json(name = "items")
    val items: List<RealEstateData>
)