package com.cgo.realestatelisting.domain.model

data class RealEstate(
    val bedroomsCount: String?,
    val cityName: String,
    val id: Int,
    val imageUrl: String?,
    val price: String,
    val professionalName: String,
    val propertyType: String,
    val roomsCount: String?
)