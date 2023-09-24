package com.cgo.utils.network

import android.content.Context
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File
import java.util.concurrent.TimeUnit

class DefaultOkHttpClientProvider(
    private val context: Context
) {
    fun get(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            cache(createDefaultCache())
            readTimeout(20L, TimeUnit.SECONDS)
            connectTimeout(20L, TimeUnit.SECONDS)
        }.build()
    }

    private fun createDefaultCache(): Cache {
        val cacheSize = (10 * 1024 * 1024).toLong()
        val cacheDirectory = File(context.cacheDir, "aviv-test")
        return Cache(cacheDirectory, cacheSize)
    }
}