package com.example.perpustakaan.core.data.source.remote.request

data class UpdatePeminjamRequest(
    val alamat: String,
    val email: String,
    val nama_lengkap: String,
    val phone: String
)