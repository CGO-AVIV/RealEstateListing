package com.cgo.realestatelisting.domain.mapper

import com.cgo.realestatelisting.data.model.RealEstateData
import com.cgo.realestatelisting.domain.model.RealEstate
import java.util.Currency
import java.util.Locale

fun RealEstateData.toRealEstate(currencySymbol: String): RealEstate {
    return RealEstate(
        bedroomsCount = bedroomsCount?.toString(),
        cityName = cityName,
        id = id,
        imageUrl = imageUrl,
        price = "$price $currencySymbol",
        professionalName = professionalName,
        propertyType = propertyType,
        roomsCount = roomsCount?.toString()
    )
}