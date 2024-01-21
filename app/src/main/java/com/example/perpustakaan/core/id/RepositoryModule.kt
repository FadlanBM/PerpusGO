package com.example.perpustakaan.core.id

import com.example.perpustakaan.core.data.repository.AppRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { AppRepository(get(),get()) }
}