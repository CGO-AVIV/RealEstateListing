package com.cgo.utils.network.inject

import com.cgo.utils.network.DefaultOkHttpClientProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val networkModule = module {
    single {
        DefaultOkHttpClientProvider(androidContext().applicationContext).get()
    }
}
