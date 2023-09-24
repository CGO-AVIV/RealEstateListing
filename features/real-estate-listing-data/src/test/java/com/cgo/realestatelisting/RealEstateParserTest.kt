package com.cgo.realestatelisting

import com.cgo.realestatelisting.data.model.RealEstateData
import com.cgo.realestatelisting.data.model.RealEstateListing
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test

class RealEstateParserTest {

    private val testListing = RealEstateListing(
        items = listOf(
            RealEstateData(
                bedroomsCount = 4,
                cityName = "Villers-sur-Mer",
                id = 1,
                areaCode = 250,
                imageUrl = "https://v.seloger.com/s/crop/590x330/visuels/1/7/t/3/17t3fitclms3bzwv8qshbyzh9dw32e9l0p0udr80k.jpg",
                price = 1500000,
                professionalName = "GSL EXPLORE",
                propertyType = "Maison - Villa",
                offerType = 1,
                roomsCount = 8
            ),
            RealEstateData(
                bedroomsCount = null,
                cityName = "Nice",
                id = 4,
                areaCode = 250,
                imageUrl = "https://v.seloger.com/s/crop/590x330/visuels/1/9/f/x/19fx7n4og970dhf186925d7lrxv0djttlj5k9dbv8.jpg",
                price = 5000000,
                professionalName = "GSL CONTACTING",
                propertyType = "Maison - Villa",
                offerType = 3,
                roomsCount = null
            )
        )
    )

    @Test
    fun `Given a properly formatted json, when parsing it, it returns a list of RealEstateData`() {
        val moshi = Moshi.Builder().build()
        val realEstateList = moshi.adapter(RealEstateListing::class.java).fromJson(wellFormattedJsonString)
        assertEquals(testListing, realEstateList)
    }

    @Test
    fun `Given a json missing mandatory fields, when parsing it, it throws a JsonDataException`() {
        val moshi = Moshi.Builder().build()
        try {
            moshi.adapter(RealEstateListing::class.java).fromJson(missingMandatoryFieldsJsonString)
        } catch (e: Exception) {
            assertTrue( e is JsonDataException)
        }
    }
}