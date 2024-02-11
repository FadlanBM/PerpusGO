package com.example.perpustakaan.core.data.source.remote.request

data class RegisterRequest(
    val email: String,
    val nama_lengkap: String,
    val uid: String,
)