package com.example.perpustakaan.core.id

import com.example.perpustakaan.core.data.source.local.LocalDataSource
import com.example.perpustakaan.core.data.source.remote.RemoteDataSource
import com.example.perpustakaan.core.data.source.remote.network.ApiConfig
import com.example.perpustakaan.core.data.source.remote.network.ApiConfigAddress
import org.koin.dsl.module

val appModule= module {
    single { ApiConfig.provideApiService }
    single { ApiConfigAddress.provideApiService }
    single { RemoteDataSource(get(),get()) }
    single { LocalDataSource() }
}