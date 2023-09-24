package com.cgo.realestatelisting.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class RealEstateData(
    @Json(name = "bedrooms")
    val bedroomsCount: Int?,
    @Json(name = "city")
    val cityName: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "area")
    val areaCode: Int,
    @Json(name = "url")
    val imageUrl: String?,
    @Json(name = "price")
    val price: Long,
    @Json(name = "professional")
    val professionalName: String,
    @Json(name = "propertyType")
    val propertyType: String,
    @Json(name = "offerType")
    val offerType: Int,
    @Json(name = "rooms")
    val roomsCount: Int?
): Parcelable
