package com.example.perpustakaan.core.data.source.models

data class Peminjam(
    val ID: Int,
    val KoleksiBuku: Int,
    val UpdatedAt: String,
    val alamat: String,
    val email: String,
    val phone: String,
    val nama_lengkap: String,
    val password: String,
    val photo: String,
)