package com.cgo.realestatelisting.data

import android.content.Context
import com.cgo.realestatelisting.R

interface RealEstateConfig {
    val baseUrl: String
    val currencySymbol: String
}

class AndroidRealEstateConfig(
    private val context: Context
): RealEstateConfig {
    override val baseUrl: String
        get() = context.getString(R.string.real_estate_base_url)

    override val currencySymbol: String
        get() = context.getString(R.string.currency_symbol)
}