package com.example.perpustakaan.core.data.source.remote.response

import com.example.perpustakaan.core.data.source.models.Peminjam

data class PeminjamResponse (
    val status: String? = null,
    val message: String? = null,
    val data: Peminjam? = null
)