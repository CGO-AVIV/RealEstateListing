package com.cgo.utils.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

inline fun <reified T> create(
    httpClient: OkHttpClient,
    baseUrl: String,
    vararg converterFactories: MoshiConverterFactory
): T =
    Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(httpClient)
        .apply {
            converterFactories.forEach { factory -> addConverterFactory(factory) }
        }
        .build()
        .create(T::class.java)