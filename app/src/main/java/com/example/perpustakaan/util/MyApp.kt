package com.example.perpustakaan.util

import android.app.Application
import com.chibatching.kotpref.Kotpref
import com.example.perpustakaan.core.id.appModule
import com.example.perpustakaan.core.id.repositoryModule
import com.example.perpustakaan.core.id.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp:Application() {
    override fun onCreate() {
        super.onCreate()
        Kotpref.init(this)
        startKoin() {
            androidContext(this@MyApp)
            modules(listOf(appModule, viewModelModule, repositoryModule))
        }
    }
}