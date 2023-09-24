package com.cgo.realestatelisting.domain.resourceprovider

import android.content.Context
import com.cgo.realestatelisting.R

interface RealEstateResourceProvider {
    val listTitleLabel: String
    val cityLabel: String
    val estateTypeLabel: String
    val priceLabel: String
    val bedroomsCountLabel: String
    val professionalLabel: String
    val roomsCountLabel: String
    val backLabel: String
    val atLabel: String
    val errorLabel: String
    val emptyLabel: String
    val retryLabel: String
    val notApplicableLabel: String
}

class AndroidRealEstateResourceProvider(
    private val context: Context
) : RealEstateResourceProvider {

    override val listTitleLabel: String
        get() = context.getString(R.string.title_label)

    override val cityLabel: String
        get() = context.getString(R.string.city_label)
    override val estateTypeLabel: String
        get() = context.getString(R.string.estate_type_label)
    override val priceLabel: String
        get() = context.getString(R.string.price_label)

    override val bedroomsCountLabel: String
        get() = context.getString(R.string.bedroom_label)

    override val professionalLabel: String
        get() = context.getString(R.string.professional_label)

    override val roomsCountLabel: String
        get() = context.getString(R.string.room_label)

    override val backLabel: String
        get() = context.getString(R.string.back_label)

    override val atLabel: String
        get() = context.getString(R.string.at_label)

    override val errorLabel: String
        get() = context.getString(R.string.error_label)

    override val emptyLabel: String
        get() = context.getString(R.string.empty_label)

    override val retryLabel: String
        get() = context.getString(R.string.retry_label)

    override val notApplicableLabel: String
        get() = context.getString(R.string.not_applicable_label)
}
