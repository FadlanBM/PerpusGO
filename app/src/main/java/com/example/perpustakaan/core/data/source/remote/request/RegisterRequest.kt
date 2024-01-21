package com.example.perpustakaan.core.data.source.remote.request

data class RegisterRequest(
    val alamat: String,
    val email: String,
    val nama_lengkap: String,
    val password: String,
    val telp: String
)