package com.example.perpustakaan.core.id

import com.example.perpustakaan.ui.MenuSettings.MenuSettingsViewModel
import com.example.perpustakaan.ui.auth.AuthViewModel
import com.example.perpustakaan.ui.settings.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule= module {
    viewModel { AuthViewModel(get()) }
    viewModel { MenuSettingsViewModel(get()) }
    viewModel { SettingsViewModel(get()) }
}