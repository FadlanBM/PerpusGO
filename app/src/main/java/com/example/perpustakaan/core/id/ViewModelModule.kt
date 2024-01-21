package com.example.perpustakaan.core.id

import com.example.perpustakaan.ui.auth.AuthViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule= module {
    viewModel { AuthViewModel(get()) }
}